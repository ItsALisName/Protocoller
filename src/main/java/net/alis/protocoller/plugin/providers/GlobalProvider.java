package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.plugin.config.ProtocollerConfig;
import net.alis.protocoller.plugin.config.ConfigUtils;
import net.alis.protocoller.plugin.data.GlobalData;
import net.alis.protocoller.plugin.server.ProtocollerServer;
import org.bukkit.Bukkit;

public class GlobalProvider {

    private static GlobalProvider instance;

    private final GlobalData data;
    private final ProtocollerServer server;
    private final EventManagersProvider eventManager;


    private GlobalProvider() {
        this.data = new GlobalData();
        this.server = new ProtocollerServer(Bukkit.getServer());
        this.eventManager = new EventManagersProvider();
    }

    public EventManagersProvider getEventManager() {
        return eventManager;
    }

    public GlobalData getData() {
        return data;
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
