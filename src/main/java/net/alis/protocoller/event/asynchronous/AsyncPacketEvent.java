package net.alis.protocoller.event.asynchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;

import java.net.InetSocketAddress;

public class AsyncPacketEvent {

    private final PacketDataContainer data;
    private final Channel channel;
    private final InetSocketAddress address;

    public AsyncPacketEvent(PacketDataContainer data, Channel channel, InetSocketAddress address) {
        this.data = data;
        this.channel = channel;
        this.address = address;
    }

    public PacketDataContainer getData() {
        return data;
    }

    public MinecraftPacketType getPacketType() {
        return this.data.getType();
    }

    public Channel getChannel() {
        return channel;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void trySendPacket(Packet packet) throws Exception {
        this.channel.writeAndFlush(packet.getRawPacket());
    }

}
