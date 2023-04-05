package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.plugin.config.ProtocollerConfig;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.server.NetworkServer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiProvider implements ApiUser {

    private final String name;
    private final String version;
    private final List<String> authors;
    private int registeredPacketListeners;

    public ApiProvider(Plugin user) {
        if(ProtocollerConfig.getBannedPlugin().contains(user.getName())) {
            this.name = "NONE";
            this.version = "NONE";
            this.authors = new ArrayList<>();
            LogsManager.get().getLogger().warn("The banned plugin(Name: \"" + user.getName() + "\") tried to use the API");
            return;
        }
        if(Utils.findSimilar(ProtocollerConfig.getBannedAuthors(), user.getDescription().getAuthors()).size() > 0) {
            this.name = "NONE";
            this.version = "NONE";
            this.authors = new ArrayList<>();
            LogsManager.get().getLogger().warn("The plugin(Name: \"" + user.getName() + "\") from banned author tried to use the API");
            return;
        }
        GlobalProvider.instance().getData().getUsersContainer().getRegisteredUsers().add(this);
        LogsManager.get().sendRegisteredUserNotify(user.getName(), user.getDescription().getVersion(), String.join(", ", user.getDescription().getAuthors()));
        this.name = user.getName();
        this.version = user.getDescription().getVersion();
        this.authors = user.getDescription().getAuthors();
        this.registeredPacketListeners = 0;
    }

    @Override
    public NetworkServer getServer() {
        return GlobalProvider.instance().getServer();
    }

    @Override
    public PacketEventsManager getEventManager() {
        return GlobalProvider.instance().getEventManager();
    }

    @Override
    public int registeredListeners() {
        return this.registeredPacketListeners;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public List<String> getAuthors() {
        return authors;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ApiUser && ((ApiUser) obj).getName().equalsIgnoreCase(this.name) && ((ApiUser) obj).getVersion().equalsIgnoreCase(this.version);
    }

    @Override
    public boolean equals(@NotNull ApiUser user) {
        return user.getName().equalsIgnoreCase(this.name) && user.getVersion().equalsIgnoreCase(this.version);
    }

    public void addListenerCount() {
        this.registeredPacketListeners = this.registeredPacketListeners + 1;
    }
}
