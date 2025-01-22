package me.serbob.asteroidapi.interfaces;

import me.serbob.asteroidapi.enums.HandEnum;
import me.serbob.asteroidapi.enums.Pose;
import org.bukkit.Material;

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
