package net.alis.protocoller.plugin.v0_0_5.server;

import net.alis.protocoller.plugin.memory.ApproximateData;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.command.ProtocolBukkitCommandMap;
import net.alis.protocoller.plugin.v0_0_5.command.ProtocolCommandListenerWrapper;
import net.alis.protocoller.plugin.v0_0_5.netty.ChannelInjector;
import net.alis.protocoller.plugin.v0_0_5.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.plugin.v0_0_5.netty.injectors.PlayersInjector;
import net.alis.protocoller.plugin.v0_0_5.netty.injectors.ServerInjector;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolWorldServer;
import net.alis.protocoller.plugin.v0_0_5.server.rcon.ProtocolQueryThreadGS4;
import net.alis.protocoller.plugin.v0_0_5.server.rcon.ProtocolRconThread;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.NetworkServer;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

import net.alis.protocoller.samples.command.CommandListenerWrapper;
import net.alis.protocoller.samples.server.rcon.QueryThreadGC4;
import net.alis.protocoller.samples.server.ServerConnection;
import net.alis.protocoller.samples.network.status.ServerPing;
import net.alis.protocoller.samples.server.rcon.RconThread;
import net.alis.protocoller.samples.server.world.level.WorldServer;
import net.alis.protocoller.util.AccessedObject;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProtocolServer implements NetworkServer {

    private final AccessedObject handle;
    private final AccessedObject craftServerHandle;
    private @Nullable AccessedObject simpleHelpMapHandle;
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
    public final List<WorldServer> worlds = new ArrayList<>();
    private final ProtocolBukkitCommandMap commandMap;

    public ProtocolServer(Server server, Object minecraft$dedicatedServer) {
        this.bukkitServer = server;
        this.handle = new AccessedObject(minecraft$dedicatedServer, true);
        this.craftServerHandle = new AccessedObject(handle.readSuperclassField(0, ClassAccessor.get().getCraftServerClass()));
        Object cmdMap = craftServerHandle.readField(0, SimpleCommandMap.class);
        if(cmdMap == null) cmdMap = craftServerHandle.readField(0, ClassAccessor.get().getCraftCommandMapClass());
        this.commandMap = new ProtocolBukkitCommandMap((SimpleCommandMap) cmdMap, this);
        Object simpleHelpMap = craftServerHandle.readField(0, ClassAccessor.get().getSimpleHelpMapClass());
        if(simpleHelpMap != null) this.simpleHelpMapHandle = new AccessedObject(simpleHelpMap);
        this.connection = new ProtocolServerConnection(this.handle.readSuperclassField(0, ClassAccessor.get().getServerConnectionClass()));
        this.packetsReceived = 0;
        this.playersList = new NetworkPlayersList();
        this.entityList = new EntityList();
        Object remStatusListener = this.handle.readField(0, ClassAccessor.get().getRemoteStatusListenerClass());
        Object remoteControlListener = this.handle.readField(0, ClassAccessor.get().getRemoteControlListenerClass());
        if(remStatusListener != null) this.queryThreadGS4 = new ProtocolQueryThreadGS4(remStatusListener);
        if(remoteControlListener != null) this.rconThread = new ProtocolRconThread(remoteControlListener, this);
        this.packetsSent = 0;
        this.coreName = Bukkit.getVersion().split("-")[1];
        this.version = Version.fromProtocol(MinecraftReflection.getServerProtocolVersion());
        this.packetInterceptors = new ArrayList<>();
        this.playersInjector = new PlayersInjector();
        this.serverInjector = new ServerInjector();
        this.serverInjector.inject(this);
        if(this.getVersion().greaterThanOrEqualTo(Version.v1_14)) {
            for(Map.Entry<?,?> mWorld : ((Map<?, ?>)this.handle.readSuperclassField(0, Map.class)).entrySet()) {
                this.worlds.add(new ProtocolWorldServer(mWorld.getValue(), this));
            }
        } else {
            for(Object mWorld :(List<?>)this.handle.readSuperclassField(1, List.class)) {
                this.worlds.add(new ProtocolWorldServer(mWorld, this));
            }
        }
    }

    @Override
    public <P> P getParameter(int index, Class<?> type) throws Exception {
        return this.handle.readField(index, type);
    }

    @Override
    public void setParameter(int index, Class<?> type, Object parameter) throws Exception {
        this.handle.writeSpecify(index, type, parameter);
    }

    @Override
    public <P> P getParameter(Predicate<Version> versionPredicate, int index1, int index2, Class<?> type) throws Exception {
        return this.handle.readField(versionPredicate, index1, index2, type);
    }

    @Override
    public void setParameter(Predicate<Version> versionPredicate, int index1, int index2, Object parameter) throws Exception {
        this.handle.write(versionPredicate, index1, index2, parameter);
    }

    @Override
    public ProtocolBukkitCommandMap getCommandMap() {
        return commandMap;
    }

    @Override
    public @Nullable CommandListenerWrapper getCommandListenerWrapper() {
        if(getCommandListenerWrapperMethod != null) {
            return new ProtocolCommandListenerWrapper(this.handle.invoke(getCommandListenerWrapperMethod), (ProtocolWorldServer) worlds.get(0));
        }
        return null;
    }

    public List<WorldServer> worlds() {
        return worlds;
    }

    @Nullable
    public WorldServer getWorldServer(String name) {
        for(WorldServer world : this.worlds) if(world.getWorldData().getWorldName().equalsIgnoreCase(name)) return world;
        return null;
    }

    public ServerPing getServerStatus() {
        return new ServerPing(this.handle.readField(0, ClassAccessor.get().getServerPingClass()));
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
    public Collection<NetworkPlayer> onlinePlayers() {
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

    public void setQueryThreadGS4(ProtocolQueryThreadGS4 queryThreadGS4) {
        this.queryThreadGS4 = queryThreadGS4;
    }

    public void setRconThread(ProtocolRconThread rconThread) {
        this.rconThread = rconThread;
    }

    @Nullable
    public static WorldServer getWorld(Object world) {
        for(WorldServer worldServer : IProtocolAccess.get().getServer().worlds()) {
            if(((ProtocolWorldServer)worldServer).getHandle().getOriginal().equals(world)) {
                return worldServer;
            }
        }
        return null;
    }

    public void syncCommands() {
        if(syncCommandsMethod != null) {
            this.craftServerHandle.invoke(syncCommandsMethod);
        } else {
            this.simpleHelpMapHandle.invoke(initializeCommandsMethod);
        }
    }

    public AccessedObject getCraftServerHandle() {
        return craftServerHandle;
    }

    public AccessedObject getSimpleHelpMapHandle() {
        return simpleHelpMapHandle;
    }

    private static Method getCommandListenerWrapperMethod, syncCommandsMethod, initializeCommandsMethod;

    public static void init() {
        if(ProtocolCommandListenerWrapper.clazz() != null) {
            getCommandListenerWrapperMethod = Reflect.getMethod(ClassAccessor.get().getMinecraftServerClass(), ProtocolCommandListenerWrapper.clazz(), true);
        }
        if(ApproximateData.get().getPreVersion().greaterThanOrEqualTo(Version.v1_13)) {
            syncCommandsMethod = Reflect.getMethod(ClassAccessor.get().getCraftServerClass(), "syncCommands", Void.TYPE, false);
        } else {
            initializeCommandsMethod = Reflect.getMethod(ClassAccessor.get().getSimpleHelpMapClass(), "initializeCommands", Void.TYPE, false);
        }
    }
}
