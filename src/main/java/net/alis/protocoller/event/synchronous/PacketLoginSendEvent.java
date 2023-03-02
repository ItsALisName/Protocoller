package net.alis.protocoller.event.synchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.event.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataSerializer;

import java.net.InetSocketAddress;

public class PacketLoginSendEvent extends SyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    public PacketLoginSendEvent(PacketDataSerializer data, Channel channel, InetSocketAddress address) {
        super(data, channel, address);
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
