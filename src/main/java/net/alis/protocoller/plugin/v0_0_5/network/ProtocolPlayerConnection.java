package net.alis.protocoller.plugin.v0_0_5.network;

import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.samples.network.NetworkManager;
import net.alis.protocoller.samples.network.PlayerConnection;
import net.alis.protocoller.samples.network.chat.PlayerChatMessage;
import net.alis.protocoller.util.AccessedObject;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public class ProtocolPlayerConnection implements PlayerConnection {

    private final AccessedObject handle;
    private final Player player;
    private final ProtocolNetworkManager networkManager;

    public ProtocolPlayerConnection(Player player) {
        this.player = player;
        this.handle = new AccessedObject(MinecraftReflection.getPlayerConnection(player));
        this.networkManager = new ProtocolNetworkManager(this.handle.readField(0, ClassAccessor.get().getNetworkManagerClass()));
        this.chatMethod = Reflect.getMethod(ClassAccessor.get().getPlayerConnectionClass(), "chat", Void.TYPE, true, String.class, Boolean.TYPE);
        if(this.chatMethod == null) this.chatMethod = Reflect.getMethod(ClassAccessor.get().getPlayerConnectionClass(), "chat", Void.TYPE, true, String.class, ClassAccessor.get().getPlayerChatMessageClass(), Boolean.TYPE);
        this.disconnectMethod = Reflect.getMethod(ClassAccessor.get().getPlayerConnectionClass(), "disconnect", Void.TYPE, true, String.class);
        this.processedDisconnectField = Reflect.getField(ClassAccessor.get().getPlayerConnectionClass(), "processedDisconnect", true);
        this.lastTickField = Reflect.getField(ClassAccessor.get().getPlayerConnectionClass(), "lastTick", true);
        this.lastDropTickField = Reflect.getField(ClassAccessor.get().getPlayerConnectionClass(), "lastDropTick", true);
        this.dropCountField = Reflect.getField(ClassAccessor.get().getPlayerConnectionClass(), "dropCount", true);
        this.allowedPlayerTicksField = Reflect.getField(ClassAccessor.get().getPlayerConnectionClass(), "allowedPlayerTicks", true);
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

    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    @Override
    public void sendPacket(PlayOutPacket packet) {
        this.networkManager.sendPacket(packet);
    }

    public void sendPackets(PlayOutPacket @NotNull ... packets) {
        for(PlayOutPacket pack : packets) sendPacket(pack);
    }



    public void disconnect(String reason) {
        Reflect.callMethod(this.handle.getOriginal(), disconnectMethod, false, reason);
    }

    public int getAllowedPlayerTicks() {
        return Reflect.readField(this.handle.getOriginal(), allowedPlayerTicksField, false);
    }

    public void setAllowedPlayerTicks(int ticks) {
        Reflect.writeField(this.handle.getOriginal(), allowedPlayerTicksField, ticks, false);
    }

    public int getDropCount() {
        return Reflect.readField(this.handle.getOriginal(), dropCountField, false);
    }

    public void setDropCount(int count) {
        Reflect.writeField(this.handle.getOriginal(), dropCountField, count, false);
    }

    public int getLastDropTick() {
        return Reflect.readField(this.handle.getOriginal(), lastDropTickField, false);
    }

    public void setLastDropTick(int lastDropTick) {
        Reflect.writeField(this.handle.getOriginal(), lastDropTickField, lastDropTick, false);
    }

    public int getLastTick() {
        return Reflect.readField(this.handle.getOriginal(), lastTickField, false);
    }

    public void setLastTick(int lastTick) {
        Reflect.writeField(this.handle.getOriginal(), lastTickField, lastTick, false);
    }

    public boolean processedDisconnect() {
        return Reflect.readField(this.handle.getOriginal(), processedDisconnectField, false);
    }

    public void chat(String msg, boolean async) {
        if(IProtocolAccess.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_19)) {
            Reflect.callMethod(this.handle.getOriginal(), chatMethod, false, msg, async);
        } else {
            Reflect.callMethod(this.handle.getOriginal(), chatMethod, false, msg, PlayerChatMessage.system(msg).createOriginal(), async);
        }
    }

    public Method getChatMethod() {
        return chatMethod;
    }

    public Player getPlayer() {
        return player;
    }

    private Method chatMethod, disconnectMethod;
    private final Field processedDisconnectField, lastTickField, lastDropTickField, dropCountField, allowedPlayerTicksField;

    public AccessedObject getHandle() {
        return handle;
    }
}
