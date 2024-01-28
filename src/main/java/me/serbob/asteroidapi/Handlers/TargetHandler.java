package me.serbob.asteroidapi.Handlers;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

/**
 * Handles targeting for entities and blocks.
 */
public class TargetHandler {
    private final Entity entity;
    private final Block block;
    private final TargetType targetType;

    /**
     * Constructs a TargetHandler for an entity target.
     *
     * @param entity The targeted entity.
     */
    public TargetHandler(Entity entity) {
        this.entity = entity;
        this.block = null;
        this.targetType = TargetType.ENTITY;
    }

    /**
     * Constructs a TargetHandler for a block target.
     *
     * @param block The targeted block.
     */
    public TargetHandler(Block block) {
        this.entity = null;
        this.block = block;
        this.targetType = TargetType.BLOCK;
    }

    /**
     * Gets the location of the target, considering the type (entity or block).
     *
     * @return The location of the target.
     */
    public Location getTargetLocation() {
        switch (targetType) {
            case ENTITY:
                return entity.getLocation();
            case BLOCK:
                return block.getLocation().clone().add(1, -0.5, 1);
        }
        return null;
    }

    /**
     * Enum representing the target type (entity or block).
     */
    public enum TargetType {
        ENTITY,
        BLOCK
    }
}

