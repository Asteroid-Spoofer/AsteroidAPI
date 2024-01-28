package me.serbob.asteroidapi.Registries;

import me.serbob.asteroidapi.Handlers.TargetHandler;
import me.serbob.asteroidapi.Interfaces.DestroyBlock;
import me.serbob.asteroidapi.Interfaces.Movement;
import me.serbob.asteroidapi.Interfaces.Target;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;

/**
 * Represents an interface for defining fake player behavior.
 */
public interface FakePlayerEntity {

    /**
     * Spawns a fake player using NMS (net.minecraft.server) implementation.
     *
     * @param fakePlayers A map containing fake player UUIDs and corresponding objects, most used being fakePlayers from FakePlayerRegistry.
     */
    void spawnFakePlayerNMS(Map<UUID, Object> fakePlayers);

    /**
     * Moves the fake player to the specified vector after transforming it as a Vec3D.
     *
     * @param vector The vector representing the movement.
     */
    void move(Vector vector);

    /**
     * Sets the head yaw of the fake player.
     *
     * @param headYaw The new head yaw angle.
     */
    void setHeadYaw(float headYaw);

    /**
     * Sets the body yaw of the fake player.
     *
     * @param bodyYaw The new body yaw angle.
     */
    void setBodyYaw(float bodyYaw);

    /**
     * Gets the Bukkit Player representation of the fake player.
     *
     * @return The Bukkit Player representing the fake player.
     */
    Player getEntityPlayer();

    /**
     * Gets the CraftPlayer object associated with the fake player.
     *
     * @return The CraftPlayer object representing the fake player.
     */
    Object getCraftPlayer();
    /**
     * Gets the movement handler for the fake player.
     *
     * @return The movement handler.
     */
    Movement getMovement();

    /**
     * Gets the target handler for the fake player.
     *
     * @return The target handler.
     */
    Target getTarget();

    /**
     * Gets the block destruction handler for the fake player.
     *
     * @return The block destruction handler.
     */
    DestroyBlock getDestroyBlock();
}
