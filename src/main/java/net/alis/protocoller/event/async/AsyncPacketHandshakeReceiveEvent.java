package net.alis.protocoller.event.async;

import io.netty.channel.Channel;
import net.alis.protocoller.event.impl.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.HandshakeInPacket;

import java.net.InetSocketAddress;

public class AsyncPacketHandshakeReceiveEvent extends AsyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    public AsyncPacketHandshakeReceiveEvent(PacketDataContainer data, Channel channel, InetSocketAddress address) {
        super(data, channel, address);
    }

    public void trySendPacket(HandshakeInPacket packet) throws Exception {
        super.trySendPacket(packet);
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
