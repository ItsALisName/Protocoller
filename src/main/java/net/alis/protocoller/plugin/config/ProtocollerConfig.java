package net.alis.protocoller.plugin.config;

import lombok.Getter;
import net.alis.protocoller.plugin.ProtocollerPlugin;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import org.bukkit.configuration.file.FileConfiguration;

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
    private static @Getter boolean autoUpdateCheck;
    private static @Getter int autoUpdateCheckInterval;
    private static @Getter boolean autoUpdateDownload = false;
    private static @Getter String updateDownloadPath;
    private static @Getter boolean notifyOperatorsOnUpdate = false;
    private static @Getter boolean asyncFilesWorking;
    private static @Getter boolean asyncUpdateChecking;

    public static void load(ProtocollerPlugin plugin) {
        FileConfiguration file = ConfigUtils.getConfigurationFile("general.yml");
        String configVersion = ConfigUtils.getConfigVersion(file);
        if(configVersion == null || !configVersion.equalsIgnoreCase("0.0.4")) {
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
        autoUpdateCheck = ConfigUtils.getIfNotNull(file, "auto-update.enabled", Boolean.class);
        autoUpdateCheckInterval = ConfigUtils.getIfNotNull(file, "auto-update.check-interval", Integer.class);
        if(autoUpdateCheck) {
            notifyOperatorsOnUpdate = ConfigUtils.getIfNotNull(file, "auto-update.notify-operators", Boolean.class);
            autoUpdateDownload = ConfigUtils.getIfNotNull(file, "auto-update.auto-download.enabled", Boolean.class);
            updateDownloadPath = ConfigUtils.getIfNotNull(file, "auto-update.auto-download.path", String.class);
        }
        asyncFilesWorking = ConfigUtils.getIfNotNull(file, "async.file-working", Boolean.class);
        asyncUpdateChecking = ConfigUtils.getIfNotNull(file, "async.update-checking", Boolean.class);
    }

    public static void reload() {
        if(!asyncFilesWorking) {
            reload$run(); return;
        }
        TaskSimplifier.get().preformAsync(ProtocollerConfig::reload$run);
    }

    private static void reload$run() {
        FileConfiguration file = ConfigUtils.getConfigurationFile("general.yml");
        String configVersion = ConfigUtils.getConfigVersion(file);
        if(configVersion == null || !configVersion.equalsIgnoreCase("0.0.4")) {
            new ExceptionBuilder().getConfigExceptions().differentVersionsError("general.yml", configVersion == null ? "N/A" : configVersion, "0.0.4").throwException();
        }
        bannedPlugin.clear(); bannedAuthors.clear();
        bannedPlugin.addAll(ConfigUtils.getIfNotNull(file, "banned-plugins", List.class));
        bannedAuthors.addAll(ConfigUtils.getIfNotNull(file, "banned-authors", List.class));
        listenerRegistrationNotify = ConfigUtils.getIfNotNull(file, "listeners-registration-notify", Boolean.class);
        clientRegistrationNotify = ConfigUtils.getIfNotNull(file, "client-registration-notify", Boolean.class);
        forceBStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.allow-force-enable", Boolean.class);
        bStatsEnabled = ConfigUtils.getIfNotNull(file, "bstats.enabled", Boolean.class);
        saveErrors = ConfigUtils.getIfNotNull(file, "save-errors", Boolean.class);
        autoUpdateCheck = ConfigUtils.getIfNotNull(file, "auto-update.enabled", Boolean.class);
        autoUpdateCheckInterval = ConfigUtils.getIfNotNull(file, "auto-update.check-interval", Integer.class);
        if(autoUpdateCheck) {
            notifyOperatorsOnUpdate = ConfigUtils.getIfNotNull(file, "auto-update.notify-operators", Boolean.class);
            autoUpdateDownload = ConfigUtils.getIfNotNull(file, "auto-update.auto-download.enabled", Boolean.class);
            updateDownloadPath = ConfigUtils.getIfNotNull(file, "auto-update.auto-download.path", String.class);
        }
        asyncFilesWorking = ConfigUtils.getIfNotNull(file, "async.file-working", Boolean.class);
        asyncUpdateChecking = ConfigUtils.getIfNotNull(file, "async.update-checking", Boolean.class);
    }

}
