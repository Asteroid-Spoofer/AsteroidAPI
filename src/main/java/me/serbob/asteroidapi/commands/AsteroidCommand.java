package me.serbob.asteroidapi.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface AsteroidCommand {
    boolean execute(CommandSender sender, String label, String args[]);
    List<String> tabComplete(CommandSender sender, String[] args);
}
