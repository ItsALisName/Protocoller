package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.plugin.v0_0_4.server.ProtocolServer;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

public class GlobalProvider {

    private final ProtocolServer server;
    private final EventManagersProvider eventManager;

    private GlobalProvider() {
        this.server = new ProtocolServer(Bukkit.getServer(), MinecraftReflection.getMinecraftServer());
        this.eventManager = new EventManagersProvider();
    }

    public EventManagersProvider getEventManagers() {
        return eventManager;
    }

    public ProtocolServer getServer() {
        return server;
    }

    public @Nullable static GlobalProvider get() {
        return instance;
    }
    public static void init() {
        instance = new GlobalProvider();
    }
    private static GlobalProvider instance;
}
