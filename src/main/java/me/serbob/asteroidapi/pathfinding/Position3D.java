package me.serbob.asteroidapi.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@AllArgsConstructor
public class Position3D implements Cloneable {
    private int x;
    private int y;
    private int z;

    public static Position3D fromLocation(Location location) {
        return new Position3D(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    public Position3D add(int x, int y, int z) {
        return new Position3D(this.x + x, this.y + y, this.z + z);
    }

    public Position3D add(Position3D pos) {
        return new Position3D(x + pos.x, y + pos.y, z + pos.z);
    }

    public Position3D subtract(Position3D pos) {
        return new Position3D(x - pos.x, y - pos.y, z - pos.z);
    }

    public Position3D multiply(Position3D pos) {
        return new Position3D(x * pos.x, y * pos.y, z * pos.z);
    }

    public Position3D divide(Position3D pos) {
        if(pos.x == 0 || pos.y == 0 || pos.z == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return new Position3D(x / pos.x, y / pos.y, z / pos.z);
    }

    public Position3D above() {
        return new Position3D(x, y + 1, z);
    }

    public Position3D above(int amount) {
        return new Position3D(x, y + amount, z);
    }

    public Position3D below() {
        return new Position3D(x, y - 1, z);
    }

    public Position3D below(int amount) {
        return new Position3D(x, y - amount, z);
    }

    public Position3D north() {
        return new Position3D(x, y, z - 1);
    }

    public Position3D north(int amount) {
        return new Position3D(x, y, z - amount);
    }

    public Position3D south() {
        return new Position3D(x, y, z + 1);
    }

    public Position3D south(int amount) {
        return new Position3D(x, y, z + amount);
    }

    public Position3D west() {
        return new Position3D(x - 1, y, z);
    }

    public Position3D west(int amount) {
        return new Position3D(x - amount, y, z);
    }

    public Position3D east() {
        return new Position3D(x + 1, y, z);
    }

    public Position3D east(int amount) {
        return new Position3D(x + amount, y, z);
    }

    public double distance(Position3D pos) {
        double dx = x - pos.x;
        double dy = y - pos.y;
        double dz = z - pos.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public Position3D clone() {
        try {
            return (Position3D) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
