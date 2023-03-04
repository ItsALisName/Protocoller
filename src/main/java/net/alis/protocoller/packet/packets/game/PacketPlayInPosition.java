package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.util.annotations.AddedSince;

import static net.alis.protocoller.bukkit.enums.Version.v1_17;

@AddedSince(v1_17)
public class PacketPlayInPosition implements Packet {

    private final PacketDataSerializer packetData;
    private double x;
    private double y;
    private double z;
    private boolean onGround;

    public PacketPlayInPosition(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.x = packetData.readDouble(0);
        this.y = packetData.readDouble(1);
        this.z = packetData.readDouble(2);
        this.onGround = packetData.readBoolean(0);
    }

    public PacketPlayInPosition(double x, double y, double z, boolean onGround) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, x, y, z, onGround));
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.packetData.writeDouble(0, x);
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.packetData.writeDouble(1, y);
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.packetData.writeDouble(2, z);
        this.z = z;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.packetData.writeBoolean(0, onGround);
        this.onGround = onGround;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.POSITION;
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
