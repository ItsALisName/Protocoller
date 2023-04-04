package net.alis.protocoller.plugin.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ProtocollerConfig {

    private final @Getter List<String> bannedPlugin;
    private final @Getter List<String> bannedAuthors;
    private @Getter boolean listenerRegistrationNotify;
    private @Getter boolean apiUserRegistrationNotify;
    private @Getter boolean forceBStatsEnabled;
    private @Getter boolean bStatsEnabled;

    public ProtocollerConfig(FileConfiguration file) {
        this.bannedPlugin = new ArrayList<>();
        this.bannedAuthors = new ArrayList<>();
        this.listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", Boolean.TYPE);
        this.bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        this.bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        this.apiUserRegistrationNotify = ConfigUtils.getIfNotNull(file, "api-user-registration-notify", Boolean.TYPE);
        this.forceBStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.allow-force-enable", Boolean.TYPE);
        this.bStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.enabled", Boolean.TYPE);
    }

    public void reload(FileConfiguration file) {
        this.bannedPlugin.clear(); this.bannedAuthors.clear();
        this.bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        this.bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        this.listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", Boolean.TYPE);
        this.apiUserRegistrationNotify = ConfigUtils.getIfNotNull(file, "api-user-registration-notify", Boolean.TYPE);
        this.forceBStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.allow-force-enable", Boolean.TYPE);
        this.bStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.enabled", Boolean.TYPE);
    }

}
