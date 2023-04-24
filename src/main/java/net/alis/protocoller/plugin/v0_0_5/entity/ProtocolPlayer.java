package net.alis.protocoller.plugin.v0_0_5.entity;

import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.packets.game.*;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.ProtocolUtil;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.command.ProtocolCommandListenerWrapper;
import net.alis.protocoller.plugin.v0_0_5.network.ProtocolPlayerConnection;
import net.alis.protocoller.plugin.v0_0_5.server.ProtocolServer;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolWorldServer;
import net.alis.protocoller.samples.TitleData;
import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import net.alis.protocoller.samples.entity.player.PlayerAbilities;
import net.alis.protocoller.samples.network.PlayerConnection;
import net.alis.protocoller.samples.network.chat.*;
import net.alis.protocoller.util.AccessedObject;
import libraries.net.md_5.bungee.api.MessageType;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.UUID;

public class ProtocolPlayer implements NetworkPlayer {

    private final AccessedObject handle;
    private final Player player;
    private final ProtocolServer server;
    private final ProtocolPlayerConnection connection;
    private final Version version;
    private int packetsSent;
    private int packetsReceived;

    public ProtocolPlayer(Player player, ProtocolServer server) {
        this.server = server;
        this.player = player;
        this.handle = new AccessedObject(MinecraftReflection.getEntityPlayer(player));
        this.connection = new ProtocolPlayerConnection(this.player);
        this.packetsSent = 0;
        this.packetsReceived = 0;
        this.version = Version.fromProtocol(ProtocolUtil.getPlayerProtocol(player));
        IProtocolAccess.get().getServer().getPlayersList().addPlayer(this);
    }

    @Override
    public void sendMessage(@NotNull String s) {
        this.player.sendMessage(s);
    }

    @Override
    public void sendMessage(@NotNull String... strings) {
        this.player.sendMessage(strings);
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String s) {
        this.player.sendMessage(uuid, s);
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String... strings) {
        this.player.sendMessage(uuid, strings);
    }

    @NotNull
    @Override
    public Server getServer() {
        return this.server.getBukkitServer();
    }

    @Override
    public String getName() {
        return this.player.getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    @Override
    public InetSocketAddress getInetSocketAddress() {
        return this.player.getAddress();
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public ProtocolWorldServer getWorld() {
        return (ProtocolWorldServer) IProtocolAccess.get().getServer().getWorldServer(player.getWorld().getName());
    }

    @Override
    public @Nullable ProtocolCommandListenerWrapper getCommandListenerWrapper() {
        if(getCommandListenerWrapperMethod != null) {
            return new ProtocolCommandListenerWrapper(this.handle.invoke(getCommandListenerWrapperMethod), this.getWorld());
        }
        return null;
    }

    @Override
    public int getPacketsReceivedCount() {
        return packetsReceived;
    }

    @Override
    public int getPacketsSentCount() {
        return packetsSent;
    }

    public GameProfile getGameProfile() {
        return new GameProfile(this.handle.readSuperclassField(0, ClassAccessor.get().getGameProfileClass()));
    }

    public void setGameProfile(GameProfile profile) {
        this.handle.writeSuperclass(0, profile.createOriginal());
    }

    public PlayerAbilities getPlayerAbilities() {
        return new PlayerAbilities(this.handle.readSuperclassField(0, ClassAccessor.get().getPlayerAbilitiesClass()));
    }

    public void setPlayerAbilities(@NotNull PlayerAbilities abilities) {
        this.handle.writeSuperclass(0, abilities.createOriginal());
    }

    public int getPing() {
        if(pingField != null) {
            return this.handle.readField(pingField);
        } else {
            return this.player.getPing();
        }
    }

    @Override
    public PlayerConnection getConnection() {
        return connection;
    }

    @Override
    public Player getBukkitPlayer() {
        return this.player;
    }

    public AccessedObject getHandle() {
        return handle;
    }

    public void addSentPacket() {
        this.packetsSent = packetsSent + 1;
    }

    public void addReceivedPacket() {
        this.packetsReceived = this.packetsReceived + 1;
    }

    public void setPlayerInput(float sidewaysSpeed, float forwardSpeed, boolean jumping, boolean sneaking) {
        this.handle.invoke(setPlayerInputMethod, sidewaysSpeed, forwardSpeed, jumping, sneaking);
    }

    public void restoreFrom(NetworkPlayer player, boolean alive) throws NoSuchMethodException {
        if(restoreFromMethod == null)
            throw new NoSuchMethodException("Method NetworkPlayer#restoreFrom(NetworkPlayer, boolean) not available on this server version!");
        this.handle.invoke(restoreFromMethod, ((ProtocolPlayer)player).handle.getOriginal(), alive);
    }

    public void sendChatMessage(ChatComponent message) {
        if(IProtocolAccess.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_18_2)) {
            this.connection.sendPacket(new PacketPlayOutChat(message, MessageType.CHAT, Utils.NIL_UUID));
        } else {
            this.handle.invoke(sendChatMessage_v_1_19_1_2, message.asIChatBaseComponent());
        }
    }

    public void sendChatMessageAsync(ChatComponent message) {
        ITaskAccess.get().doAsync(() -> sendChatMessage(message));
    }

    public void sendActionbarText(ChatComponent message) {
        if(IProtocolAccess.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_17_1)) {
            this.connection.sendPacket(new PacketPlayOutChat(message, MessageType.ACTION_BAR, Utils.NIL_UUID));
        } else {
            this.connection.sendPacket(new ClientboundSetActionBarTextPacket(message));
        }
    }

