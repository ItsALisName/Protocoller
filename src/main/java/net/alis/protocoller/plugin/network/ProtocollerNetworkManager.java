package net.alis.protocoller.plugin.network;

import io.netty.channel.Channel;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.data.ClassesContainer;
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

public class ProtocollerNetworkManager implements NetworkManager {

    private final AccessedObject handle;
    private final Channel channel;

    public ProtocollerNetworkManager(Object networkManager) {
        this.handle = new AccessedObject(networkManager);
        this.channel = this.handle.read(0, Channel.class);
        this.spoofedUUIDField = Reflect.getField(ClassesContainer.get().getNetworkManagerClass(), "spoofedUUID", true);
        this.preparingField = Reflect.getField(ClassesContainer.get().getNetworkManagerClass(), "preparing", true);
        this.spoofedProfileField = Reflect.getField(ClassesContainer.get().getNetworkManagerClass(), "spoofedProfile", true);
        this.sendPacketMethod = Reflect.getMethod(ClassesContainer.get().getNetworkManagerClass(), Void.TYPE, true, ClassesContainer.get().getMinecraftPacketClass());
    }

    public Property[] getSpoofedProfiles() {
        Object[] props = Reflect.readField(this.handle.getObject(), spoofedProfileField, false);
        Property[] properties = new Property[props.length];
        int i = 0;
        for(Object prop : props) {
            properties[i] = new Property(prop);
            i += 1;
        }
        return properties;
    }

    public ProtocolDirection getProtocolDirection() {
        return ProtocolDirection.getById(((Enum<?>)this.handle.read(0, ClassesContainer.get().getProtocolDirectionEnum())).ordinal());
    }

    public void setSpoofedProfiles(Property @NotNull [] properties) {
        Object[] props = new Object[properties.length];
        int i = 0;
        for(Property property : properties) {
            props[i] = property.createOriginal();
        }
        Reflect.writeField(this.handle.getObject(), spoofedProfileField, props, false);
    }

    public UUID getSpoofedUUID() {
        return Reflect.readField(this.handle.getObject(), spoofedUUIDField, false);
    }

    public void setSpoofedUUID(UUID uuid) {
        Reflect.writeField(this.handle.getObject(), spoofedUUIDField, uuid, false);
    }

    public boolean isPreparing() {
        return Reflect.readField(this.handle.getObject(), preparingField, false);
    }

    public void setPreparing(boolean preparing) {
        Reflect.writeField(this.handle.getObject(), preparingField, preparing, false);
    }

    public SocketAddress getSocketAddress() {
        return this.handle.read(0, SocketAddress.class);
    }

    public void setSocketAddress(SocketAddress address) {
        this.handle.write(0, address);
    }

    public Channel getChannel() {
        return channel;
    }

    public void sendPacket(@NotNull Packet packet) {
        Reflect.callMethod(this.handle.getObject(), sendPacketMethod, false, packet.getRawPacket());
    }

    private final Field spoofedUUIDField, preparingField, spoofedProfileField;
    private final Method sendPacketMethod;

    public AccessedObject getHandle() {
        return handle;
    }
}
