package net.alis.protocoller.bukkit.config;

import net.alis.protocoller.bukkit.exceptions.ConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    public static <PARAM> PARAM getIfNotNull(FileConfiguration file, String path, Class<?> requiredType) {
        if(file.get(path) != null) {
            Object response = file.get(path);
            if(requiredType == List.class || requiredType == ArrayList.class) {
                if(response.getClass() == List.class || response.getClass() == ArrayList.class) {
                    return (PARAM) response;
                }
            }
            if(requiredType == boolean.class || requiredType == Boolean.class) {
                if(response.getClass() == Boolean.class || response.getClass() == boolean.class) {
                    return (PARAM) response;
                }
            }
            if(response.getClass() == requiredType) {
                return (PARAM) response;
            }
            throw new ConfigurationException(file.toString(), path, new String[]{response.getClass().getSimpleName(), requiredType.getSimpleName()});
        }
        throw new ConfigurationException(file.getName(), path);
    }

    public static FileConfiguration getConfigurationFile(String name) {
        return YamlConfiguration.loadConfiguration(getFile(name));
    }

    public static @NotNull File getFile(String name) {
        return new File("plugins/Protocoller/", name);
    }
}
