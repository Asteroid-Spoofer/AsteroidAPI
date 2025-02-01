package me.serbob.asteroidapi.actions.abstracts;

import lombok.Getter;
import lombok.Setter;
import me.serbob.asteroidapi.actions.enums.ActionState;
import me.serbob.asteroidapi.actions.enums.ActionType;
import me.serbob.asteroidapi.actions.enums.Priority;
import me.serbob.asteroidapi.actions.interfaces.ActionValidator;
import me.serbob.asteroidapi.actions.interfaces.IActionManager;
import me.serbob.asteroidapi.actions.normal.ActionFatigue;
import me.serbob.asteroidapi.actions.normal.ActionMonitor;
import me.serbob.asteroidapi.actions.util.DynamicPriority;
import me.serbob.asteroidapi.actions.util.Result;
import me.serbob.asteroidapi.registries.FakePlayerEntity;

import java.util.*;
import java.util.function.Consumer;

@Getter
@Setter
public abstract class Action implements Comparable<Action> {
    // Core components
    private IActionManager manager;
    private FakePlayerEntity fakePlayer;
    private final ActionType type;

    // Enhanced systems
    private ActionState state = ActionState.IDLE;
    private ActionState previousState = null;
    private final Map<ActionState, Set<ActionState>> validTransitions = new HashMap<>();
    private final DynamicPriority priority;
    private ActionFatigue fatigue;
    private final ActionMonitor monitor;

    // Tree structure
    private Action parentAction;
    private Action childAction;
    private final List<Action> actionChain = new ArrayList<>();

    // Validation and results
    private Result<?> result;
    private final List<ActionValidator> validators = new ArrayList<>();
    private final Set<Consumer<Result<?>>> callbacks = new HashSet<>();

    // State tracking
    private boolean finished;
    private boolean paused;
    private long currentDuration;
    private final Map<String, Object> actionData = new HashMap<>();

    public Action(ActionType type) {
        this.type = type;
        this.priority = new DynamicPriority(Priority.NORMAL);
        this.fatigue = new ActionFatigue(100, 1, 1);
        this.monitor = new ActionMonitor(this);
        initializeStateTransitions();
    }

    public Action(ActionType type, Priority priority) {
        this.type = type;
        this.priority = new DynamicPriority(priority);
        this.fatigue = new ActionFatigue(100, 1, 1);
        this.monitor = new ActionMonitor(this);
        initializeStateTransitions();
    }

    private void initializeStateTransitions() {
        addTransition(ActionState.IDLE, ActionState.INITIALIZING);
        addTransition(ActionState.INITIALIZING, ActionState.RUNNING);
        addTransition(ActionState.RUNNING, ActionState.PAUSED, ActionState.FINISHING);
        addTransition(ActionState.PAUSED, ActionState.RUNNING, ActionState.FINISHING);
        addTransition(ActionState.FINISHING, ActionState.FINISHED);
    }

    private void addTransition(ActionState from, ActionState... to) {
        validTransitions.computeIfAbsent(from, k -> new HashSet<>())
                .addAll(Arrays.asList(to));
    }

    public void register(IActionManager manager) {
        this.manager = manager;
        fakePlayer = manager.getFakePlayer();
    }

    public void internalStart(StartType startType) {
        if (startType == StartType.START) {
            if (!transitionTo(ActionState.INITIALIZING)) return;
            currentDuration = 0;
            finished = false;
            monitor.recordStateChange(state, ActionState.RUNNING);

            if (validateAll()) {
                if (transitionTo(ActionState.RUNNING)) {
                    onStart(startType);
                }
            } else {
                markAsFinished(new Result<>(Result.Type.FAILURE, "Validation failed"));
            }
        } else if (startType == StartType.RESUME) {
            if (childAction != null) {
                childAction.internalStart(StartType.RESUME);
                childAction.onStart(StartType.RESUME);
            }
            transitionTo(ActionState.RUNNING);
        }
    }

    public void internalUpdate() {
        if (childAction != null) {
            childAction.internalUpdate();
            childAction.onUpdate();
        }

        currentDuration++;
        fatigue.update();

        // TODO
        //   if (fatigue.isFatigued()) {
        //     markAsFinished(new Result<>(Result.Type.FAILURE, "Action fatigued"));
        //    return;
        // }

        if (!validateAll()) {
            markAsFinished(new Result<>(Result.Type.FAILURE, "Runtime validation failed"));
            return;
        }

        onUpdate();
        fatigue.increaseFatigue();
    }

    public void internalStop(StopType stopType) {
        transitionTo(ActionState.FINISHING);
        if (childAction != null) {
            childAction.internalStop(stopType);
            childAction.onStop(stopType);
        }
        onStop(stopType);
        transitionTo(ActionState.FINISHED);
    }

    public boolean transitionTo(ActionState newState) {
        if (!canTransitionTo(newState)) {
            return false;
        }

        previousState = state;
        state = newState;
        monitor.recordStateChange(previousState, state);
        onStateChanged(previousState, state);
        return true;
    }

    private boolean canTransitionTo(ActionState newState) {
        return validTransitions.containsKey(state) &&
                validTransitions.get(state).contains(newState);
    }

    public void markAsFinished(Result<?> result) {
        if (!transitionTo(ActionState.FINISHING)) return;

        if (parentAction != null) {
            parentAction.childAction = null;
        }

        finished = true;
        this.result = result;
        monitor.recordResult(result);
        callbacks.forEach(callback -> callback.accept(result));

        onStop(result.getType() == Result.Type.SUCCESS ? StopType.SUCCESS : StopType.FAILURE);
        internalStop(StopType.SUCCESS);

        transitionTo(ActionState.FINISHED);
    }

    public void run(Action action) {
        action.fakePlayer = fakePlayer;
        if (action.canStart(StartType.START)) {
            if (childAction != null) {
                childAction.internalStop(StopType.STOP);
                childAction.onStop(StopType.STOP);
            }
            childAction = action;
            action.parentAction = this;
            actionChain.add(action);
            action.internalStart(StartType.START);
            action.onStart(StartType.START);
        }
    }

    private boolean validateAll() {
        return validators.stream().allMatch(v -> v.validate(this));
    }

    public void addValidator(ActionValidator validator) {
        validators.add(validator);
    }

    public Action onFinished(Consumer<Result<?>> callback) {
        callbacks.add(callback);
        return this;
    }

    public void setData(String key, Object value) {
        actionData.put(key, value);
    }

    public boolean isHigherPriorityThan(Action other) {
        return this.priority.calculate() > other.priority.calculate();
    }

    public void updatePriority(Priority priority) {
        this.priority.setBasePriority(priority);
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(String key) {
        return (T) actionData.get(key);
    }

    @Override
    public int compareTo(Action other) {
        return Float.compare(this.priority.calculate(), other.priority.calculate());
    }

    public abstract void onStart(StartType startType);
    public abstract void onUpdate();
    public abstract void onStop(StopType stopType);
    public abstract boolean canStart(StartType startType);
    public abstract boolean canStop(StopType stopType);
    public void onStateChanged(ActionState oldState, ActionState newState) {}

    public enum StartType {
        START,
        RESUME,
        RESTART
    }

    public enum StopType {
        STOP,
        PAUSE,
        SUCCESS,
        FAILURE
    }
}
