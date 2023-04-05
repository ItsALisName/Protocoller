package net.alis.protocoller.plugin.config;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigUtils {

    public static <PARAM> PARAM getIfNotNull(@NotNull FileConfiguration file, String path, Class<?> requiredType) {
        Object response = file.get(path);
        if(response != null) {
            if(requiredType.isAssignableFrom(response.getClass())) {
                return (PARAM) response;
            }
            return new ExceptionBuilder().getConfigExceptions().wrongType(file.getName(), path, requiredType, response.getClass()).throwException();
        }
        return new ExceptionBuilder().showStackTrace(false).getConfigExceptions().unknownPath(file.getName(), path).throwException();
    }

    @Contract("_ -> new")
    public static @NotNull FileConfiguration getConfigurationFile(String name) {
        return YamlConfiguration.loadConfiguration(getFile(name));
    }

    public static @NotNull File getFile(String name) {
        return new File("plugins/Protocoller/", name);
    }

    public static void createFiles(Plugin plugin) {
        if(!getFile("general.yml").exists())
            plugin.saveResource("general.yml", false);
    }
}
