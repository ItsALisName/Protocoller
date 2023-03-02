package net.alis.protocoller.event.asynchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.event.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataSerializer;

import java.net.InetSocketAddress;

public class AsyncPacketStatusSendEvent extends AsyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    public AsyncPacketStatusSendEvent(PacketDataSerializer data, Channel channel, InetSocketAddress address) {
        super(data, channel, address);
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
