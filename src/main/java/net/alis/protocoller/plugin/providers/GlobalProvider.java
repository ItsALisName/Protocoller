package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.plugin.data.GlobalData;
import net.alis.protocoller.plugin.server.ProtocollerServer;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

public class GlobalProvider {

    private final GlobalData data;
    private final ProtocollerServer server;
    private final EventManagersProvider eventManager;

    private GlobalProvider() {
        this.data = new GlobalData();
        this.server = new ProtocollerServer(Bukkit.getServer(), MinecraftReflection.getMinecraftServer());
        this.eventManager = new EventManagersProvider();
    }

    public EventManagersProvider getEventManagers() {
        return eventManager;
    }

    public GlobalData getData() {
        return data;
    }

    public ProtocollerServer getServer() {
        return server;
    }

    public @Nullable static GlobalProvider instance() {
        return instance;
    }
    public static void init() {
        instance = new GlobalProvider();
        instance.server.getServerInjector().inject();
    }
    private static GlobalProvider instance;
}
