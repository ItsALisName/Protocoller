package net.alis.protocoller.plugin.server;

import com.google.common.collect.Lists;

import net.alis.protocoller.plugin.network.netty.ChannelInjector;
import net.alis.protocoller.plugin.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.plugin.network.netty.injectors.PlayersInjector;
import net.alis.protocoller.plugin.network.netty.injectors.ServerInjector;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.plugin.util.reflection.ServerReflection;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.server.NetworkServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.bukkit.Server;

public class ProtocollerServer implements NetworkServer {

    private final Version version;
    private final boolean isLegacy; //Lower than 1.17
    private final boolean isVeryLegacy; //Lower than 1.13
    protected final List<Channel> channels;
    protected final List<ChannelFuture> channelFutures;
    private final Server server;
    private final List<NettyPacketInterceptor> packetInterceptors;
    private final ServerInjector serverInjector;
    private final PlayersInjector playersInjector;

    public ProtocollerServer(Server server) {
        this.server = server;
        this.version = Version.fromProtocol(ServerReflection.getProtocolVersion());
        this.isLegacy = this.version.ordinal() < Version.v1_17.ordinal();
        this.isVeryLegacy = isLegacy && this.version.ordinal() < Version.v1_13.ordinal();
        this.channels = new ArrayList<>();
        this.channelFutures = Collections.synchronizedList(Lists.newArrayList());
        List<Object> networkManagers = ServerReflection.getServerNetworkManagers();
        synchronized (networkManagers) {
            for(Object networkManager : networkManagers) {
                this.channels.add(BaseReflection.readField(networkManager, 0, Channel.class));
            }
        }
        for(Object future : ServerReflection.getServerChannelFutures()) {
            this.channelFutures.add((ChannelFuture) future);
        }
        this.packetInterceptors = new ArrayList<>();
        this.playersInjector = new PlayersInjector();
        this.serverInjector = new ServerInjector();
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public boolean isLegacy() {
        return isLegacy;
    }

    @Override
    public boolean isVeryLegacy() {
        return isVeryLegacy;
    }

    @Override
    public Collection<NetworkPlayer> getOnlinePlayers() {
        return GlobalProvider.instance().getData().getPlayersContainer().getNetworkPlayers();
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public List<ChannelFuture> getChannelFutures() {
        return channelFutures;
    }

    public Server getServer() {
        return server;
    }

    public List<NettyPacketInterceptor> getPacketInterceptors() {
        return packetInterceptors;
    }

    public ChannelInjector.ServerInjector getServerInjector() {
        return serverInjector;
    }

    public ChannelInjector.PlayerInjector getPlayersInjector() {
        return playersInjector;
    }

}
