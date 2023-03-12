package net.alis.protocoller.event.synchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.event.PacketHandlerList;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.LoginOutPacket;

import java.net.InetSocketAddress;

public class PacketLoginSendEvent extends SyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    public PacketLoginSendEvent(PacketDataContainer data, Channel channel, InetSocketAddress address) {
        super(data, channel, address);
    }

    public void setPacket(LoginOutPacket packet) {
        super.setPacket(packet);
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
