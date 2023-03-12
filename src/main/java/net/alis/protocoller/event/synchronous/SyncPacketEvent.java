package net.alis.protocoller.event.synchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.event.impl.CancellablePacketEvent;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import org.jetbrains.annotations.NotNull;

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

    protected void setPacket(@NotNull Packet packet) {
        if(packet.getData() != null) {
            this.data = packet.getData();
            return;
        }
        throw new RuntimeException("Failed to replace packet. Is the new packet configured correctly?");
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
