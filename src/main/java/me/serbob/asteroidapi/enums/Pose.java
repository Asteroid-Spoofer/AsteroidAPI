package me.serbob.asteroidapi.enums;

public enum Pose {
    /**
     * Default standing position with full height
     */
    STANDING(1F),

    /**
     * Crouching position with reduced height (0.5 blocks)
     */
    SNEAKING(0.5F),

    /**
     * Horizontal position with minimal height
     * Used when sleeping in beds
     */
    SLEEPING(0F),

    /**
     * Swimming position with full height
     * Used while swimming or in water
     */
    SWIMMING(1F),

    /**
     * Flying position with full height
     * Used when gliding with elytra
     */
    FLYING(1F),

    /**
     * Special animation pose with full height
     * Used when throwing tridents
     */
    SPINNING(1F),

    /**
     * Death animation pose with minimal height
     * Used when player dies or takes fatal damage
     */
    DYING(0F);

    private final float speedModifier;

    Pose(float speedModifier) {
        this.speedModifier = speedModifier;
    }

    public float getSpeedModifier() {
        return speedModifier;
    }
}
