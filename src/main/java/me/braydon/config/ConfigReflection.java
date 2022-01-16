package me.braydon.config;

import me.braydon.config.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Braydon
 */
public final class ConfigReflection extends JavaPlugin implements Listener {
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

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(String.format(Config.JOIN_MESSAGE, player.getName())); // Send the join message
    }
}