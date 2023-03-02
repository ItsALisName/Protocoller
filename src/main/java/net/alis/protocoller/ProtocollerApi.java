package net.alis.protocoller;

import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.server.NetworkServer;

import java.net.InetSocketAddress;
import java.util.UUID;

public interface ProtocollerApi {

    NetworkServer getNetworkServer();

    NetworkPlayer getPlayer(String playerName);

    NetworkPlayer getPlayer(UUID uuid);

    NetworkPlayer getPlayer(InetSocketAddress address);

    PacketEventsManager getEventManager();

}
