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

    public Logger getLogger(Thread thread) {
        return LogManager.getLogger("Protocoller/" + thread.getName());
    }

    public final void sendPreloadingStartMessage() {
        Logger log = getLogger(Thread.currentThread());
        logger.log(Level.INFO, "[*]============================================================[*]");
        logger.log(Level.INFO, "[*]       Preloading has started! Collecting information...");
        logger.log(Level.INFO, "[*]============================================================[*]");
    }

    public final void sendPreloadingFinishedMessage(String pluginVersion) {
        Logger log = getLogger(Thread.currentThread());
        log.log(Level.INFO, "[*]============================================================[*]");
        log.log(Level.INFO, "[*]      ____             __                   ____         ");
        log.log(Level.INFO, "[*]     / __ \\_________  / /_____  _________  / / /__  _____");
        log.log(Level.INFO, "[*]    / /_/ / ___/ __ \\/ __/ __ \\/ ___/ __ \\/ / / _ \\/ ___/");
        log.log(Level.INFO, "[*]   / ____/ /  / /_/ / /_/ /_/ / /__/ /_/ / / /  __/ /    ");
        log.log(Level.INFO, "[*]  /_/   /_/   \\____/\\__/\\____/\\___/\\____/_/_/\\___/_/     ");
        log.log(Level.INFO, "[*]                            by ALis                           ");
        log.log(Level.INFO, "[*]============================================================[*]");
        log.log(Level.INFO, "[*]                      Preloading finished.                     ");
        log.log(Level.INFO, "[*] Information:                                                  ");
        log.log(Level.INFO, "[*]  * Server Version: " + GlobalProvider.instance().getServer().getVersion().asString() + "(Protocol: " + GlobalProvider.instance().getServer().getVersion().getProtocol() + ")");
        log.log(Level.INFO, "[*]  * Plugin Version: " + pluginVersion);
        log.log(Level.INFO, "[*]  * Java Version: " + InitialData.INSTANCE.getJavaVersion());
        log.log(Level.INFO, "[*]  * JDK Version: " + InitialData.INSTANCE.getJdkVersion());
        log.log(Level.INFO, "[*]  * OS Name/Architecture: " + InitialData.INSTANCE.getOsName() + " / " + InitialData.INSTANCE.getOsArchitecture());
        log.log(Level.INFO, "[*]  * OS Version: " + InitialData.INSTANCE.getOsVersion());
        log.log(Level.INFO, "[*]============================================================[*]");
    }

    public final void sendRegisteredListenerMessage(String user, String version, String lType) {
        if (GlobalProvider.instance().getConfig().isListenerRegistrationNotify()) {
            Logger log = getLogger(Thread.currentThread());
            log.log(Level.INFO, "[*]=============================== [Protocoller] =============================[*]");
            log.log(Level.INFO, "[*]                 A new packet listener has been registered!");
            log.log(Level.INFO, "[*] Who registered: " + user + "(Version: " + version + ")");
            log.log(Level.INFO, "[*] Packet listener type: " + lType);
            log.log(Level.INFO, "[*]=============================== [Protocoller] =============================[*]");
        }
    }

    public final void sendRegisteredUserNotify(String user, String version, String authors) {
        if(GlobalProvider.instance().getConfig().isApiUserRegistrationNotify()) {
            Logger log = getLogger(Thread.currentThread());
            log.log(Level.INFO, "[*]=============================== [Protocoller] =============================[*]");
            log.log(Level.INFO, "[*]                          A new api user registered");
            log.log(Level.INFO, "[*] Plugin: " + user + "(Version:  " + version + ", Authors: " + authors + ")");
            log.log(Level.INFO, "[*]=============================== [Protocoller] =============================[*]");
        }
    }

    public static void init() {
        INSTANCE = new LogsManager();
    }

    public static LogsManager get() {
        return INSTANCE;
    }

    LogsManager() {
        this.logger = LogManager.getLogger("Protocoller/" + Thread.currentThread().getName());
    }
}
