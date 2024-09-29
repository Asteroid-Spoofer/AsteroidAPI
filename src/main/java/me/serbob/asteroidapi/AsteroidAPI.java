package me.serbob.asteroidapi;

import lombok.Getter;
import lombok.Setter;
import me.serbob.asteroidapi.Handlers.SpoofHandlerAPI;

@Setter
@Getter
public final class AsteroidAPI {
    private static class LazyHolder {
        static AsteroidAPI INSTANCE = new AsteroidAPI();
    }

    public static AsteroidAPI getInstance() {
        return LazyHolder.INSTANCE;
    }

    private AsteroidAPI() {}

    private SpoofHandlerAPI spoofHandlerAPI;
    private boolean disableSpoofHandlerTick;
}
