package me.serbob.asteroidapi.Behaviour;

import me.serbob.asteroidapi.Registries.FakePlayerEntity;
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
    void individualPlayerTick(FakePlayerEntity fakePlayer, JavaPlugin javaPlugin);
}

