package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.server.NetworkServer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ApiProvider implements ApiUser {

    private final String name;
    private final String version;
    private final String[] authors;
    private int registeredPacketListeners;

    public ApiProvider(ApiUser user) {
        if(GlobalProvider.instance().getConfig().getBannedPlugin().contains(user.getName())) {
            this.name = "NONE";
            this.version = "NONE";
            this.authors = new String[]{"NONE"};
            LogsManager.get().getLogger().warn("The banned plugin(Name: \"" + user.getName() + "\") tried to use the API");
            return;
        }
        if(Utils.findSimilar(GlobalProvider.instance().getConfig().getBannedAuthors(), Arrays.asList(user.getAuthors())).size() > 0) {
            this.name = "NONE";
            this.version = "NONE";
            this.authors = new String[]{"NONE"};
            LogsManager.get().getLogger().warn("The plugin(Name: \"" + user.getName() + "\") from banned author tried to use the API");
            return;
        }
        GlobalProvider.instance().getData().getUsersContainer().getRegisteredUsers().add(user);
        LogsManager.get().sendRegisteredUserNotify(user.getName(), user.getVersion(), String.join(", ", user.getAuthors()));
        this.name = user.getName();
        this.version = user.getVersion();
        this.authors = user.getAuthors();
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
    public String[] getAuthors() {
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
