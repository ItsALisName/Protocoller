package net.alis.protocoller.entity;

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

    void writePacket(PlayOutPacket packet);

    void sendPacket(PlayOutPacket packet);

}
