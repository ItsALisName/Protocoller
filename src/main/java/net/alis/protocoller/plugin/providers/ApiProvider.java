package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.Protocoller;
import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.event.RegisteredPacketListener;
import net.alis.protocoller.event.async.*;
import net.alis.protocoller.event.impl.ManagerType;
import net.alis.protocoller.event.sync.*;
import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.events.RegisteredProtocollerListener;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.NetworkServer;
import net.alis.protocoller.plugin.v0_0_5.api.ProtocollerApi;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ApiProvider implements ProtocollerClient {

    private String name;
    private String version;
    private List<String> authors;
    private List<RegisteredProtocollerListener> totalListeners;

    public ApiProvider(@NotNull Plugin client) {
        if(ProtocollerConfig.getBannedPlugin().contains(client.getName())) {
            new ExceptionBuilder().getClientExceptions().bannedPlugin(client).throwException();
            return;
        }
        if(Utils.findSimilar(ProtocollerConfig.getBannedAuthors(), client.getDescription().getAuthors()).size() > 0) {
            new ExceptionBuilder().getClientExceptions().bannedAuthor(client).throwException();
            return;
        }
        ((ProtocollerApi) Protocoller.get()).getClients().get().add(this);
        LogsManager.get().sendRegisteredClientNotify(client.getName(), client.getDescription().getVersion(), String.join(", ", client.getDescription().getAuthors()));
        this.name = client.getName();
        this.version = client.getDescription().getVersion();
        this.authors = client.getDescription().getAuthors();
        this.totalListeners = new ArrayList<>();
    }

    @Override
    public NetworkServer getServer() {
        return IProtocolAccess.get().getServer();
    }

    @Override
    public PacketEventsManager getEventManager(ManagerType type) {
        switch (type) {
            case SYNCHRONOUS: return IProtocolAccess.get().getEventManagers().getSync();
            case ASYNCHRONOUS: return IProtocolAccess.get().getEventManagers().getAsync();
            default: return new ExceptionBuilder().getEventsExceptions().customMessage("Can't get packet event manager").throwException();
        }
    }

    public List<RegisteredProtocollerListener> getListeners() {
        return totalListeners;
    }

    @Override
    public int registeredListeners() {
        return this.totalListeners.size();
    }

    @Override
    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getFullName() {
        return this.name + " " + this.version;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProtocollerClient && ((ProtocollerClient) obj).getName().equalsIgnoreCase(this.name) && ((ApiProvider) obj).getVersion().equalsIgnoreCase(this.version);
    }

    @Override
    public boolean equals(@NotNull ProtocollerClient client) {
        return client.getName().equalsIgnoreCase(this.name) && ((ApiProvider)client).getVersion().equalsIgnoreCase(this.version);
    }

    public void addListener(RegisteredProtocollerListener listener) {
        this.totalListeners.add(listener);
    }

    public void removeListener(RegisteredPacketListener listener) {
        if(this.totalListeners.removeIf((c -> c.equals((RegisteredProtocollerListener) listener)))) {
            PacketHandshakeReceiveEvent.getHandlerList().unregister(listener);
            PacketPlayReceiveEvent.getHandlerList().unregister(listener);
            PacketPlaySendEvent.getHandlerList().unregister(listener);
            PacketLoginReceiveEvent.getHandlerList().unregister(listener);
            PacketLoginSendEvent.getHandlerList().unregister(listener);
            PacketStatusReceiveEvent.getHandlerList().unregister(listener);
            PacketStatusSendEvent.getHandlerList().unregister(listener);
            AsyncPacketHandshakeReceiveEvent.getHandlerList().unregister(listener);
            AsyncPacketPlayReceiveEvent.getHandlerList().unregister(listener);
            AsyncPacketPlaySendEvent.getHandlerList().unregister(listener);
            AsyncPacketLoginReceiveEvent.getHandlerList().unregister(listener);
            AsyncPacketLoginSendEvent.getHandlerList().unregister(listener);
            AsyncPacketStatusReceiveEvent.getHandlerList().unregister(listener);
            AsyncPacketStatusSendEvent.getHandlerList().unregister(listener);
        }
    }

    public void removeAllListeners() {
        for(RegisteredProtocollerListener listener : totalListeners) this.removeListener(listener);
    }
}
