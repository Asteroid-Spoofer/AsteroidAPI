package me.serbob.asteroidapi.actions.abstracts;

import lombok.Getter;
import lombok.Setter;
import me.serbob.asteroidapi.actions.enums.ActionType;
import me.serbob.asteroidapi.actions.enums.Priority;
import me.serbob.asteroidapi.actions.interfaces.ActionValidator;
import me.serbob.asteroidapi.actions.interfaces.IActionManager;
import me.serbob.asteroidapi.actions.normal.ActionFatigue;
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
    private final DynamicPriority priority;
    private ActionFatigue fatigue;

    // Tree structure
    private Action parentAction;
    private Action childAction;

    // Validation and results
    private Result<?> result;
    private List<ActionValidator> validators;
    private Set<Consumer<Result<?>>> callbacks;

    // State tracking
    private boolean finished;
    private boolean paused;
    private boolean running;
    private long currentDuration;
    private Map<String, Object> actionData;

    public Action(ActionType type) {
        this.type = type;
        this.priority = new DynamicPriority(Priority.NORMAL);
        this.fatigue = new ActionFatigue(100, 1, 1);
    }

    public Action(ActionType type, Priority priority) {
        this.type = type;
        this.priority = new DynamicPriority(priority);
        this.fatigue = new ActionFatigue(100, 1, 1);
    }

    public void register(IActionManager manager) {
        this.manager = manager;
        fakePlayer = manager.getFakePlayer();
    }

    public void internalStart(StartType startType) {
        if (startType == StartType.START) {
            currentDuration = 0;
            finished = false;

            if (validators == null || validateAll()) {
                running = true;
                onStart(startType);
            } else {
                markAsFinished(new Result<>(Result.Type.FAILURE, "Validation failed"));
            }
        } else if (startType == StartType.RESUME) {
            if (childAction != null) {
                childAction.internalStart(StartType.RESUME);
                childAction.onStart(StartType.RESUME);
            }
            running = true;
            paused = false;
        }
    }

    public void internalUpdate() {
        if (childAction != null) {
            Action child = childAction;
            child.internalUpdate();

            if (childAction != null) {
                childAction.onUpdate();
            }
        }

        currentDuration++;
        fatigue.update();

        if (validators != null && !validateAll()) {
            markAsFinished(new Result<>(Result.Type.FAILURE, "Runtime validation failed"));
            return;
        }

        onUpdate();
        fatigue.increaseFatigue();
    }

    public void internalStop(StopType stopType) {
        running = false;

        if (childAction != null) {
            childAction.internalStop(stopType);
            childAction.onStop(stopType);
        }

        onStop(stopType);

        if (stopType == StopType.PAUSE) {
            paused = true;
        } else {
            finished = true;
        }
    }

    public void markAsFinished(Result<?> result) {
        if (parentAction != null) {
            parentAction.childAction = null;
        }

        running = false;
        finished = true;
        this.result = result;

        if (callbacks != null) {
            for (Consumer<Result<?>> callback : callbacks) {
                callback.accept(result);
            }
        }

        onStop(result.getType() == Result.Type.SUCCESS ? StopType.SUCCESS : StopType.FAILURE);
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
            action.internalStart(StartType.START);
            action.onStart(StartType.START);
        }
    }

    private boolean validateAll() {
        for (ActionValidator validator : validators) {
            if (!validator.validate(this)) {
                return false;
            }
        }
        return true;
    }

    public void addValidator(ActionValidator validator) {
        if (validators == null) {
            validators = new ArrayList<>(4);
        }
        validators.add(validator);
    }

    public Action onFinished(Consumer<Result<?>> callback) {
        if (callbacks == null) {
            callbacks = new HashSet<>(2);
        }
        callbacks.add(callback);
        return this;
    }

    public void setData(String key, Object value) {
        if (actionData == null) {
            actionData = new HashMap<>(4);
        }
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
        return actionData != null ? (T) actionData.get(key) : null;
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
