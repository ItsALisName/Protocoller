package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketLoginOutSetCompression implements Packet {

    private final PacketDataContainer packetData;
    private int compressionThreshold;

    public PacketLoginOutSetCompression(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.compressionThreshold = packetData.readInt(0);
    }

    public PacketLoginOutSetCompression(int compressionThreshold) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        this.packetData = new PacketDataSerializer(converter.buildPacket(null, compressionThreshold));
        this.compressionThreshold = compressionThreshold;
    }

    public int getCompressionThreshold() {
        return compressionThreshold;
    }

    public void setCompressionThreshold(int compressionThreshold) {
        this.packetData.writeInt(0, compressionThreshold);
        this.compressionThreshold = compressionThreshold;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Login.Server.SET_COMPRESSION;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
