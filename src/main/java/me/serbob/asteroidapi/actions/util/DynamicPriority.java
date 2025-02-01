package me.serbob.asteroidapi.actions.util;

import me.serbob.asteroidapi.actions.enums.Priority;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles dynamic priority calculations with modifiers for actions
 */
public class DynamicPriority {
    private Priority basePriority;
    private final Map<String, Float> modifiers;
    private float cachedValue;
    private boolean isDirty;

    /**
     * Creates a new DynamicPriority with a base priority
     * @param basePriority The initial priority level
     */
    public DynamicPriority(Priority basePriority) {
        this.basePriority = basePriority;
        this.modifiers = new HashMap<>();
        this.isDirty = true;
    }

    /**
     * Adds or updates a priority modifier
     * @param key The modifier identifier
     * @param value The modifier value (multiplier)
     * @throws IllegalArgumentException if value is negative
     */
    public void addModifier(String key, float value) {
        if (value < 0) {
            throw new IllegalArgumentException("Modifier value cannot be negative");
        }
        modifiers.put(key, value);
        isDirty = true;
    }

    /**
     * Removes a priority modifier
     * @param key The modifier identifier
     * @return true if the modifier was removed
     */
    public boolean removeModifier(String key) {
        if (modifiers.remove(key) != null) {
            isDirty = true;
            return true;
        }
        return false;
    }

    /**
     * Gets the base priority
     * @return The base Priority
     */
    public Priority getBasePriority() {
        return basePriority;
    }

    /**
     * Sets a new base priority
     * @param priority The new Priority level
     */
    public void setBasePriority(Priority priority) {
        if (priority != basePriority) {
            this.basePriority = priority;
            isDirty = true;
        }
    }

    /**
     * Gets all current modifiers
     * @return Unmodifiable map of modifiers
     */
    public Map<String, Float> getModifiers() {
        return Collections.unmodifiableMap(modifiers);
    }

    /**
     * Calculates the final priority value with all modifiers
     * @return The calculated priority value
     */
    public float calculate() {
        if (!isDirty) {
            return cachedValue;
        }

        float result = basePriority.getValue();
        if (!modifiers.isEmpty()) {
            result *= modifiers.values().stream()
                    .reduce(1f, (a, b) -> a * b);
        }

        // Ensure result stays within valid range
        cachedValue = Math.min(1.0f, Math.max(0.0f, result));
        isDirty = false;

        return cachedValue;
    }

    /**
     * Checks if this priority is higher than another
     * @param other The priority to compare against
     * @return true if this priority is higher
     */
    public boolean isHigherThan(DynamicPriority other) {
        return this.calculate() > other.calculate();
    }

    /**
     * Checks if this priority is equal to another
     * @param other The priority to compare against
     * @return true if priorities are equal
     */
    public boolean equals(DynamicPriority other) {
        if (other == null) return false;
        return Math.abs(this.calculate() - other.calculate()) < 0.0001f;
    }

    /**
     * Clears all modifiers
     */
    public void clearModifiers() {
        if (!modifiers.isEmpty()) {
            modifiers.clear();
            isDirty = true;
        }
    }
}
