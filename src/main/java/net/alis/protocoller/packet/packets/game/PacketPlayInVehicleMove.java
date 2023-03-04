package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.util.annotations.AddedSince;
import org.bukkit.entity.Entity;

import static net.alis.protocoller.bukkit.enums.Version.v1_9;

@AddedSince(v1_9)
public class PacketPlayInVehicleMove implements Packet {

    private final PacketDataSerializer packetData;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public PacketPlayInVehicleMove(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.x = packetData.readDouble(0);
        this.y = packetData.readDouble(1);
        this.z = packetData.readDouble(2);
        this.yaw = packetData.readFloat(0);
        this.pitch = packetData.readFloat(1);
    }

    public PacketPlayInVehicleMove(double x, double y, double z, float yaw, float pitch) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, x, y, z, yaw, pitch));
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public PacketPlayInVehicleMove(Entity entity) {
        this(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), entity.getLocation().getYaw(), entity.getLocation().getPitch());
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

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.packetData.writeFloat(0, yaw);
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.packetData.writeFloat(1, pitch);
        this.pitch = pitch;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.VEHICLE_MOVE;
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
