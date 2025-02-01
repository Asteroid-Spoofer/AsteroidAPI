package me.serbob.asteroidapi.commands;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class AsteroidCommandManager {
    private static class LazyHolder {
        public static AsteroidCommandManager INSTANCE = new AsteroidCommandManager();
    }

    private AsteroidCommandManager() {}

    public static AsteroidCommandManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Getter
    private final Map<String, AsteroidCommand> commandMap = new HashMap<>();

    public static void registerCommand(String commandName, AsteroidCommand command) {
        AsteroidCommandManager.getInstance().commandMap.put(commandName, command);
    }
}
