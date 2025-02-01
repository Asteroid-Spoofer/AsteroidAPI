package me.serbob.asteroidapi.utils;

import me.serbob.asteroidapi.blocks.BlockTag;
import me.serbob.asteroidapi.pathfinding.Position3D;
import me.serbob.asteroidapi.utils.math.MathUtils;
import me.serbob.asteroidapi.utils.math.Interval;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class LocationUtils {
    public static Vector getDeltaVector(Position3D pos1, Position3D pos2) {
        double dx = Math.abs(pos1.getX() - pos2.getX());
        double dy = Math.abs(pos1.getY() - pos2.getY());
        double dz = Math.abs(pos1.getZ() - pos2.getZ());
        return new Vector(dx, dy, dz);
    }

    public static Vector getDeltaVector(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld())
            return new Vector(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        return getDeltaVector(loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ());
    }

    public static double getDistance(Position3D pos1, Position3D pos2) {
        Vector delta = getDeltaVector(pos1, pos2);
        return calculateDistance(delta);
    }

    public static double getDistance(Location loc1, Location loc2) {
        Vector delta = getDeltaVector(loc1, loc2);
        return calculateDistance(delta);
    }

    public static double getManhattanDistance(Location loc1, Location loc2) {
        Vector delta = getDeltaVector(loc1, loc2);
        return Math.sqrt(delta.getX() + delta.getY() + delta.getZ());
    }

    public static boolean isStandable(Position3D pos, World world) {
        return isStandable(pos.toLocation(world));
    }

    public static boolean isStandable(Location location) {
        return !isSolid(location)
                && !isSolid(location.clone().add(0, 1, 0))
                && isSolid(location.clone().add(0, -1, 0));
    }

    public static boolean isSolid(Position3D pos, World world) {
        return isSolid(pos.toLocation(world));
    }

    public static boolean isSolid(Location location) {
        Material type = location.getBlock().getType();
        return (type.isSolid() && !type.name().contains("TRAPDOOR"))
                || type.name().contains("LEAVES");
    }

    public static Vector getCenterBottom(Vector vector) {
        return new Vector(vector.getX() + 0.5, vector.getY(), vector.getZ() + 0.5);
    }

    public static boolean isWithinDistance(Vector vec1, Vector vec2, double distance) {
        return getSquaredDistance(vec1, vec2) < distance * distance;
    }

    public static double getSquaredDistance(Vector vec1, Vector vec2) {
        double dx = vec1.getX() - vec2.getX();
        double dy = vec1.getY() - vec2.getY();
        double dz = vec1.getZ() - vec2.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    public static Block findSolidBlock(Location location, BlockFace direction) {
        while (!BlockTag.SOLID.matches(location)) {
            location = location.getBlock().getRelative(direction).getLocation();
        }
        return location.getBlock();
    }

    public static Block getBlockInFront(Location location, int distance) {
        BlockIterator blocks = new BlockIterator(location, 1, distance);
        Block lastNonSolidBlock = null;
        while (blocks.hasNext()) {
            Block block = blocks.next();
            if (block.getType().isSolid()) {
                return lastNonSolidBlock;
            } else {
                lastNonSolidBlock = block;
            }
        }
        return null;
    }

    public static Block findAirBlock(Location location, BlockFace direction) {
        while (location.getBlock().getType() != Material.AIR) {
            location = location.getBlock().getRelative(direction).getLocation();
        }
        return location.getBlock();
    }

    public static boolean canReach(Location to, Location from) {
        return from.clone().add(0, 2, 0).distance(to) < 5;
    }

    private static Location validDownwardsSearch(Location location) {
        for (int i = 0; i <= 10; i++) {
            Location checkedUnder = location.clone().subtract(0,i,0);
            if (!checkedUnder.clone().add(0,1,0).getBlock().getType().isSolid()
                    && !checkedUnder.getBlock().getType().isSolid()) {
                return checkedUnder;
            }
        }
        return null;
    }

    public static Vector toLocalSpace(Vector vector, double yaw) {
        double angle = Math.toRadians(yaw);
        double x = vector.getX() * Math.cos(angle) + vector.getZ() * Math.sin(angle);
        double z = vector.getZ() * Math.cos(angle) - vector.getX() * Math.sin(angle);
        return new Vector(x, vector.getY(), z);
    }

    public static Location parseLocation(String string) {
        String[] parts = string.split(",");
        return new Location(
                Bukkit.getWorld(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3])
        );
    }

    public static String formatLocation(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }

    public static String formatLocationPrecise(Location location) {
        return MathUtils.round(location.getX(), 2) + ", " +
                MathUtils.round(location.getY(), 2) + ", " +
                MathUtils.round(location.getZ(), 2);
    }

    public static Location findRandomLocation(Location from, Interval interval, float directionBias) {
        Location loc = from.clone();
        int x = MathUtils.randomInt(interval.getMin(), interval.getMax()) * (Math.random() < directionBias ? 1 : -1);
        int z = MathUtils.randomInt(interval.getMin(), interval.getMax()) * (Math.random() < directionBias ? 1 : -1);

        /*
         * Pre 1.17: 256
         * Post 1.17: 320 (overworld)
         * Nether: 128
         */
        loc.add(x, 0, z);
        loc.setY(loc.getWorld().getMaxHeight());
        return findSolidBlock(loc, BlockFace.DOWN).getRelative(BlockFace.UP).getLocation();
    }

    private static Vector getDeltaVector(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Vector(
                Math.abs(x1 - x2),
                Math.abs(y1 - y2),
                Math.abs(z1 - z2)
        );
    }

    private static double calculateDistance(Vector delta) {
        double distance2d = Math.sqrt(delta.getX() * delta.getX() + delta.getZ() * delta.getZ());
        return Math.sqrt(distance2d * distance2d + delta.getY() * delta.getY());
    }
}
