package net.alis.protocoller.plugin.v0_0_3.server;

import net.alis.protocoller.NetworkPlayer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class NetworkPlayersList {

    private final Collection<NetworkPlayer> networkPlayers;

    public NetworkPlayersList() {
        this.networkPlayers = new ArrayList<>();
    }

    public Collection<NetworkPlayer> getNetworkPlayers() {
        return networkPlayers;
    }

    public boolean removePlayer(String playerName) {
        return this.networkPlayers.removeIf(networkPlayer -> networkPlayer.getName().equalsIgnoreCase(playerName));
    }

    public boolean removePlayer(UUID uuid) {
        return this.networkPlayers.removeIf(networkPlayer -> networkPlayer.getUniqueId().equals(uuid));
    }

    public boolean removePlayer(InetSocketAddress address) {
        return this.networkPlayers.removeIf(networkPlayer -> networkPlayer.getInetSocketAddress().equals(address));
    }

    public void addPlayer(NetworkPlayer player) {
        this.networkPlayers.add(player);
    }

    public NetworkPlayer get(UUID uuid) {
        for(NetworkPlayer player : this.networkPlayers) {
            if(player.getUniqueId().equals(uuid)) return player;
        }
        return null;
    }

    public NetworkPlayer get(String nickname) {
        for(NetworkPlayer player : this.networkPlayers) {
            if(player.getName().equalsIgnoreCase(nickname)) return player;
        }
        return null;
    }
}
