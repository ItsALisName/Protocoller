package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.v0_0_5.server.ProtocolServer;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class IProtocolAccess {

    private final ProtocolServer server;
    private final EventManagersProvider eventManager;

    private IProtocolAccess() {
        this.server = new ProtocolServer(Bukkit.getServer(), MinecraftReflection.getMinecraftServer());
        this.eventManager = new EventManagersProvider();
    }

    public EventManagersProvider getEventManagers() {
        return eventManager;
    }

    public ProtocolServer getServer() {
        return server;
    }

    public NetworkPlayer getPlayer(String name) {
        return this.server.getPlayer(name);
    }

    public NetworkPlayer getPlayer(UUID uuid) {
        return this.server.getPlayer(uuid);
    }

    public @Nullable static IProtocolAccess get() {
        return instance;
    }
    public static void init() {
        instance = new IProtocolAccess();
    }
    private static IProtocolAccess instance;
}
