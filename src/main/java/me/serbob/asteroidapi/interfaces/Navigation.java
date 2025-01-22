package me.serbob.asteroidapi.interfaces;

import org.bukkit.Location;

public interface Navigation {
    /**
     * Moves the fake player to the specified location with pathfinding.
     *
     * @param location The location where it has to go.
     */
    void moveTo(Location location);
}
