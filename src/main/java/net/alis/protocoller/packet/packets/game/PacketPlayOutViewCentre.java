package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutViewCentre implements Packet {

    private final PacketDataSerializer packetData;
    private int x;
    private int z;

    public PacketPlayOutViewCentre(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.x = packetData.readInt(0);
        this.z = packetData.readInt(0);
    }

    public PacketPlayOutViewCentre(int x, int z) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, x, z));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
