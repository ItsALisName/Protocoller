package net.alis.protocoller.packet.packets.status;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketStatusInPing implements Packet {

    private final PacketDataSerializer packetData;
    private long startTime;

    public PacketStatusInPing(PacketDataSerializer data) {
        this.packetData = new PacketDataSerializer(data.getRawPacket());
        this.startTime = data.readLong(0);
    }

    public PacketStatusInPing(long startTime) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        if(converter.getConstructorIndicator().getLevel() > 0) {
            this.packetData = new PacketDataSerializer(converter.create(null, startTime));
        } else {
            IndexedParam<?, ?>[] params = {
                    new IndexedParam<>(startTime, 0)
            };
            this.packetData = new PacketDataSerializer(converter.create(params));
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
    public PacketDataSerializer getPacketData() {
        return this.packetData;
    }
}
