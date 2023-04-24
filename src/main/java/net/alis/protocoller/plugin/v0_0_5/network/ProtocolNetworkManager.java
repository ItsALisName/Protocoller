package net.alis.protocoller.plugin.v0_0_5.network;

import io.netty.channel.Channel;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.authlib.properties.Property;
import net.alis.protocoller.samples.network.NetworkManager;
import net.alis.protocoller.samples.network.ProtocolDirection;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.util.UUID;
import java.util.function.Predicate;

public class ProtocolNetworkManager implements NetworkManager {

    private final AccessedObject handle;
    private final Channel channel;

    public ProtocolNetworkManager(Object networkManager) {
        this.handle = new AccessedObject(networkManager);
        this.channel = this.handle.readField(0, Channel.class);
        this.spoofedUUIDField = Reflect.getField(ClassAccessor.get().getNetworkManagerClass(), "spoofedUUID", true);
        this.preparingField = Reflect.getField(ClassAccessor.get().getNetworkManagerClass(), "preparing", true);
        this.spoofedProfileField = Reflect.getField(ClassAccessor.get().getNetworkManagerClass(), "spoofedProfile", true);
        this.sendPacketMethod = Reflect.getMethod(ClassAccessor.get().getNetworkManagerClass(), Void.TYPE, true, ClassAccessor.get().getMinecraftPacketClass());
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

    public Property[] getSpoofedProfiles() {
        Object[] props = Reflect.readField(this.handle.getOriginal(), spoofedProfileField, false);
        Property[] properties = new Property[props.length];
        int i = 0;
        for(Object prop : props) {
            properties[i] = new Property(prop);
            i += 1;
        }
        return properties;
    }

    public ProtocolDirection getProtocolDirection() {
        return ProtocolDirection.getById(((Enum<?>)this.handle.readField(0, ClassAccessor.get().getProtocolDirectionEnum())).ordinal());
    }

    public void setSpoofedProfiles(Property @NotNull [] properties) {
        Object[] props = new Object[properties.length];
        int i = 0;
        for(Property property : properties) {
            props[i] = property.createOriginal();
        }
        Reflect.writeField(this.handle.getOriginal(), spoofedProfileField, props, false);
    }

    public UUID getSpoofedUUID() {
        return Reflect.readField(this.handle.getOriginal(), spoofedUUIDField, false);
    }

    public void setSpoofedUUID(UUID uuid) {
        Reflect.writeField(this.handle.getOriginal(), spoofedUUIDField, uuid, false);
    }

    public boolean isPreparing() {
        return Reflect.readField(this.handle.getOriginal(), preparingField, false);
    }

    public void setPreparing(boolean preparing) {
        Reflect.writeField(this.handle.getOriginal(), preparingField, preparing, false);
    }

    public SocketAddress getSocketAddress() {
        return this.handle.readField(0, SocketAddress.class);
    }

    public void setSocketAddress(SocketAddress address) {
        this.handle.write(0, address);
    }

    public Channel getChannel() {
        return channel;
    }

    public void sendPacket(@NotNull Packet packet) {
        Reflect.callMethod(this.handle.getOriginal(), sendPacketMethod, false, packet.getRawPacket());
    }

    private final Field spoofedUUIDField, preparingField, spoofedProfileField;
    private final Method sendPacketMethod;

    public AccessedObject getHandle() {
        return handle;
    }
}
