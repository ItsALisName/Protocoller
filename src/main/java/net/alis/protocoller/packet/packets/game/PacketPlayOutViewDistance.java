package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutViewDistance implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int distance;

    public PacketPlayOutViewDistance(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.distance = packetData.readInt(0);
    }

    public PacketPlayOutViewDistance(int distance) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, distance));
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.packetData.writeInt(0, distance);
        this.distance = distance;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.VIEW_DISTANCE;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
