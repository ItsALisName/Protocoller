package net.alis.protocoller.packet.packets.status;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.network.status.ServerPing;

public class PacketStatusOutServerInfo implements Packet {

    private final PacketDataContainer packetData;
    private ServerPing metadata;

    public PacketStatusOutServerInfo(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.metadata = packetData.readServerPing(0);
    }

    public PacketStatusOutServerInfo(ServerPing metadata) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(metadata.createOriginal(), 0)};
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, metadata.createOriginal()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.metadata = metadata;
    }

    public ServerPing getMetadata() {
        return metadata;
    }

    public void setMetadata(ServerPing metadata) {
        this.packetData.writeServerPing(0, metadata);
        this.metadata = metadata;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Status.Server.SERVER_INFO;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
