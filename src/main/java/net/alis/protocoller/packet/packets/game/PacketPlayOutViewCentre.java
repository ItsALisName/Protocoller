package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutViewCentre implements Packet {

    private final PacketDataContainer packetData;
    private int x;
    private int z;

    public PacketPlayOutViewCentre(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.x = packetData.readInt(0);
        this.z = packetData.readInt(0);
    }

    public PacketPlayOutViewCentre(int x, int z) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, x, z));
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.packetData.writeInt(0, x);
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.packetData.writeInt(1, z);
        this.z = z;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.VIEW_CENTRE;
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
