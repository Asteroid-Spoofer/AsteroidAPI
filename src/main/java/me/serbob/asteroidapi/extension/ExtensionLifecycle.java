package me.serbob.asteroidapi.extension;

import me.serbob.asteroidapi.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class ExtensionLifecycle {
    private Plugin plugin;
    private Logger logger;
    private File dataFolder;
    private File configFile;
    private FileConfiguration config;
    private File jarFile;

    public void initialize(Plugin plugin, Logger logger, File jarFile) {
        this.plugin = plugin;
        this.logger = logger;
        this.jarFile = jarFile;
        this.dataFolder = new File(plugin.getDataFolder(), "Extensions/" + getClass().getSimpleName());
        this.configFile = new File(dataFolder, "config.yml");
    }

    public abstract void onEnable();
    public abstract void onDisable();

    public Plugin getPlugin() {
        return plugin;
    }

    public Logger getLogger() {
        return logger;
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);

        try (JarFile jar = new JarFile(jarFile)) {
            JarEntry entry = jar.getJarEntry("config.yml");
            if (entry != null) {
                try (InputStream defaultStream = jar.getInputStream(entry)) {
                    config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream)));
                }
            }
        } catch (IOException e) {
            logger.error("Could not load default config for extension " + getClass().getSimpleName());
        }
    }

    public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            logger.error("Could not save config to " + configFile + ": " + ex.getMessage());
        }
    }

    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            dataFolder.mkdirs();
            try (JarFile jar = new JarFile(jarFile)) {
                JarEntry entry = jar.getJarEntry("config.yml");
                if (entry != null) {
                    try (InputStream in = jar.getInputStream(entry)) {
                        Files.copy(in, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            } catch (IOException e) {
                logger.error("Could not save default config for extension " + getClass().getSimpleName());
            }
        }
    }

    public File getDataFolder() {
        return dataFolder;
    }
}
