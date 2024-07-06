package me.serbob.asteroidapi.Interfaces;

import me.serbob.asteroidapi.Enums.HandEnum;
import me.serbob.asteroidapi.Enums.Pose;
import me.serbob.asteroidapi.injection.Feature;
import org.bukkit.Material;

import java.util.List;

public interface Overrides {
    public void setRotation(float yaw, float pitch);
    public void startUsingItem(HandEnum hand);
    public boolean isUsingItem();
    public void stopUsingItem();
    public void swingHand(HandEnum hand);
    public int getItemCooldown(Material material);
    public void setItemCooldown(Material material, int ticks);
    public void setJumping(boolean isJumping);
    public boolean isJumping();
    public void setPose(Pose pose);
    public Pose getPose();
}
