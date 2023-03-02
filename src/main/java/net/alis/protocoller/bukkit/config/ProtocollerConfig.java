package net.alis.protocoller.bukkit.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ProtocollerConfig {

    private final @Getter List<String> bannedPlugin;
    private final @Getter List<String> bannedAuthors;
    private @Getter boolean listenerRegistrationNotify;
    private @Getter boolean apiUserRegistrationNotify;

    public ProtocollerConfig(FileConfiguration file) {
        this.bannedPlugin = new ArrayList<>();
        this.bannedAuthors = new ArrayList<>();
        this.listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", boolean.class);
        this.bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        this.bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        this.apiUserRegistrationNotify = ConfigUtils.getIfNotNull(file, "api-user-registration-notify", boolean.class);
    }

    public void reload(FileConfiguration file) {
        this.bannedPlugin.clear(); this.bannedAuthors.clear();
        this.bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        this.bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        this.listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", boolean.class);
        this.apiUserRegistrationNotify = ConfigUtils.getIfNotNull(file, "api-user-registration-notify", boolean.class);
    }

}
