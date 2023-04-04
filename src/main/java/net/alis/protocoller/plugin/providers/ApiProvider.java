package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.ProtocollerApi;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.server.NetworkServer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.UUID;

public class ApiProvider implements ProtocollerApi {

    public ApiProvider(ApiUser user) {
        if(GlobalProvider.instance().getConfig().getBannedPlugin().contains(user.getName())) {
            LogsManager.get().getLogger().warn("The banned plugin(Name: \"" + user.getName() + "\") tried to use the API");
            return;
        }
        if(Utils.findSimilar(GlobalProvider.instance().getConfig().getBannedAuthors(), Arrays.asList(user.getAuthors())).size() > 0) {
            LogsManager.get().getLogger().warn("The plugin(Name: \"" + user.getName() + "\") from banned author tried to use the API");
            return;
        }
        GlobalProvider.instance().getData().getUsersContainer().getRegisteredUsers().add(user);
        LogsManager.get().sendRegisteredUserNotify(user.getName(), user.getVersion(), String.join(", ", user.getAuthors()));
    }

    @Override
    public NetworkPlayer getPlayer(@NotNull Player player) {
        return getPlayer(player.getUniqueId());
    }

    @Override
    public NetworkServer getNetworkServer() {
        return GlobalProvider.instance().getServer();
    }

    @Override
    @Nullable
    public NetworkPlayer getPlayer(String playerName) {
        for(NetworkPlayer player : getNetworkServer().getOnlinePlayers()) {
            if(player.getName().equalsIgnoreCase(playerName)) return player;
        }
        return null;
    }

    @Override
    @Nullable
    public NetworkPlayer getPlayer(UUID uuid) {
        for(NetworkPlayer player : getNetworkServer().getOnlinePlayers()) {
            if(player.getUniqueId().equals(uuid)) return player;
        }
        return null;
    }

    @Override
    @Nullable
    public NetworkPlayer getPlayer(InetSocketAddress address) {
        for(NetworkPlayer player : getNetworkServer().getOnlinePlayers()) {
            if(player.getInetSocketAddress().equals(address)) return player;
        }
        return null;
    }

    @Override
    public PacketEventsManager getEventManager() {
        return GlobalProvider.instance().getEventManager();
    }
}
