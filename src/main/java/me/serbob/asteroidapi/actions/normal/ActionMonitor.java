package me.serbob.asteroidapi.actions.normal;

import me.serbob.asteroidapi.actions.abstracts.Action;
import me.serbob.asteroidapi.actions.enums.ActionState;
import me.serbob.asteroidapi.actions.util.Result;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ActionMonitor {
    private final Action action;
    private final List<StateChange> stateHistory = new ArrayList<>();
    private long startTime;
    private long totalRunTime;
    private int successCount;
    private int failureCount;

    public ActionMonitor(Action action) {
        this.action = action;
    }

    public void recordStateChange(ActionState oldState, ActionState newState) {
        stateHistory.add(new StateChange(oldState, newState, System.currentTimeMillis()));
    }

    public void recordResult(Result<?> result) {
        if (result.getType() == Result.Type.SUCCESS) {
            successCount++;
        } else {
            failureCount++;
        }
    }

    static class StateChange {
        final ActionState from;
        final ActionState to;
        final long timestamp;

        StateChange(ActionState from, ActionState to, long timestamp) {
            this.from = from;
            this.to = to;
            this.timestamp = timestamp;
        }
    }
}
