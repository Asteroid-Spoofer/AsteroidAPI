package me.serbob.asteroidapi.events.Impl;

import me.serbob.asteroidapi.events.FakePlayerEvent;
import me.serbob.asteroidapi.registries.FakePlayerEntity;
import org.bukkit.event.HandlerList;

public class FakePlayerQuitEvent extends FakePlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    public FakePlayerQuitEvent(FakePlayerEntity fakePlayer) {
        super(fakePlayer);
    }
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    public  static HandlerList getHandlerList() {
        return handlerList;
    }
}
