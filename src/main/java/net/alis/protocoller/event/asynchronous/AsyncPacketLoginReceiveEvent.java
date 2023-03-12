package net.alis.protocoller.event.asynchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.event.PacketHandlerList;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.LoginInPacket;

import java.net.InetSocketAddress;

public class AsyncPacketLoginReceiveEvent extends AsyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    public AsyncPacketLoginReceiveEvent(PacketDataContainer data, Channel channel, InetSocketAddress address) {
        super(data, channel, address);
    }

    public void trySendPacket(LoginInPacket packet) throws Exception {
        super.trySendPacket(packet);
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
