package net.alis.protocoller.plugin.server;

import com.google.common.collect.Lists;

import net.alis.protocoller.plugin.network.netty.ChannelInjector;
import net.alis.protocoller.plugin.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.plugin.network.netty.injectors.PlayersInjector;
import net.alis.protocoller.plugin.network.netty.injectors.ServerInjector;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.server.NetworkServer;

import java.util.*;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private final String coreName;

    public ProtocollerServer(Server server) {
        this.server = server;
        this.coreName = Bukkit.getVersion().split("-")[1];
        this.version = Version.fromProtocol(MinecraftReflection.getServerProtocolVersion());
        this.isLegacy = this.version.ordinal() < Version.v1_17.ordinal();
        this.isVeryLegacy = isLegacy && this.version.ordinal() < Version.v1_13.ordinal();
        this.channels = new ArrayList<>();
        this.channelFutures = Collections.synchronizedList(Lists.newArrayList());
        List<Object> networkManagers = MinecraftReflection.getServerNetworkManagers();
        synchronized (networkManagers) {
            for(Object networkManager : networkManagers) {
                this.channels.add(BaseReflection.readField(networkManager, 0, Channel.class, false));
            }
        }
        for(Object future : MinecraftReflection.getServerChannelFutures()) {
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

    @Override
    public @Nullable NetworkPlayer getPlayer(@NotNull Player player) {
        return GlobalProvider.instance().getServer().getPlayer(player.getUniqueId());
    }

    @Override
    public @Nullable NetworkPlayer getPlayer(UUID uuid) {
        return GlobalProvider.instance().getServer().getPlayer(uuid);
    }

    @Override
    public @Nullable NetworkPlayer getPlayer(String nickname) {
        return GlobalProvider.instance().getServer().getPlayer(nickname);
    }

    @Override
    public String getCoreName() {
        return coreName;
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
