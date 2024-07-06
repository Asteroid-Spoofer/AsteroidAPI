package me.serbob.asteroidapi.Events;

import me.serbob.asteroidapi.Registries.FakePlayerEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FakePlayerEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final FakePlayerEntity fakePlayer;
    public FakePlayerEvent(FakePlayerEntity fakePlayer) {
        this.fakePlayer = fakePlayer;
    }
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    public static HandlerList getHandlerList() {
        return handlerList;
    }
    public FakePlayerEntity getFakePlayer() {
        return this.fakePlayer;
    }
}
