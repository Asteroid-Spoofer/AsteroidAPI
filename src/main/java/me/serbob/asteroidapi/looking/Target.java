package me.serbob.asteroidapi.looking;

import lombok.Getter;
import me.serbob.asteroidapi.actions.enums.Priority;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

@Getter
public class Target {
    private final Object target;
    private final Type type;
    private final Priority priority;

    public Target(Object target, Type type, Priority priority) {
        this.target = target;
        this.type = type;
        this.priority = priority;
    }

    public Target(Location location, Priority priority) {
        this.target = location;
        this.type = Type.LOCATION;
        this.priority = priority;
    }

    public Target(Entity entity, Priority priority) {
        this.target = entity;
        this.type = Type.ENTITY;
        this.priority = priority;
    }

    public Target(Block block, Priority priority) {
        this.target = block;
        this.type = Type.BLOCK;
        this.priority = priority;
    }

    public Target(Location location) {
        this.target = location;
        this.type = Type.LOCATION;
        this.priority = Priority.NORMAL;
    }

    public Target(Entity entity) {
        this.target = entity;
        this.type = Type.ENTITY;
        this.priority = Priority.NORMAL;
    }

    public Target(Block block) {
        this.target = block;
        this.type = Type.BLOCK;
        this.priority = Priority.NORMAL;
    }

    public static Target of(Location location, Priority priority) {
        return new Target(location, priority);
    }

    public static Target of(Entity entity, Priority priority) {
        return new Target(entity, priority);
    }

    public static Target of(Block block, Priority priority) {
        return new Target(block, priority);
    }

    public Location getLocation() {
        switch (type) {
            case LOCATION:
                return (Location) target;
            case ENTITY:
                return ((Entity) target).getLocation();
            case BLOCK:
                // TODO
                // Could work as well:
                // return block.getLocation().clone().add(1, -0.5, 1);
                return ((Block) target).getLocation().clone().add(0.5, -0.5, 0.5);
            default:
                return null;
        }
    }

    public enum Type {
        LOCATION, ENTITY, BLOCK
    }

    @SuppressWarnings("unchecked")
    public <T> T getTarget() {
        return (T) target;
    }
}
