package me.serbob.asteroidapi.Interfaces;

import me.serbob.asteroidapi.Handlers.TargetHandler;

/**
 * Represents an interface for handling targeting-related actions.
 */
public interface Target {

    /**
     * Changes the target handler for this entity.
     *
     * @param targetHandler The new target handler to be set.
     */
    void changeTargetHandler(TargetHandler targetHandler);

    /**
     * Gets the current target handler for this entity.
     *
     * @return The current target handler.
     */
    TargetHandler getTargetHandler();
}

