package me.serbob.asteroidapi.Interfaces;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface FakePlayerInventory {
    void select(int slot);
    void drop();
    void drop(int slot);
    void setHelmet(ItemStack itemStack);
    void setChestplate(ItemStack itemStack);
    void setLeggings(ItemStack itemStack);
    void setBoots(ItemStack itemStack);
    void setOffhand(ItemStack itemStack);
    void setMainHand(ItemStack itemStack);
    int getSelectedSlot();
    ItemStack getSelectedItem();
    ItemStack getOffhandItem();
    ItemStack getMainhand();
    int addItem(ItemStack itemStack);
    void removeItem(ItemStack itemStack);
    boolean contains(ItemStack itemStack);
    boolean contains(Material material);
    ItemStack getFirst(Material material);
    ItemStack[] getContents();
    ItemStack[] getHotbar();
}
