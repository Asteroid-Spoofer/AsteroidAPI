package me.serbob.asteroidapi.actions.util;

import lombok.Getter;

@Getter
public class Result<T> {
    private final Type type;
    private final T value;
    private final String message;
    private final Throwable error;

    public Result(Type type) {
        this(type, null, null, null);
    }

    public Result(Type type, T value) {
        this(type, value, null, null);
    }

    public Result(Type type, T value, String message, Throwable error) {
        this.type = type;
        this.value = value;
        this.message = message;
        this.error = error;
    }

    public enum Type {
        SUCCESS,
        FAILURE,
        INTERRUPTED,
        ERROR
    }
}
