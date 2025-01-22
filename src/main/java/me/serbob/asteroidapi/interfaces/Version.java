package me.serbob.asteroidapi.interfaces;

import me.serbob.asteroidapi.enums.MinecraftVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating the Minecraft version compatibility of a class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Version {

    /**
     * Specifies the Minecraft version compatibility for the annotated class.
     *
     * @return The Minecraft version compatibility.
     */
    MinecraftVersion value();
}
