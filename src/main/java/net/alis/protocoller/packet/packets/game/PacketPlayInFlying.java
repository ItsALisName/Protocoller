package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInFlying implements PlayInPacket {

    private final PacketDataContainer packetData;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private boolean changePosition;
    private boolean changeLook;

    public PacketPlayInFlying(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.x = packetData.readDouble(0);
        this.y = packetData.readDouble(1);
        this.z = packetData.readDouble(2);
        this.yaw = packetData.readFloat(0);
        this.pitch = packetData.readFloat(1);
        this.onGround = packetData.readBoolean(0);
        this.changePosition = packetData.readBoolean(1);
        this.changeLook = packetData.readBoolean(2);
    }

    public PacketPlayInFlying(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean changePosition, boolean changeLook) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                    new IndexedParam<>(x, 0), new IndexedParam<>(y, 1), new IndexedParam<>(z, 2),
                    new IndexedParam<>(yaw, 0), new IndexedParam<>(pitch, 1),
                    new IndexedParam<>(onGround, 0), new IndexedParam<>(changePosition, 1), new IndexedParam<>(changeLook, 2)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, x, y, z, yaw, pitch, onGround, changePosition, changeLook));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.changePosition = changePosition;
        this.changeLook = changeLook;
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
        this.packetData.writeFloat(0, pitch);
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.packetData.writeBoolean(0, onGround);
        this.onGround = onGround;
    }

    public boolean isChangePosition() {
        return changePosition;
    }

    public void setChangePosition(boolean changePosition) {
        this.packetData.writeBoolean(1, changePosition);
        this.changePosition = changePosition;
    }

    public boolean isChangeLook() {
        return changeLook;
    }

    public void setChangeLook(boolean changeLook) {
        this.packetData.writeBoolean(2, changeLook);
        this.changeLook = changeLook;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.FLYING;
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
