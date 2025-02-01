package me.serbob.asteroidapi.actions.interfaces;

import me.serbob.asteroidapi.actions.abstracts.Action;
import me.serbob.asteroidapi.registries.FakePlayerEntity;

import java.util.Set;

public interface IActionManager {
    /**
     * Register one or multiple actions
     * @param actions The actions to register
     */
    void register(Action... actions);

    /**
     * Unregister one or multiple actions
     * @param actions The actions to unregister
     */
    void unregister(Action... actions);

    /**
     * Unregister actions with an option to stop them
     * @param stop Whether to stop the action if it's current
     * @param actions The actions to unregister
     */
    void unregister(boolean stop, Action... actions);

    /**
     * Force unregister actions, stopping them immediately
     * @param actions The actions to force unregister
     */
    void forceUnregister(Action... actions);

    /**
     * Unregister all actions
     */
    void unregisterAll();

    /**
     * Tick/update the action system
     */
    void tick();

    /**
     * Get all registered actions
     * @return Set of registered actions
     */
    Set<Action> getRegisteredactions();

    /**
     * Get the currently active action
     * @return The current action, or null if none
     */
    Action getCurrentaction();

    /**
     * Get the FakePlayer instance
     * @return The FakePlayer entity
     */
    FakePlayerEntity getFakePlayer();
}
