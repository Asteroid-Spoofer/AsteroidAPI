package me.serbob.asteroidapi.Server;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Custom event triggered when Asteroid is loaded.
 */
public class AsteroidLoadedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
