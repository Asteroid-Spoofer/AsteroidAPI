package me.serbob.asteroidapi.interfaces;

import me.serbob.asteroidapi.pathfinding.Path;
import org.bukkit.Location;

public interface INavigation {
    Path getCurrentPath();

    void moveTo(Location location);
    Path moveTo(Location location, Path path);

    void stop();
}
