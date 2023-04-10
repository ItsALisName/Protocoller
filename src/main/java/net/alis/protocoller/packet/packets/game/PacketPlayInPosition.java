package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;

import org.jetbrains.annotations.NotNull;

public class PacketPlayInPosition implements PlayInPacket {

    private final PacketDataContainer packetData;
    private double x;
    private double y;
    private double z;
    private boolean onGround;

    public PacketPlayInPosition(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.x = packetData.readDouble(0);
        this.y = packetData.readDouble(1);
        this.z = packetData.readDouble(2);
        this.onGround = packetData.readBoolean(0);
    }

    public PacketPlayInPosition(double x, double y, double z, boolean onGround) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, x, y, z, onGround));
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
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
