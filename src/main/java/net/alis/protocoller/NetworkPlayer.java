package net.alis.protocoller;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.samples.network.PlayerConnection;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.util.UUID;

public interface NetworkPlayer {

    String getName();

    UUID getUniqueId();

    Version getVersion();

    InetSocketAddress getInetSocketAddress();

    PlayerConnection getConnection();

    int getPacketsSentCount();

    int getPacketsReceivedCount();

    Player getBukkitPlayer();

    int getPing();

}
