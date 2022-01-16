package me.braydon.config.config;

import lombok.NonNull;
import me.braydon.config.ConfigReflection;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;

/**
 * @author Braydon
 */
public final class Config {
    @ConfigOption(path = "join-message")
    public static String JOIN_MESSAGE = "Hello, %s!";

    /**
     * Initialize the config by iterating over the fields
     * of the class that are annotated with @ConfigOption
     * and setting them to the values in the config file.
     * <p>
     * If the config value is null, the value of the
     * field will be used as the default value
     * </p>
     *
     * @param plugin the plugin owner of the config
     * @throws IllegalAccessException if the field is not accessible
     * @see ConfigOption for config option
     */
    public static void initialize(@NonNull ConfigReflection plugin) throws IllegalAccessException {
        FileConfiguration config = plugin.getConfig();

        int loaded = 0;
        boolean dirty = false;
        for (Field field : Config.class.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigOption.class)) { // If the field is not annotated with @ConfigOption, skip it
                continue;
            }
            Object defaultValue = field.get(null); // The default value of the config option
            ConfigOption option = field.getAnnotation(ConfigOption.class);
            String path = option.path(); // The path of the config option
            Object value = config.get(path); // The value of the config option in the config file
            if (value == null) { // If the value from the config is null, use the default value
                plugin.getLogger().warning(String.format("Config value for %s was null, using the default value of %s", path, defaultValue));
                config.set(path, defaultValue); // Set the config value to the default value
                value = defaultValue;
                dirty = true; // Mark the config as dirty so it will be saved
            }
            field.set(null, value); // Set the field value to the config value
            loaded++; // Increment the loaded config option count
        }
        if (dirty) { // If the config is dirty, save it
            plugin.saveConfig();
        }
        plugin.getLogger().info(String.format("%s config value(s) were loaded", loaded));
    }
}