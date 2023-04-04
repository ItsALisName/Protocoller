package net.alis.protocoller;

import io.netty.channel.Channel;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.type.PlayOutPacket;

import java.net.InetSocketAddress;
import java.util.UUID;

public interface NetworkPlayer {

    String getName();

    UUID getUniqueId();

    InetSocketAddress getInetSocketAddress();

    Channel getChannel();

    void sendPacket(PlayOutPacket packet);

    int getPacketsSentCount();

    int getPacketsReceivedCount();

}
