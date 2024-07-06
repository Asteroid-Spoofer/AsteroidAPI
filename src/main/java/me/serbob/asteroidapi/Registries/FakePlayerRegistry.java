package me.serbob.asteroidapi.Registries;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry class for managing fake players.
 */
public class FakePlayerRegistry {

    /**
     * The singleton instance of FakePlayerRegistry.
     */
    public static final FakePlayerRegistry INSTANCE = new FakePlayerRegistry();

    /**
     * Public constructor to enforce singleton pattern.
     */
    public FakePlayerRegistry() {}

    /**
     * Map to store fake players by their UUIDs.
     */
    private static final Map<UUID, Object> fakePlayers = new ConcurrentHashMap<>();

    /**
     * Gets the map of fake players.
     *
     * @return The map of fake players.
     */
    public Map<UUID, Object> getFakePlayers() {
        return fakePlayers;
    }

    /**
     * Checks if a given UUID corresponds to a fake player.
     *
     * @param uuid The UUID to check.
     * @return True if the UUID belongs to a fake player, false otherwise.
     */
    public boolean isAFakePlayer(UUID uuid) {
        return fakePlayers.containsKey(uuid);
    }

    /**
     * Adds a fake player to the registry.
     *
     * @param uuid          The UUID of the fake player.
     * @param playerObject  The object representing the fake player.
     */
    public void addFakePlayer(UUID uuid, Object playerObject) {
        fakePlayers.put(uuid, playerObject);
    }

    /**
     * Removes a fake player from the registry.
     *
     * @param uuid The UUID of the fake player to remove.
     */
    public void removeFakePlayer(UUID uuid) {
        fakePlayers.remove(uuid);
    }
}
