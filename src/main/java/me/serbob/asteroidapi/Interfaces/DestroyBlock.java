package me.serbob.asteroidapi.Interfaces;

import org.bukkit.block.Block;

/**
 * Represents an interface for handling block destruction events.
 */
public interface DestroyBlock {

    /**
     * Target a block for destruction.
     *
     * @param block The block that is targeted to be destroyed.
     */
    void destroy(Block block);
}

