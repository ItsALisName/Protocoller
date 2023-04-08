package net.alis.protocoller.event.async;

import io.netty.channel.Channel;
import net.alis.protocoller.event.impl.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.LoginOutPacket;

import java.net.InetSocketAddress;

public class AsyncPacketLoginSendEvent extends AsyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    public AsyncPacketLoginSendEvent(PacketDataContainer data, Channel channel, InetSocketAddress address) {
        super(data, channel, address);
    }

    public void trySendPacket(LoginOutPacket packet) throws Exception {
        super.trySendPacket(packet);
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
