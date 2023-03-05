package net.alis.protocoller.packet.packets.status;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketStatusInPing implements Packet {

    private final PacketDataContainer packetData;
    private long startTime;

    public PacketStatusInPing(PacketDataContainer data) {
        this.packetData = data;
        this.startTime = data.readLong(0);
    }

    public PacketStatusInPing(long startTime) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        if(converter.getConstructorIndicator().getLevel() > 0) {
            this.packetData = new PacketDataSerializer(converter.buildPacket(null, startTime));
        } else {
            IndexedParam<?, ?>[] params = {
                    new IndexedParam<>(startTime, 0)
            };
            this.packetData = new PacketDataSerializer(converter.buildPacket(params));
        }
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.packetData.writeLong(0, startTime);
        this.startTime = startTime;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Status.Client.PING;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }

    @Override
    public PacketDataContainer getPacketData() {
        return this.packetData;
    }
}
