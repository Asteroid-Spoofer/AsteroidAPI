package me.serbob.asteroidapi.interfaces;

import me.serbob.asteroidapi.looking.Target;

/**
 * Represents an interface for handling targeting-related actions.
 */
public interface ITargetCore {

    /**
     * Changes the target for this entity.
     *
     * @param targetHandler The new target handler to be set.
     */
    void changeTargetHandler(Target targetHandler);

    /**
     * Gets the current target for this entity.
     *
     * @return The current target handler.
     */
    Target getTargetHandler();
}

