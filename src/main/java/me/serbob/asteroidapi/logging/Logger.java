package me.serbob.asteroidapi.logging;

public interface Logger {
    void success(String message);
    void info(String message);
    void warning(String message);
    void error(String message);
    void debug(String message);
}
