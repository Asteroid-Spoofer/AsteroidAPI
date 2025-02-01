package me.serbob.asteroidapi.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {
    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double round(double value) {
        return round(value, 0);
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException("Places cannot be negative");

        if (!isFinite(value))
            return value;

        if (places == 0)
            return Math.round(value);

        try {
            return BigDecimal.valueOf(value)
                    .setScale(places, RoundingMode.HALF_UP)
                    .doubleValue();
        } catch (NumberFormatException e) {
            return value;
        }
    }

    public static boolean isFinite(double value) {
        return Math.abs(value) <= Double.MAX_VALUE;
    }

    public static double remap(double value, double maxValue, double minTarget, double maxTarget) {
        return minTarget + (value / maxValue) * (maxTarget - minTarget);
    }

    public static int floor(double value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }
}
