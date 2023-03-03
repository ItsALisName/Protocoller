package net.alis.protocoller.bukkit.providers;

import net.alis.protocoller.bukkit.exceptions.NetworkPlayerNotFoundException;
import net.alis.protocoller.ApiUser;
import net.alis.protocoller.ProtocollerApi;
import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.util.TaskSimplifier;
import net.alis.protocoller.bukkit.util.Utils;
import net.alis.protocoller.bukkit.exceptions.BannedApiUserException;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.server.NetworkServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.UUID;

import static net.alis.protocoller.bukkit.util.Utils.setColors;

public class ApiProvider implements ProtocollerApi {

    public ApiProvider(ApiUser user) {
        if(GlobalProvider.instance().getConfig().getBannedPlugin().contains(user.getName())) {
            throw new BannedApiUserException(new String[]{user.getName(), user.getVersion()}, user.getAuthors());
        }
        if(Utils.findSimilar(GlobalProvider.instance().getConfig().getBannedAuthors(), Arrays.asList(user.getAuthors())).size() > 0) {
            throw new BannedApiUserException(new String[]{user.getName(), user.getVersion()}, user.getAuthors());
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
    public NetworkPlayer getPlayer(String playerName) {
        for(NetworkPlayer player : getNetworkServer().getOnlinePlayers()) {
            if(player.getName().equalsIgnoreCase(playerName)) return player;
        }
        throw new NetworkPlayerNotFoundException(playerName);
    }

    @Override
    public NetworkPlayer getPlayer(UUID uuid) {
        for(NetworkPlayer player : getNetworkServer().getOnlinePlayers()) {
            if(player.getUniqueId().equals(uuid)) return player;
        }
        throw new NetworkPlayerNotFoundException(uuid);
    }

    @Override
    public NetworkPlayer getPlayer(InetSocketAddress address) {
        for(NetworkPlayer player : getNetworkServer().getOnlinePlayers()) {
            if(player.getInetSocketAddress().equals(address)) return player;
        }
        throw new NetworkPlayerNotFoundException(address);
    }

    @Override
    public PacketEventsManager getEventManager() {
        return GlobalProvider.instance().getEventManager();
    }
}
