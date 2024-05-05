package me.serbob.asteroidapi.Events.Impl;

import me.serbob.asteroidapi.Events.FakePlayerEvent;
import me.serbob.asteroidapi.Registries.FakePlayerEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;

public class FakePlayerDeathEvent extends FakePlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    // TODO MORE
    public FakePlayerDeathEvent(FakePlayerEntity fakePlayer) {
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
