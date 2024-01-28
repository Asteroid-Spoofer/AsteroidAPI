package me.serbob.asteroidapi.Behaviour;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents an interface for handling fake player spawning events.
 */
public interface FakePlayerSpawn {

    /**
     * Asynchronously called when a fake player is spawned in the NMS (net.minecraft.server) implementation.
     *
     * @param fakePlayer The fake player that is spawned.
     * @param entityPlayer The NMS entity player.
     * @param instance   The JavaPlugin instance responsible for the spawning.
     */
    void onSpawnFakePlayerNMS(Player fakePlayer, Object entityPlayer, JavaPlugin instance);

    /**
     * Asynchronously called after a fake player has been spawned, allowing for additional actions.
     *
     * @param fakePlayer The fake player that has been spawned.
     * @param instance   The JavaPlugin instance responsible for the spawning.
     */
    void onSpawnFakePlayerAfterLoad(Player fakePlayer, JavaPlugin instance);
}

