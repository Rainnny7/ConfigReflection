package me.braydon.config;

import me.braydon.config.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Braydon
 */
public final class ConfigReflection extends JavaPlugin {
    @Override
    public void onEnable() {
        // Save and reload the config
        saveDefaultConfig();
        reloadConfig();

        // Initialize the config class
        try {
            Config.initialize(this);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        Bukkit.broadcastMessage("join msg = " + Config.JOIN_MESSAGE);
    }
}