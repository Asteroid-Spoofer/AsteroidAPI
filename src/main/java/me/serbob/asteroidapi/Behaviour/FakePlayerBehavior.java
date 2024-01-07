package me.serbob.asteroidapi.Behaviour;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface FakePlayerBehavior {
    void onIndividualFakePlayerSpawn(Player fakePlayer, JavaPlugin instance);
}
