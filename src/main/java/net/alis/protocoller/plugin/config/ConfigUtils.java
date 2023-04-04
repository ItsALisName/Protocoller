package net.alis.protocoller.plugin.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ConfigUtils {

    @Nullable
    public static <PARAM> PARAM getIfNotNull(@NotNull FileConfiguration file, String path, Class<?> requiredType) {
        Object response = file.get(path);
        if(response != null) {
            return (PARAM) response;
        }
        return null;
    }

    @Contract("_ -> new")
    public static @NotNull FileConfiguration getConfigurationFile(String name) {
        return YamlConfiguration.loadConfiguration(getFile(name));
    }

    public static @NotNull File getFile(String name) {
        return new File("plugins/Protocoller/", name);
    }
}
