package net.alis.protocoller.packet.packets.status;

import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.StatusInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketStatusInPing implements StatusInPacket {

    private final PacketDataContainer packetData;
    private long startTime;

    public PacketStatusInPing(@NotNull PacketDataContainer data) {
        PacketUtils.checkPacketCompatibility(data.getType(), this.getPacketType());
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
    public PacketDataContainer getData() {
        return this.packetData;
    }
}
