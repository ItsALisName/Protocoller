package net.alis.protocoller.plugin.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ProtocollerConfig {

    private static final @Getter List<String> bannedPlugin = new ArrayList<>();
    private static final @Getter List<String> bannedAuthors = new ArrayList<>();
    private static @Getter boolean listenerRegistrationNotify;
    private static @Getter boolean apiUserRegistrationNotify;
    private static @Getter boolean forceBStatsEnabled;
    private static @Getter boolean bStatsEnabled;
    private static @Getter boolean saveErrors;

    public static void load(FileConfiguration file) {
        listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", Boolean.class);
        bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        apiUserRegistrationNotify = ConfigUtils.getIfNotNull(file, "api-user-registration-notify", Boolean.class);
        forceBStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.allow-force-enable", Boolean.class);
        bStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.enabled", Boolean.class);
        saveErrors = ConfigUtils.getIfNotNull(file, "save-errors", Boolean.class);
    }

    public static void reload(FileConfiguration file) {
        bannedPlugin.clear(); bannedAuthors.clear();
        bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", Boolean.class);
        apiUserRegistrationNotify = ConfigUtils.getIfNotNull(file, "api-user-registration-notify", Boolean.class);
        forceBStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.allow-force-enable", Boolean.class);
        bStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.enabled", Boolean.class);
        saveErrors = ConfigUtils.getIfNotNull(file, "save-errors", Boolean.class);
    }

}
