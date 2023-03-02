package net.alis.protocoller.event.synchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.bukkit.exceptions.PacketDeserializeException;
import net.alis.protocoller.event.impl.CancellablePacketEvent;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;

import java.net.InetSocketAddress;

public class SyncPacketEvent implements CancellablePacketEvent {
    private PacketDataSerializer data;
    private final Channel channel;
    private final InetSocketAddress address;
    private boolean cancelled;

    public SyncPacketEvent(PacketDataSerializer data, Channel channel, InetSocketAddress address) {
        this.data = data;
        this.channel = channel;
        this.address = address;
    }

    public PacketDataSerializer getData() {
        return this.data;
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

    public void setPacket(Packet packet) {
        if(packet.getPacketData() != null) {
            this.data = packet.getPacketData();
            return;
        }
        throw new PacketDeserializeException("Failed to get new packet data. Have you done everything right?");
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
