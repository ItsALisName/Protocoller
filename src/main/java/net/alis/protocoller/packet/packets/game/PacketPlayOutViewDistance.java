package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutViewDistance implements Packet {

    private final PacketDataContainer packetData;
    private int distance;

    public PacketPlayOutViewDistance(PacketDataContainer packetData) {
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
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
