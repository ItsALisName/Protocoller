package net.alis.protocoller.plugin.network;

import io.netty.channel.Channel;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class NettyChannelManager {

    private final Channel channel;

    public NettyChannelManager(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void writePacket(Object rawPacket) {
        channel.write(rawPacket);
    }

    public void writePacket(PlayOutPacket packet) {
        channel.write(packet.getRawPacket());
    }

    public void flushPackets() {
        channel.flush();
    }

    public void sendPacket(Object rawPacket) {
        channel.writeAndFlush(rawPacket);
    }

    public void sendPacket(@NotNull PlayOutPacket packet) {
        channel.writeAndFlush(packet.getData().getRawPacket());
    }

}
