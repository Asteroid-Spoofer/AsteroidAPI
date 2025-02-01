package me.serbob.asteroidapi.utils.math;

import lombok.Getter;

@Getter
public class Interval {
    private int min;
    private int max;

    public Interval(int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("Min cannot be greater than max");

        this.min = min;
        this.max = max;
    }

    public static Interval of(int min, int max) {
        return new Interval(min, max);
    }

    public boolean contains(int value) {
        return value >= min && value <= max;
    }

    public boolean overlaps(Interval other) {
        return min <= other.max && other.min <= max;
    }

    public int clamp(int value) {
        return Math.min(Math.max(value, min), max);
    }

    public void setMin(int min) {
        if (min > max)
            throw new IllegalArgumentException("Min cannot be greater than max");

        this.min = min;
    }

    public void setMax(int max) {
        if (max < min)
            throw new IllegalArgumentException("Max cannot be less than min");

        this.max = max;
    }

    public int getSize() {
        return max - min + 1;
    }
}
