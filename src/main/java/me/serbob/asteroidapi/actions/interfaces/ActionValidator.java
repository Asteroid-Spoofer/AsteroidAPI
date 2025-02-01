package me.serbob.asteroidapi.actions.interfaces;

import me.serbob.asteroidapi.actions.abstracts.Action;

@FunctionalInterface
public interface ActionValidator {
    boolean validate(Action action);
}
