package me.serbob.asteroidapi.actions.enums;

/**
 * Represents priority levels for actions with values ranging from 0.0 to 1.0
 */
public enum Priority {
    HIGHEST(1.0f),
    HIGH(0.75f),
    NORMAL(0.5f),
    LOW(0.25f),
    LOWEST(0.0f),
    CUSTOM(0.0f);

    private float value;

    Priority(float value) {
        this.value = value;
    }

    /**
     * Get the numerical value of this priority
     * @return float value between 0.0 and 1.0
     */
    public float getValue() {
        return value;
    }

    /**
     * Check if this priority is higher than another priority
     * @param other The priority to compare against
     * @return true if this priority is higher
     */
    public boolean isHigherThan(Priority other) {
        return this.value > other.getValue();
    }

    /**
     * Check if this priority is lower than another priority
     * @param other The priority to compare against
     * @return true if this priority is lower
     */
    public boolean isLowerThan(Priority other) {
        return this.value < other.getValue();
    }

    /**
     * Set a custom priority value
     * @param value The new value (must be between 0.0 and 1.0)
     * @return this Priority instance
     * @throws IllegalArgumentException if value is not between 0.0 and 1.0
     * @throws IllegalStateException if trying to modify non-CUSTOM priority
     */
    public Priority setValue(float value) {
        if (this != CUSTOM) {
            throw new IllegalStateException("Cannot modify non-CUSTOM priority value");
        }
        if (value < 0.0f || value > 1.0f) {
            throw new IllegalArgumentException("Priority value must be between 0.0 and 1.0");
        }
        this.value = value;
        return this;
    }

    /**
     * Compare this priority with another priority
     * @param other The priority to compare with
     * @return true if priorities are equal
     */
    public boolean equals(Priority other) {
        if (other == null) return false;
        return Math.abs(this.value - other.value) < 0.0001f; // Float comparison with epsilon
    }

    /**
     * Create a new CUSTOM priority with specified value
     * @param value The priority value between 0.0 and 1.0
     * @return A new CUSTOM priority
     */
    public static Priority custom(float value) {
        return CUSTOM.setValue(value);
    }

    /**
     * Get a string representation of the priority
     * @return String in format "PRIORITY_NAME(value)"
     */
    @Override
    public String toString() {
        return name() + "(" + value + ")";
    }
}
