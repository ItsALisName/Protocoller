package net.alis.protocoller.plugin.network;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.packet.type.PlayOutPacket;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProtocollerPlayer implements NetworkPlayer {

    private final AttributeKey<Integer> PROTOCOL_KEY = AttributeKey.valueOf("PROTOCOL-" + new AtomicInteger().getAndIncrement());

    private final NettyChannelManager channelManager;
    private final String name;
    private final UUID uuid;
    private final InetSocketAddress address;
    private int packetsSent;
    private int packetsReceived;

    public ProtocollerPlayer(NettyChannelManager channelManager, String name, UUID uuid, InetSocketAddress address) {
        this.channelManager = channelManager;
        this.name = name;
        this.uuid = uuid;
        this.address = address;
        this.packetsSent = 0;
        this.packetsReceived = 0;
        GlobalProvider.instance().getData().getPlayersContainer().addPlayer(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getProtocolVersion() {
        return this.channelManager.getChannel().attr(PROTOCOL_KEY).get();
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public Channel getChannel() {
        return this.channelManager.getChannel();
    }

    @Override
    public InetSocketAddress getInetSocketAddress() {
        return address;
    }

    public void writePacket(PlayOutPacket packet) {
        this.channelManager.writePacket(packet);
    }

    @Override
    public void sendPacket(PlayOutPacket packet) {
        this.channelManager.sendPacket(packet);
    }

    @Override
    public int getPacketsReceivedCount() {
        return packetsReceived;
    }

    @Override
    public int getPacketsSentCount() {
        return packetsSent;
    }

    public void addSentPacket() {
        this.packetsSent = packetsSent + 1;
    }

    public void addReceivedPacket() {
        this.packetsReceived = this.packetsReceived + 1;
    }

}
