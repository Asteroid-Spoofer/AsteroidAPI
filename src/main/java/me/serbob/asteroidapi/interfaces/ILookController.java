package me.serbob.asteroidapi.interfaces;

import me.serbob.asteroidapi.looking.Target;
import me.serbob.asteroidapi.pathfinding.Position3D;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Map;

public interface ILookController {
    void tick();

    void lookAt(Position3D position);
    void lookAt(Location location);

    Target getHighestPriorityTarget();
    String getLastTarget();

    boolean hasTarget(String identifier);
    boolean addTarget(String identifier, Target ITarget);
    boolean removeTarget(String identifier);
    void purgeTargets();

    Target getTarget(String identifier);
    Map<String, Target> getTargets();

    Vector getDirection();
    void setLookAhead(boolean value);
}
