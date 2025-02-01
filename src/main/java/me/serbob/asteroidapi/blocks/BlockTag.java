package me.serbob.asteroidapi.blocks;

import me.serbob.asteroidapi.pathfinding.Position3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public enum BlockTag {
    SOLID {
        @Override
        protected boolean checkMaterial(Material material) {
            String name = material.name().toLowerCase();
            return material.isSolid() || name.contains("leaves");
        }
    },

    LIQUID {
        @Override
        protected boolean checkMaterial(Material material) {
            return material == Material.WATER || material == Material.LAVA;
        }
    },

    CLIMBABLE {
        @Override
        protected boolean checkMaterial(Material material) {
            String name = material.name().toLowerCase();
            return material == Material.LADDER || name.contains("vine");
        }
    };

    public boolean matches(Position3D position, World world) {
        return matches(position.toLocation(world));
    }

    public boolean matches(Location location) {
        return matches(location.getBlock());
    }

    public boolean matches(Block block) {
        return matches(block.getType());
    }

    public boolean matches(Material material) {
        return checkMaterial(material);
    }

    protected abstract boolean checkMaterial(Material material);
}
