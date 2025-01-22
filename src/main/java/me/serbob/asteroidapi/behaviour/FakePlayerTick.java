package me.serbob.asteroidapi.behaviour;

import me.serbob.asteroidapi.registries.FakePlayerEntity;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Handles individual fake player tick events.
 */
public interface FakePlayerTick {

    /**
     * Called on each server tick for individual fake players.
     *
     * @param fakePlayer      The fake player to perform actions on.
     * @param javaPlugin  The JavaPlugin instance responsible for the tick.
     */
    void individualPlayerTickAsync(FakePlayerEntity fakePlayer, JavaPlugin javaPlugin);
}

