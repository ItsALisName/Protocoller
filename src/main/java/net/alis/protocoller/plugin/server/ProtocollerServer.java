package net.alis.protocoller.plugin.server;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.network.netty.ChannelInjector;
import net.alis.protocoller.plugin.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.plugin.network.netty.injectors.PlayersInjector;
import net.alis.protocoller.plugin.network.netty.injectors.ServerInjector;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.server.rcon.ProtocollerQueryThreadGS4;
import net.alis.protocoller.plugin.server.rcon.ProtocollerRconThread;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.NetworkServer;

import java.util.*;

import net.alis.protocoller.samples.server.rcon.QueryThreadGC4;
import net.alis.protocoller.samples.server.ServerConnection;
import net.alis.protocoller.samples.network.status.ServerPing;
import net.alis.protocoller.samples.server.rcon.RconThread;
import net.alis.protocoller.util.AccessedObject;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProtocollerServer implements NetworkServer {

    private final AccessedObject handle;
    private final Version version;
    private final ProtocollerServerConnection connection;
    private final List<NettyPacketInterceptor> packetInterceptors;
    private final ServerInjector serverInjector;
    private final PlayersInjector playersInjector;
    private final String coreName;
    private int packetsReceived;
    private int packetsSent;
    private final Server bukkitServer;
    private ProtocollerQueryThreadGS4 queryThreadGS4;
    private ProtocollerRconThread rconThread;

    public ProtocollerServer(Server server, Object minecraft$dedicatedServer) {
        this.bukkitServer = server;
        this.handle = new AccessedObject(minecraft$dedicatedServer, true);
        this.connection = new ProtocollerServerConnection(this.handle.readSuperclass(0, ClassesContainer.get().getServerConnectionClass()));
        this.packetsReceived = 0;
        Object remStatusListener = this.handle.read(0, ClassesContainer.get().getRemoteStatusListenerClass());
        Object remoteControlListener = this.handle.read(0, ClassesContainer.get().getRemoteControlListenerClass());
        if(remStatusListener != null) this.queryThreadGS4 = new ProtocollerQueryThreadGS4(remStatusListener);
        if(remoteControlListener != null) this.rconThread = new ProtocollerRconThread(remoteControlListener, this);
        this.packetsSent = 0;
        this.coreName = Bukkit.getVersion().split("-")[1];
        this.version = Version.fromProtocol(MinecraftReflection.getServerProtocolVersion());
        this.packetInterceptors = new ArrayList<>();
        this.playersInjector = new PlayersInjector();
        this.serverInjector = new ServerInjector();
    }

    public ServerPing getServerStatus() {
        return new ServerPing(this.handle.read(0, ClassesContainer.get().getServerPingClass()));
    }

    @Override
    public QueryThreadGC4 getQueryThreadGC4() {
        return this.queryThreadGS4;
    }

    @Override
    public RconThread getRconThread() {
        return rconThread;
    }

    public void setServerStatus(@NotNull ServerPing serverPing) {
        this.handle.write(0, serverPing.createOriginal());
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public Collection<NetworkPlayer> getOnlinePlayers() {
        return GlobalProvider.instance().getData().getPlayersContainer().getNetworkPlayers();
    }

    @Override
    public @Nullable NetworkPlayer getPlayer(@NotNull Player player) {
        return GlobalProvider.instance().getData().getPlayersContainer().get(player.getUniqueId());
    }

    @Override
    public @Nullable NetworkPlayer getPlayer(UUID uuid) {
        return GlobalProvider.instance().getData().getPlayersContainer().get(uuid);
    }

    @Override
    public @Nullable NetworkPlayer getPlayer(String nickname) {
        return GlobalProvider.instance().getData().getPlayersContainer().get(nickname);
    }

    @Override
    public ServerConnection getConnection() {
        return connection;
    }

    @Override
    public int getReceivedPacketsCount() {
        return this.packetsReceived;
    }

    @Override
    public int getSentPacketsCount() {
        return this.packetsSent;
    }

    @Override
    public String getCoreName() {
        return coreName;
    }

    public Server getBukkitServer() {
        return bukkitServer;
    }

    public boolean isLegacy() {
        return this.version.lessThan(Version.v1_17);
    }

    public boolean isVeryLegacy() {
        return this.version.lessThan(Version.v1_13);
    }

    public AccessedObject getHandle() {
        return handle;
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

    public void addReceivedPacket() {
        this.packetsReceived += 1;
    }

    public void addSentPacket() {
        this.packetsSent += 1;
    }

    protected void setQueryThreadGS4(ProtocollerQueryThreadGS4 queryThreadGS4) {
        this.queryThreadGS4 = queryThreadGS4;
    }

    protected void setRconThread(ProtocollerRconThread rconThread) {
        this.rconThread = rconThread;
    }
}
