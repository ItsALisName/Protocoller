package net.alis.protocoller.event.sync;

import io.netty.channel.Channel;
import net.alis.protocoller.event.impl.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.StatusOutPacket;

import java.net.InetSocketAddress;

public class PacketStatusSendEvent extends SyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    public PacketStatusSendEvent(PacketDataContainer data, Channel channel, InetSocketAddress address) {
        super(data, channel, address);
    }

    public void setPacket(StatusOutPacket packet) {
        super.setPacket(packet);
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
