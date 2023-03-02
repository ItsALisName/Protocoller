package net.alis.protocoller.entity;

import io.netty.channel.Channel;
import net.alis.protocoller.packet.Packet;

import java.net.InetSocketAddress;
import java.util.UUID;

public interface NetworkPlayer {

    String getName();

    UUID getUniqueId();

    InetSocketAddress getInetSocketAddress();

    Channel getChannel();

    void writePacket(Packet packet);

    void sendPacket(Packet packet);

}
