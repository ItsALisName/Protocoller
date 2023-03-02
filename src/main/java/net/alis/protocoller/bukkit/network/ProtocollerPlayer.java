package net.alis.protocoller.bukkit.network;

import io.netty.channel.Channel;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.packet.Packet;

import java.net.InetSocketAddress;
import java.util.UUID;

public class ProtocollerPlayer implements NetworkPlayer {

    private final NettyChannelManager channelManager;
    private final String name;
    private final UUID uuid;
    private final InetSocketAddress address;

    public ProtocollerPlayer(NettyChannelManager channelManager, String name, UUID uuid, InetSocketAddress address) {
        this.channelManager = channelManager;
        this.name = name;
        this.uuid = uuid;
        this.address = address;
        GlobalProvider.instance().getData().getPlayersContainer().addPlayer(this);
    }

    @Override
    public String getName() {
        return name;
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

    @Override
    public void writePacket(Packet packet) {
        this.channelManager.writePacket(packet);
    }

    @Override
    public void sendPacket(Packet packet) {
        this.channelManager.sendPacket(packet);
    }
}
