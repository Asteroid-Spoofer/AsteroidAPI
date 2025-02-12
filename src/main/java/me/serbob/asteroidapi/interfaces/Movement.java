package me.serbob.asteroidapi.interfaces;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Represents an interface for handling various movement-related actions, combat, velocity, and climbing.
 */
public interface Movement {

    // Movement

    /**
     * Moves the entity to the specified coordinates.
     *
     * @param x The X-coordinate to move to.
     * @param z The Z-coordinate to move to.
     */
    void moveTo(double x, double z);

    /**
     * Initiates a jump action.
     */
    void jump();

    /**
     * Initiates a jump action without checking if the player is on ground or not.
     */
    void forceJump();

    /**
     * Stops the entity's movement with moonwalk.
     * This is used so that the stop inside PathingAction looks smooth.
     */
    void stopMovement();

    /**
     * Stops the entity's movement with no moonwalk.
     * This is used when the movement should be COMPLETELY stopped.
     */
    void fullStop();

    /**
     * Gets the target X-coordinate for movement.
     *
     * @return The target X-coordinate.
     */
    double getTargetX();

    /**
     * Gets the target Z-coordinate for movement.
     *
     * @return The target Z-coordinate.
     */
    double getTargetZ();

    // Combat

    /**
     * Applies knockback to the entity based on the attacker's location.
     *
     * @param attackerLocation The location of the attacker.
     */
    void applyKnockback(Location attackerLocation);

    // Velocity

    /**
     * Adds velocity to the entity.
     *
     * @param velocity The velocity to be added.
     */
    void addVelocity(Vector velocity);

    /**
     * Gets the current velocity of the entity.
     *
     * @return The current velocity.
     */
    Vector getVelocity();

    // Climbing

    /**
     * Applies an upward force to the entity.
     *
     * @param force The force to be applied.
     */
    void applyUpwardForce(double force);

    /**
     * Sets whether the entity is climbing.
     *
     * @param climbing True if the entity is climbing, false otherwise.
     */
    void setClimbing(boolean climbing);

    /**
     * Checks if the entity is currently climbing.
     *
     * @return True if the entity is climbing, false otherwise.
     */
    boolean isClimbing();
}

