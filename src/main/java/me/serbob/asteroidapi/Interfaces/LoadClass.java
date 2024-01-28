package me.serbob.asteroidapi.Interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface LoadClass {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Priority {
        /**
         * Priority level for loading the class. Higher values indicate higher priority.
         *
         * @return Priority level for loading the class.
         */
        int value();
    }
}