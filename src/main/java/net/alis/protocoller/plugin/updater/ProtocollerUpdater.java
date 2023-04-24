package net.alis.protocoller.plugin.updater;

import com.google.gson.JsonObject;
import net.alis.protocoller.Protocoller;
import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.samples.util.ChatDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Date;

public class ProtocollerUpdater {

    private static final String PROTOCOLLER_DOWNLOAD_LINK = "https://github.com/ItsALisName/Protocoller/releases/download/%version%/Protocoller.jar";
    private static final String SPIGOT_API_LINK = "https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=109150";

    private static boolean currentVersionIsLatest;
    private static boolean isFirstCheck = true;
    private static String latest;

    private static Date lastCheck;

    public static void init() {
        File dir = new File(ProtocollerConfig.getUpdateDownloadPath());
        if(!dir.exists()) dir.mkdir();
        if(ProtocollerConfig.isAutoUpdateCheck()){
            if (ProtocollerConfig.isAsyncUpdateChecking()) {
                ITaskAccess.get().doAsyncTimerTask(ProtocollerUpdater::checkUpdate, 100L, (ProtocollerConfig.getAutoUpdateCheckInterval() * 20L));
            } else {
                ITaskAccess.get().doSyncTimerTask(ProtocollerUpdater::checkUpdate, 100L, (ProtocollerConfig.getAutoUpdateCheckInterval() * 20L));
            }
        }
    }

    private static String readLatestVersion(BufferedReader reader) {
        return ChatDeserializer.GSON.fromJson(reader, JsonObject.class).get("current_version").getAsString();
    }

    public static void checkUpdate() {
        try {
            InputStreamReader input = new InputStreamReader(new URL(SPIGOT_API_LINK).openConnection().getInputStream());
            BufferedReader reader = new BufferedReader(input);
            latest = ProtocollerUpdater.readLatestVersion(reader);
        } catch (IOException e) {
            new ExceptionBuilder().getUpdaterExceptions().defineReason(e).checkLatestError().throwException();
        }
        if(!latest.equalsIgnoreCase(Protocoller.get().getVersion())) {
            if(ProtocollerConfig.isAutoUpdateDownload()) {
                LogsManager.get().getLogger().warn("A new plugin version has been detected (Current version \"" + Protocoller.get().getVersion() + "\", latest version \"" + latest + "\"). Download started in the folder on the path \"" + ProtocollerConfig.getUpdateDownloadPath() + "\"...");
                notifyOperators("&e[Protocoller] A new plugin version has been detected (Current version \"&6" + Protocoller.get().getVersion() + "&e\", latest version \"&6" + latest + "&e\"). Download started in the folder on the path \"&6" + ProtocollerConfig.getUpdateDownloadPath() + "&e\"...");
                try {
                    if(ProtocollerConfig.isAsyncFilesWorking()) {
                        ITaskAccess.get().doAsync(() -> {
                            try {
                                downloadLatest(latest);
                            } catch (IOException e) {
                                new ExceptionBuilder().getUpdaterExceptions().defineReason(e).downloadLatestError().throwException();
                            }
                        });
                    } else {
                        downloadLatest(latest);
                    }
                } catch (IOException e) {
                    new ExceptionBuilder().getUpdaterExceptions().defineReason(e).downloadLatestError().throwException();
                }
            } else {
                LogsManager.get().getLogger().warn("You are using an old version of the plugin! Current version \"" + Protocoller.get().getVersion() + "\", latest version \"" + latest + "\", please update the plugin.");
                currentVersionIsLatest = false;
                notifyOperators("&c[Protocoller] You are using an old version of the plugin! Current version \"&4" + Protocoller.get().getVersion() + "&c\", latest version \"&4" + latest + "&c\", please update the plugin.");
            }
        } else {
            currentVersionIsLatest = true;
            if(isFirstCheck) {
                isFirstCheck = false;
                LogsManager.get().getLogger().info("You are using the latest version of the plugin.");
            }
        }
        lastCheck = new Date();
    }

    private static void notifyOperators(String message) {
        if(ProtocollerConfig.isNotifyOperatorsOnUpdate()) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.isOp()) player.sendMessage(Utils.colors(message));
            }
        }
    }

    public static void notifyOperator(Player player) {
        if(!currentVersionIsLatest && Utils.isNotNullText(latest) && player.isOp()) {
            player.sendMessage(Utils.colors("&c[Protocoller] You are using an old version of the plugin! Current version \"&4" + Protocoller.get().getVersion() + "&c\", latest version \"&4" + latest + "&c\", please update the plugin."));
        }
    }

    private static void downloadLatest(@NotNull String latest) throws IOException {
        URL site = new URL(PROTOCOLLER_DOWNLOAD_LINK.replace("%version%", "protocoller-v" + latest.replace(".", "-")));
        File file = new File(ProtocollerConfig.getUpdateDownloadPath(), "Protocoller-" + latest + ".jar");
        if(file.exists()) file.createNewFile();
        ReadableByteChannel input = Channels.newChannel(site.openStream());
        FileOutputStream plugin = new FileOutputStream(file);
        plugin.getChannel().transferFrom(input, 0, Long.MAX_VALUE);
        plugin.close();
        LogsManager.get().getLogger().info("The new version of the plugin has been successfully uploaded to the folder on the path \"" + ProtocollerConfig.getUpdateDownloadPath() + "\"");
        notifyOperators("&a[Protocoller] The new version of the plugin has been successfully uploaded to the folder on the path \"" + ProtocollerConfig.getUpdateDownloadPath() + "\"");
    }

    public static String getLatest() {
        return latest;
    }

    public static Date getLastCheck() {
        return lastCheck;
    }
}
