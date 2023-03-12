package net.alis.protocoller.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    @Nullable
    public static <PARAM> PARAM getIfNotNull(@NotNull FileConfiguration file, String path, Class<?> requiredType) {
        if(file.get(path) != null) {
            Object response = file.get(path);
            if(requiredType == List.class || requiredType == ArrayList.class) {
                if(response.getClass() == List.class || response.getClass() == ArrayList.class) {
                    return (PARAM) response;
                }
            }
            if(requiredType == Boolean.TYPE) {
                if(response.getClass() == Boolean.TYPE) {
                    return (PARAM) response;
                }
            }
            if(response.getClass() == requiredType) {
                return (PARAM) response;
            }
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
