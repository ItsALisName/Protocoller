package net.alis.protocoller.plugin.config;

import lombok.Getter;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ProtocollerConfig {

    private static final @Getter List<String> bannedPlugin = new ArrayList<>();
    private static final @Getter List<String> bannedAuthors = new ArrayList<>();
    private static @Getter boolean listenerRegistrationNotify;
    private static @Getter boolean clientRegistrationNotify;
    private static @Getter boolean forceBStatsEnabled;
    private static @Getter boolean bStatsEnabled;
    private static @Getter boolean saveErrors;

    public static void load(Plugin plugin, FileConfiguration file) {
        String configVersion = ConfigUtils.getConfigVersion(file);
        if(configVersion == null || !configVersion.equalsIgnoreCase("0.0.2")) {
            ConfigUpdater.updateConfiguration(plugin, "general.yml", ConfigUtils.getFile("general.yml"));
            file = ConfigUtils.getConfigurationFile("general.yml");
        }
        listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", Boolean.class);
        bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        clientRegistrationNotify = ConfigUtils.getIfNotNull(file, "client-registration-notify", Boolean.class);
        forceBStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.allow-force-enable", Boolean.class);
        bStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.enabled", Boolean.class);
        saveErrors = ConfigUtils.getIfNotNull(file, "save-errors", Boolean.class);
    }

    public static void reload(FileConfiguration file) {
        String configVersion = ConfigUtils.getConfigVersion(file);
        if(configVersion == null || !configVersion.equalsIgnoreCase("0.0.2")) {
            new ExceptionBuilder().getConfigExceptions().differentVersionsError("general.yml", configVersion == null ? "N/A" : configVersion, "0.0.2").throwException();
        }
        bannedPlugin.clear(); bannedAuthors.clear();
        bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", Boolean.class);
        clientRegistrationNotify = ConfigUtils.getIfNotNull(file, "client-registration-notify", Boolean.class);
        forceBStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.allow-force-enable", Boolean.class);
        bStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.enabled", Boolean.class);
        saveErrors = ConfigUtils.getIfNotNull(file, "save-errors", Boolean.class);
    }

}