    public void sendActionbarTextAsync(ChatComponent message) {
        ITaskAccess.get().doAsync(() -> sendActionbarText(message));
    }

    public void sendTitle(TitleData title) {
        if(IProtocolAccess.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_16_4n5)) {
            PacketPlayOutTitle times = new PacketPlayOutTitle(title, PacketPlayOutTitle.Action.TIMES);
            PacketPlayOutTitle hTitle = new PacketPlayOutTitle(title, PacketPlayOutTitle.Action.TITLE);
            PacketPlayOutTitle fTitle = new PacketPlayOutTitle(title, PacketPlayOutTitle.Action.SUBTITLE);
            this.connection.sendPackets(times, hTitle, fTitle);
        } else {
            ClientboundSetTitlesAnimationPacket anim = new ClientboundSetTitlesAnimationPacket(title);
            ClientboundSetTitleTextPacket hTitlePack = new ClientboundSetTitleTextPacket(title);
            ClientboundSetSubtitleTextPacket fTitlePack = new ClientboundSetSubtitleTextPacket(title);
            this.connection.sendPackets(anim, hTitlePack, fTitlePack);
        }
    }

    public void sendTitleAsync(TitleData title) {
        ITaskAccess.get().doAsync(() -> sendTitle(title));
    }

    public void trackChunk(ChunkCoordIntPair pos, Packet packet) throws NoSuchMethodException {
        if(trackChunkMethod == null)
            throw new NoSuchMethodException("Method NetworkPlayer#trackChunk(ChunkCoordIntPair, Packet) not available on this server version!");
        this.handle.invoke(trackChunkMethod, pos.createOriginal(), packet.getRawPacket());
    }

    public void untrackChunk(ChunkCoordIntPair pos) throws NoSuchMethodException {
        if(untrackChunkMethod == null)
            throw new NoSuchMethodException("Method NetworkPlayer#untrackChunk(ChunkCoordIntPair) not available on this server version!");
        this.handle.invoke(untrackChunkMethod, pos);
    }



    private static Field pingField;
    private static Method getCommandListenerWrapperMethod, setPlayerInputMethod, restoreFromMethod, trackChunkMethod, untrackChunkMethod;
    private static Method sendChatMessage_v_1_19_1_2, sendChatMessageOldMethod;

    public static void init() {
        trackChunkMethod = Reflect.getMethod(ClassAccessor.get().getEntityPlayerClass(), Void.TYPE, true, ChunkCoordIntPair.clazz(), ClassAccessor.get().getMinecraftPacketClass());
        untrackChunkMethod = Reflect.getMethod(ClassAccessor.get().getEntityPlayerClass(), Void.TYPE, true, ChunkCoordIntPair.clazz());
        restoreFromMethod = Reflect.getMethod(ClassAccessor.get().getEntityPlayerClass(), Void.TYPE, true, ClassAccessor.get().getEntityPlayerClass(), boolean.class);
        setPlayerInputMethod = Reflect.getMethod(ClassAccessor.get().getEntityPlayerClass(), Void.TYPE, false, float.class, float.class, boolean.class, boolean.class);
        pingField = Reflect.getField(ClassAccessor.get().getEntityPlayerClass(), "ping", true);
        if(ProtocolCommandListenerWrapper.clazz() != null){
            getCommandListenerWrapperMethod = Reflect.getMethod(ClassAccessor.get().getMinecraftEntityClass(), ProtocolCommandListenerWrapper.clazz(), true);
        }
        sendChatMessageOldMethod = Reflect.getMethod(ClassAccessor.get().getEntityPlayerClass(), Void.TYPE, true, ChatComponent.clazz());
        sendChatMessage_v_1_19_1_2 = Reflect.getMethod(ClassAccessor.get().getEntityPlayerClass(), "a", Void.TYPE, true, ChatComponent.clazz());
    }

    @Override
    public boolean isPermissionSet(@NotNull String s) {
        return this.player.isPermissionSet(s);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission permission) {
        return this.player.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(@NotNull String s) {
        return this.player.hasPermission(s);
    }

    @Override
    public boolean hasPermission(@NotNull Permission permission) {
        return this.player.hasPermission(permission);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b) {
        return this.player.addAttachment(plugin, s, b);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return this.player.addAttachment(plugin);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b, int i) {
        return this.player.addAttachment(plugin, s, b, i);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, int i) {
        return this.player.addAttachment(plugin, i);
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment permissionAttachment) {
        this.player.removeAttachment(permissionAttachment);
    }

    @Override
    public void recalculatePermissions() {
        this.player.recalculatePermissions();
    }

    @NotNull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return this.player.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return this.player.isOp();
    }

    @Override
    public void setOp(boolean b) {
        this.player.setOp(b);
    }
}
