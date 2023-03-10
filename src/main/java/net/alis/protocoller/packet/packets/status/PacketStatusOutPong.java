package net.alis.protocoller.packet.packets.status;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.StatusOutPacket;
import org.jetbrains.annotations.NotNull;

public class PacketStatusOutPong implements StatusOutPacket {

    private final PacketDataContainer packetData;
    private long startTime;

    public PacketStatusOutPong(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.startTime = packetData.readLong(0);
    }

    public PacketStatusOutPong(long startTime) {
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
        return PacketType.Status.Server.PONG;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
