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
    public static final String JOIN_MESSAGE = "Hello, %s!";

    public static void initialize(@NonNull ConfigReflection plugin) throws IllegalAccessException {
        FileConfiguration config = plugin.getConfig();
        for (Field field : Config.class.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigOption.class)) { // If the field is not annotated with @ConfigOption, skip it
                continue;
            }
            Object defaultValue = field.get(null); // The default value of the config option
            ConfigOption option = field.getAnnotation(ConfigOption.class);
            String path = option.path(); // The path of the config option
            Object value = config.get(path); // The value of the config option in the config file
            if (value == null) { // If the value from the config is null, use the default value
                value = defaultValue;
            }
            field.set(null, value); // Set the field value to the config value
        }
    }
}