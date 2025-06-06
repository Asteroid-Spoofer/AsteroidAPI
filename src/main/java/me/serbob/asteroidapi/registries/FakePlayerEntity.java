package me.serbob.asteroidapi.registries;

import me.serbob.asteroidapi.handlers.FBrain;
import me.serbob.asteroidapi.interfaces.*;
import me.serbob.asteroidapi.injection.Features;
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
     * Disconnects a fake player through paper's internal async disconnect method
     */
    void disconnectAsync();

    /**
     * Moves the fake player to the specified vector after transforming it as a Vec3D.
     * No pathfinding involved
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

    void setPing(int ping);

    /**
     * Gets the CraftPlayer object associated with the fake player.
     *
     * @return The CraftPlayer object representing the fake player.
     */
    Object getCraftPlayer();

    /**
     * Gets the actual NMS ServerPlayer object associated with the fake player.
     *
     * @return The NMS ServerPlayer object representing the NMS fake player.
     */
    Object getServerPlayer();

    /*
     * This allows you to use actions
     */
    FBrain getFBrain();

    INavigation getNavigation();

    FakePlayerInventory getFakeInventory();

    ILookController getLookController();

    Overrides getOverrides();

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
    ITargetCore getTargetCore();

    /**
     * Gets the block destruction handler for the fake player.
     *
     * @return The block destruction handler.
     */
    DestroyBlock getDestroyBlock();

    /**
     * Gets the features associated with the fake player.
     *
     * @return The features associated with the fake player.
     */
    Features features();
}
