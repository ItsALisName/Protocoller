package net.alis.protocoller.event.synchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.bukkit.exceptions.PacketDeserializeException;
import net.alis.protocoller.event.impl.CancellablePacketEvent;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;

import java.net.InetSocketAddress;

public class SyncPacketEvent implements CancellablePacketEvent {
    private PacketDataContainer data;
    private final Channel channel;
    private final InetSocketAddress address;
    private boolean cancelled;

    public SyncPacketEvent(PacketDataContainer data, Channel channel, InetSocketAddress address) {
        this.data = data;
        this.channel = channel;
        this.address = address;
    }

    public PacketDataContainer getData() {
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
        throw new PacketDeserializeException("Failed to get new packet data. Have you configure everything right?");
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
