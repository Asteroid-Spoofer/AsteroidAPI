package me.serbob.asteroidapi.handlers;

import lombok.Getter;
import me.serbob.asteroidapi.actions.interfaces.IActionManager;
import me.serbob.asteroidapi.registries.FakePlayerEntity;

@Getter
public class FBrain {
    private final IActionManager actionManager;

    public FBrain(FakePlayerEntity fakePlayer, IActionManager actionManager) {
        this.actionManager = actionManager;
    }

    public void tick() {
        actionManager.tick();
    }
}
