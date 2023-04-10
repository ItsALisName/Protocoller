package net.alis.protocoller.plugin.v0_0_4.server;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.v0_0_4.netty.ChannelInjector;
import net.alis.protocoller.plugin.v0_0_4.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.plugin.v0_0_4.netty.injectors.PlayersInjector;
import net.alis.protocoller.plugin.v0_0_4.netty.injectors.ServerInjector;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.v0_0_4.server.rcon.ProtocolQueryThreadGS4;
import net.alis.protocoller.plugin.v0_0_4.server.rcon.ProtocolRconThread;
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

public class ProtocolServer implements NetworkServer {

    private final AccessedObject handle;
    private final Version version;
    private final NetworkPlayersList playersList;
    private final ProtocolServerConnection connection;
    private final List<NettyPacketInterceptor> packetInterceptors;
    private final ServerInjector serverInjector;
    private final PlayersInjector playersInjector;
    private final String coreName;
    private int packetsReceived;
    private int packetsSent;
    private final Server bukkitServer;
    private ProtocolQueryThreadGS4 queryThreadGS4;
    private ProtocolRconThread rconThread;
    private final EntityList entityList;

    public ProtocolServer(Server server, Object minecraft$dedicatedServer) {
        this.bukkitServer = server;
        this.handle = new AccessedObject(minecraft$dedicatedServer, true);
        this.connection = new ProtocolServerConnection(this.handle.readSuperclass(0, ClassAccessor.get().getServerConnectionClass()));
        this.packetsReceived = 0;
        this.playersList = new NetworkPlayersList();
        this.entityList = new EntityList();
        Object remStatusListener = this.handle.read(0, ClassAccessor.get().getRemoteStatusListenerClass());
        Object remoteControlListener = this.handle.read(0, ClassAccessor.get().getRemoteControlListenerClass());
        if(remStatusListener != null) this.queryThreadGS4 = new ProtocolQueryThreadGS4(remStatusListener);
        if(remoteControlListener != null) this.rconThread = new ProtocolRconThread(remoteControlListener, this);
        this.packetsSent = 0;
        this.coreName = Bukkit.getVersion().split("-")[1];
        this.version = Version.fromProtocol(MinecraftReflection.getServerProtocolVersion());
        this.packetInterceptors = new ArrayList<>();
        this.playersInjector = new PlayersInjector();
        this.serverInjector = new ServerInjector();
        this.serverInjector.inject(this);
    }

    public ServerPing getServerStatus() {
        return new ServerPing(this.handle.read(0, ClassAccessor.get().getServerPingClass()));
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

    public EntityList getEntityList() {
        return entityList;
    }

    public NetworkPlayersList getPlayersList() {
        return playersList;
    }

    @Override
    public Collection<NetworkPlayer> getOnlinePlayers() {
        return this.playersList.getNetworkPlayers();
    }

    @Override
    public @Nullable NetworkPlayer getPlayer(@NotNull Player player) {
        return this.playersList.get(player.getUniqueId());
    }

    @Override
    public @Nullable NetworkPlayer getPlayer(UUID uuid) {
        return this.playersList.get(uuid);
    }

    @Override
    public @Nullable NetworkPlayer getPlayer(String nickname) {
        return this.playersList.get(nickname);
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

    protected void setQueryThreadGS4(ProtocolQueryThreadGS4 queryThreadGS4) {
        this.queryThreadGS4 = queryThreadGS4;
    }

    protected void setRconThread(ProtocolRconThread rconThread) {
        this.rconThread = rconThread;
    }
}
