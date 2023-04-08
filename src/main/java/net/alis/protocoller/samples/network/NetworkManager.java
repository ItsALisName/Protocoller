package net.alis.protocoller.samples.network;

import io.netty.channel.Channel;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.authlib.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.net.SocketAddress;
import java.util.UUID;

public interface NetworkManager {

    Property[] getSpoofedProfiles();

    ProtocolDirection getProtocolDirection();

    void setSpoofedProfiles(Property @NotNull [] properties);

    UUID getSpoofedUUID();

    void setSpoofedUUID(UUID uuid);

    boolean isPreparing();

    void setPreparing(boolean preparing);

    SocketAddress getSocketAddress();

    void setSocketAddress(SocketAddress address);

    Channel getChannel();

    void sendPacket(@NotNull Packet packet);

}
