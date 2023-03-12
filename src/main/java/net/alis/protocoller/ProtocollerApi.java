package net.alis.protocoller;

import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.server.NetworkServer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.UUID;

public interface ProtocollerApi {

    NetworkServer getNetworkServer();

    @Nullable NetworkPlayer getPlayer(String playerName);

    @Nullable NetworkPlayer getPlayer(UUID uuid);

    @Nullable NetworkPlayer getPlayer(InetSocketAddress address);

    @Nullable NetworkPlayer getPlayer(Player player);

    PacketEventsManager getEventManager();

}
