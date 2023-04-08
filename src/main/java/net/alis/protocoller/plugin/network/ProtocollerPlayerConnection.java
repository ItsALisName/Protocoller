package net.alis.protocoller.plugin.network;

import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.exception.ProtocollerException;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.samples.network.NetworkManager;
import net.alis.protocoller.samples.network.PlayerConnection;
import net.alis.protocoller.samples.network.chat.PlayerChatMessage;
import net.alis.protocoller.util.AccessedObject;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProtocollerPlayerConnection implements PlayerConnection {

    private final AccessedObject handle;
    private final Player player;
    private final ProtocollerNetworkManager networkManager;

    public ProtocollerPlayerConnection(Player player) {
        this.player = player;
        this.handle = new AccessedObject(MinecraftReflection.getPlayerConnection(player));
        this.networkManager = new ProtocollerNetworkManager(this.handle.read(0, ClassesContainer.get().getNetworkManagerClass()));
        this.chatMethod = Reflect.getMethod(ClassesContainer.get().getPlayerConnectionClass(), "chat", Void.TYPE, true, String.class, Boolean.TYPE);
        if(this.chatMethod == null) this.chatMethod = Reflect.getMethod(ClassesContainer.get().getPlayerConnectionClass(), "chat", Void.TYPE, true, String.class, ClassesContainer.get().getPlayerChatMessageClass(), Boolean.TYPE);
        this.disconnectMethod = Reflect.getMethod(ClassesContainer.get().getPlayerConnectionClass(), "disconnect", Void.TYPE, true, String.class);
        this.processedDisconnectField = Reflect.getField(ClassesContainer.get().getPlayerConnectionClass(), "processedDisconnect", true);
        this.lastTickField = Reflect.getField(ClassesContainer.get().getPlayerConnectionClass(), "lastTick", true);
        this.lastDropTickField = Reflect.getField(ClassesContainer.get().getPlayerConnectionClass(), "lastDropTick", true);
        this.dropCountField = Reflect.getField(ClassesContainer.get().getPlayerConnectionClass(), "dropCount", true);
        this.allowedPlayerTicksField = Reflect.getField(ClassesContainer.get().getPlayerConnectionClass(), "allowedPlayerTicks", true);
    }

    @Override
    public void setParameter(int index, Class<?> type, Object parameter) throws ProtocollerException {
        this.handle.writeSpecify(index, type, parameter);
    }

    @Override
    public <P> P getParameter(int index, Class<?> type) throws ProtocollerException {
        return this.handle.read(index, type);
    }

    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    @Override
    public void sendPacket(PlayOutPacket packet) {
        this.networkManager.sendPacket(packet);
    }

    public void disconnect(String reason) {
        Reflect.callMethod(this.handle.getObject(), disconnectMethod, false, reason);
    }

    public int getAllowedPlayerTicks() {
        return Reflect.readField(this.handle.getObject(), allowedPlayerTicksField, false);
    }

    public void setAllowedPlayerTicks(int ticks) {
        Reflect.writeField(this.handle.getObject(), allowedPlayerTicksField, ticks, false);
    }

    public int getDropCount() {
        return Reflect.readField(this.handle.getObject(), dropCountField, false);
    }

    public void setDropCount(int count) {
        Reflect.writeField(this.handle.getObject(), dropCountField, count, false);
    }

    public int getLastDropTick() {
        return Reflect.readField(this.handle.getObject(), lastDropTickField, false);
    }

    public void setLastDropTick(int lastDropTick) {
        Reflect.writeField(this.handle.getObject(), lastDropTickField, lastDropTick, false);
    }

    public int getLastTick() {
        return Reflect.readField(this.handle.getObject(), lastTickField, false);
    }

    public void setLastTick(int lastTick) {
        Reflect.writeField(this.handle.getObject(), lastTickField, lastTick, false);
    }

    public boolean processedDisconnect() {
        return Reflect.readField(this.handle.getObject(), processedDisconnectField, false);
    }

    public void chat(String msg, boolean async) {
        if(GlobalProvider.instance().getServer().getVersion().lessThanOrEqualTo(Version.v1_19)) {
            Reflect.callMethod(this.handle.getObject(), chatMethod, false, msg, async);
        } else {
            Reflect.callMethod(this.handle.getObject(), chatMethod, false, msg, PlayerChatMessage.system(msg).createOriginal(), async);
        }
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
