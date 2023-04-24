package net.alis.protocoller.plugin.config.configs;

import lombok.Getter;
import net.alis.protocoller.plugin.ProtocollerPlugin;
import net.alis.protocoller.plugin.config.ConfigUpdater;
import net.alis.protocoller.plugin.config.ConfigUtils;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.util.ITaskAccess;
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
    private static @Getter boolean asyncCommandExecution;
    private static @Getter boolean replacePluginsCommand;
    private static @Getter String commandsDenyMessage;
    private static @Getter boolean disableReloadCommand;
    private static @Getter boolean replaceVersionCommand;
    private static @Getter String versionCommandMessage;

    public static void load(ProtocollerPlugin plugin) {
        FileConfiguration file = ConfigUtils.getConfigurationFile("general.yml");
        String configVersion = ConfigUtils.getConfigVersion(file);
        if(configVersion == null || !configVersion.equalsIgnoreCase("0.0.5")) {
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
        replacePluginsCommand = ConfigUtils.getIfNotNull(file, "replace-plugins-command", Boolean.class);
        asyncCommandExecution = ConfigUtils.getIfNotNull(file, "async.command-execution", Boolean.class);
        commandsDenyMessage = ConfigUtils.getIfNotNull(file, "commands-deny-message", String.class);
        disableReloadCommand = ConfigUtils.getIfNotNull(file, "disable-reload-command", Boolean.class);
        replaceVersionCommand = ConfigUtils.getIfNotNull(file, "replace-version-command", Boolean.class);
        versionCommandMessage = ConfigUtils.getIfNotNull(file, "version-command-message", String.class);
    }

    public static void reload() {
        if(!asyncFilesWorking) {
            reload$run(); return;
        }
        ITaskAccess.get().doAsync(ProtocollerConfig::reload$run);
    }

    private static void reload$run() {
        FileConfiguration file = ConfigUtils.getConfigurationFile("general.yml");
        String configVersion = ConfigUtils.getConfigVersion(file);
        if(configVersion == null || !configVersion.equalsIgnoreCase("0.0.5")) {
            new ExceptionBuilder().getConfigExceptions().differentVersionsError("general.yml", configVersion == null ? "N/A" : configVersion, "0.0.5").throwException();
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
        asyncCommandExecution = ConfigUtils.getIfNotNull(file, "async.command-execution", Boolean.class);
        replacePluginsCommand = ConfigUtils.getIfNotNull(file, "replace-plugins-command", Boolean.class);
        commandsDenyMessage = ConfigUtils.getIfNotNull(file, "commands-deny-message", String.class);
        disableReloadCommand = ConfigUtils.getIfNotNull(file, "disable-reload-command", Boolean.class);
        replaceVersionCommand = ConfigUtils.getIfNotNull(file, "replace-version-command", Boolean.class);
        versionCommandMessage = ConfigUtils.getIfNotNull(file, "version-command-message", String.class);
    }

}
