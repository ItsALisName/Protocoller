package net.alis.protocoller.event.sync;

import io.netty.channel.Channel;
import net.alis.protocoller.event.impl.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.StatusInPacket;

import java.net.InetSocketAddress;

public class PacketStatusReceiveEvent extends SyncPacketEvent{

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    public PacketStatusReceiveEvent(PacketDataContainer data, Channel channel, InetSocketAddress address) {
        super(data, channel, address);
    }

    public void setPacket(StatusInPacket packet) {
        super.setPacket(packet);
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
