package net.alis.protocoller.bukkit.managers;

import net.alis.protocoller.bukkit.ProtocollerPlugin;
import net.alis.protocoller.bukkit.data.InitialData;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;

public class LogsManager {

    private static LogsManager INSTANCE;

    private final Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public void info(String... lines) {
        getLogger().info("[*]=============================== [Protocoller] =============================[*]");
        for(String line : lines) getLogger().info("[*] " + line);
        getLogger().info("[*]=============================== [Protocoller] =============================[*]");
    }

    public void warning(String... lines) {
        getLogger().warn("[*]=============================== [Protocoller] =============================[*]");
        for(String line : lines) getLogger().warn("[*] " + line);
        getLogger().warn("[*]=============================== [Protocoller] =============================[*]");
    }

    public void error(String... lines) {
        getLogger().error("[*]=============================== [Protocoller] =============================[*]");
        for(String line : lines) getLogger().error("[*] " + line);
        getLogger().error("[*]=============================== [Protocoller] =============================[*]");
    }

    public void fatal(String... lines) {
        getLogger().fatal("[*]=============================== [Protocoller] =============================[*]");
        for(String line : lines) getLogger().fatal("[*] " + line);
        getLogger().fatal("[*]=============================== [Protocoller] =============================[*]");
    }

    public final void sendPreloadingStartMessage() {
        logger.log(Level.INFO, "[*]============================================================[*]");
        logger.log(Level.INFO, "[*]       Preloading has started! Collecting information...");
        logger.log(Level.INFO, "[*]============================================================[*]");
    }

    public final void sendPreloadingFinishedMessage(String pluginVersion) {
        logger.log(Level.INFO, "[*]============================================================[*]");
        logger.log(Level.INFO, "[*]      ____             __                   ____         ");
        logger.log(Level.INFO, "[*]     / __ \\_________  / /_____  _________  / / /__  _____");
        logger.log(Level.INFO, "[*]    / /_/ / ___/ __ \\/ __/ __ \\/ ___/ __ \\/ / / _ \\/ ___/");
        logger.log(Level.INFO, "[*]   / ____/ /  / /_/ / /_/ /_/ / /__/ /_/ / / /  __/ /    ");
        logger.log(Level.INFO, "[*]  /_/   /_/   \\____/\\__/\\____/\\___/\\____/_/_/\\___/_/     ");
        logger.log(Level.INFO, "[*]                            by ALis                           ");
        logger.log(Level.INFO, "[*]============================================================[*]");
        logger.log(Level.INFO, "[*]                      Preloading finished.                     ");
        logger.log(Level.INFO, "[*] Information:                                                  ");
        logger.log(Level.INFO, "[*]  * Server Version: " + GlobalProvider.instance().getServer().getVersion().asString() + "(Protocol: " + GlobalProvider.instance().getServer().getVersion().getProtocol() + ")");
        logger.log(Level.INFO, "[*]  * Plugin Version: " + pluginVersion);
        logger.log(Level.INFO, "[*]  * Java Version: " + InitialData.INSTANCE.getJavaVersion());
        logger.log(Level.INFO, "[*]  * JDK Version: " + InitialData.INSTANCE.getJdkVersion());
        logger.log(Level.INFO, "[*]  * OS Name/Architecture: " + InitialData.INSTANCE.getOsName() + " / " + InitialData.INSTANCE.getOsArchitecture());
        logger.log(Level.INFO, "[*]  * OS Version: " + InitialData.INSTANCE.getOsVersion());
        logger.log(Level.INFO, "[*]============================================================[*]");
    }

    public final void sendRegisteredListenerMessage(String user, String version, String lType) {
        if (GlobalProvider.instance().getConfig().isListenerRegistrationNotify()) {
            logger.log(Level.INFO, "[*]=============================== [Protocoller] =============================[*]");
            logger.log(Level.INFO, "[*]                 A new packet listener has been registered!");
            logger.log(Level.INFO, "[*] Who registered: " + user + "(Version: " + version + ")");
            logger.log(Level.INFO, "[*] Packet listener type: " + lType);
            logger.log(Level.INFO, "[*]=============================== [Protocoller] =============================[*]");
        }
    }

    public final void sendRegisteredUserNotify(String user, String version, String authors) {
        if(GlobalProvider.instance().getConfig().isApiUserRegistrationNotify()) {
            logger.log(Level.INFO, "[*]=============================== [Protocoller] =============================[*]");
            logger.log(Level.INFO, "[*]                          A new api user registered");
            logger.log(Level.INFO, "[*] Plugin: " + user + "(Version:  " + version + ", Authors: " + authors + ")");
            logger.log(Level.INFO, "[*]=============================== [Protocoller] =============================[*]");
        }
    }

    public static void init() {
        INSTANCE = new LogsManager();
    }

    public static LogsManager get() {
        return INSTANCE;
    }

    LogsManager() {
        this.logger = LogManager.getLogger("Protocoller");
    }
}
