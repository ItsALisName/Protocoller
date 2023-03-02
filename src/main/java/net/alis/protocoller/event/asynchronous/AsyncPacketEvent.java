package net.alis.protocoller.event.asynchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataSerializer;

import java.net.InetSocketAddress;

public class AsyncPacketEvent {

    private final PacketDataSerializer data;
    private final Channel channel;
    private final InetSocketAddress address;

    public AsyncPacketEvent(PacketDataSerializer data, Channel channel, InetSocketAddress address) {
        this.data = data;
        this.channel = channel;
        this.address = address;
    }

    public PacketDataSerializer getData() {
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

}
