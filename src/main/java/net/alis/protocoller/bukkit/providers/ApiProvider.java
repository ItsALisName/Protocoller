package net.alis.protocoller.bukkit.providers;

import net.alis.protocoller.bukkit.exceptions.NetworkPlayerNotFoundException;
import net.alis.protocoller.entity.ApiUser;
import net.alis.protocoller.ProtocollerApi;
import net.alis.protocoller.bukkit.util.Utils;
import net.alis.protocoller.bukkit.exceptions.BannedApiUserException;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.server.NetworkServer;
import org.bukkit.Bukkit;

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
        if(GlobalProvider.instance().getConfig().isApiUserRegistrationNotify()) {
            Bukkit.getConsoleSender().sendMessage(setColors("&Skip$1[Protocoller] New api user registered:"));
            Bukkit.getConsoleSender().sendMessage(setColors("&Skip$1[Protocoller] Plugin Name: " + user.getName()));
            Bukkit.getConsoleSender().sendMessage(setColors("&Skip$1[Protocoller] Version: " + user.getVersion()));
            Bukkit.getConsoleSender().sendMessage(setColors("&Skip$1[Protocoller] Authors: " + String.join(", ", user.getAuthors())));
        }
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
