package net.alis.protocoller.packet.packets.status;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.network.status.ServerPing;

public class PacketStatusOutServerInfo implements Packet {

    private final PacketDataSerializer packetData;
    private ServerPing metadata;

    public PacketStatusOutServerInfo(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.metadata = packetData.readServerPing(0);
    }

    public PacketStatusOutServerInfo(ServerPing metadata) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(metadata.createOriginal(), 0)};
                this.packetData = new PacketDataSerializer(converter.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.create(null, metadata.createOriginal()));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
