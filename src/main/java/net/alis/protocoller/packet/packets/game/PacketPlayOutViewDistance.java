package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutViewDistance implements Packet {

    private final PacketDataSerializer packetData;
    private int distance;

    public PacketPlayOutViewDistance(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.distance = packetData.readInt(0);
    }

    public PacketPlayOutViewDistance(int distance) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, distance));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
