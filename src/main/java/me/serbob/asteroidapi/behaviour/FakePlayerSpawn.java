package me.serbob.asteroidapi.behaviour;

import me.serbob.asteroidapi.registries.FakePlayerEntity;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents an interface for handling fake player spawning events.
 */
public interface FakePlayerSpawn {

    /**
     * Asynchronously called when a fake player is spawned in the NMS (net.minecraft.server) implementation.
     *
     * @param fakePlayerEntity The FakePlayerEntity object.
     * @param instance   The JavaPlugin instance responsible for the spawning.
     */
    void onSpawnFakePlayerNMSAsync(FakePlayerEntity fakePlayerEntity, JavaPlugin instance);

    /**
     * Asynchronously called after a fake player has been spawned, allowing for additional actions.
     *
     * @param fakePlayerEntity The FakePlayerEntity object.
     * @param instance   The JavaPlugin instance responsible for the spawning.
     */
    void onSpawnFakePlayerAfterLoadAsync(FakePlayerEntity fakePlayerEntity, JavaPlugin instance);
}

