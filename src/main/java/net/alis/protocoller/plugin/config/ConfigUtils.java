package net.alis.protocoller.plugin.config;

import net.alis.protocoller.plugin.exception.ConfigException;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ConfigUtils {

    public static <PARAM> PARAM getIfNotNull(@NotNull FileConfiguration file, String path, Class<?> requiredType) {
        Object response = file.get(path);
        if(response != null) {
            if(requiredType.isAssignableFrom(response.getClass())) {
                return (PARAM) response;
            }
            return new ExceptionBuilder().getConfigExceptions().wrongType(file.getCurrentPath(), path, requiredType, response.getClass()).throwException();
        }
        return new ExceptionBuilder().showStackTrace(false).getConfigExceptions().unknownPath(file.getCurrentPath(), path).throwException();
    }

    @Contract("_ -> new")
    public static @NotNull FileConfiguration getConfigurationFile(String name) {
        return YamlConfiguration.loadConfiguration(getFile(name));
    }

    public static @NotNull File getFile(String name) {
        return new File("plugins/Protocoller/", name);
    }

    public static void createFiles(Plugin plugin, boolean force) {
        if(!getFile("general.yml").exists())
            plugin.saveResource("general.yml", force);
    }

    protected static @Nullable String getConfigVersion(FileConfiguration config) {
        try {
            return getIfNotNull(config, "config-version", String.class);
        } catch (Exception e) {
            return null;
        }
    }
}
