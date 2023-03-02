package net.alis.protocoller.bukkit.providers;

import net.alis.protocoller.bukkit.config.ProtocollerConfig;
import net.alis.protocoller.bukkit.config.ConfigUtils;
import net.alis.protocoller.bukkit.data.GlobalData;
import net.alis.protocoller.bukkit.server.ProtocollerServer;
import org.bukkit.Bukkit;

public class GlobalProvider {

    private static GlobalProvider instance;

    private final GlobalData data;
    private final ProtocollerConfig config;
    private final ProtocollerServer server;
    private final EventManagersProvider eventManager;


    private GlobalProvider() {
        this.data = new GlobalData();
        this.config = new ProtocollerConfig(ConfigUtils.getConfigurationFile("general.yml"));
        this.server = new ProtocollerServer(Bukkit.getServer());
        this.eventManager = new EventManagersProvider();
    }

    public EventManagersProvider getEventManager() {
        return eventManager;
    }

    public GlobalData getData() {
        return data;
    }

    public ProtocollerConfig getConfig() {
        return config;
    }

    public ProtocollerServer getServer() {
        return server;
    }

    public static GlobalProvider instance() {
        return instance;
    }

    public static void init() {
        instance = new GlobalProvider();
        instance.server.getServerInjector().inject();
    }
}
