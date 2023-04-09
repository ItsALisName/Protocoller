package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class ClientboundInitializeBorderPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private double newCenterX;
    private double newCenterZ;
    private double oldSize;
    private double newSize;
    private long lerpTime;
    private int newAbsoluteMaxSize;
    private int warningBlocks;
    private int warningTime;

    public ClientboundInitializeBorderPacket(@NotNull PacketDataContainer packetData) {
        this.packetData = packetData;
        this.newCenterX = packetData.readDouble(0);
        this.newCenterZ = packetData.readDouble(1);
        this.oldSize = packetData.readDouble(2);
        this.newSize = packetData.readDouble(3);
        this.lerpTime = packetData.readLong(0);
        this.newAbsoluteMaxSize = packetData.readInt(0);
        this.warningBlocks = packetData.readInt(1);
        this.warningTime = packetData.readInt(2);
    }

    public double getNewCenterX() {
        return newCenterX;
    }

    public void setNewCenterX(double newCenterX) {
        this.packetData.writeDouble(0, newCenterX);
        this.newCenterX = newCenterX;
    }

    public double getNewCenterZ() {
        return newCenterZ;
    }

    public void setNewCenterZ(double newCenterZ) {
        this.packetData.writeDouble(1, newCenterZ);
        this.newCenterZ = newCenterZ;
    }

    public double getOldSize() {
        return oldSize;
    }

    public void setOldSize(double oldSize) {
        this.packetData.writeDouble(2, oldSize);
        this.oldSize = oldSize;
    }

    public double getNewSize() {
        return newSize;
    }

    public void setNewSize(double newSize) {
        this.packetData.writeDouble(3, newSize);
        this.newSize = newSize;
    }

    public long getLerpTime() {
        return lerpTime;
    }

    public void setLerpTime(long lerpTime) {
        this.packetData.writeLong(0, lerpTime);
        this.lerpTime = lerpTime;
    }

    public int getNewAbsoluteMaxSize() {
        return newAbsoluteMaxSize;
    }

    public void setNewAbsoluteMaxSize(int newAbsoluteMaxSize) {
        this.packetData.writeInt(0, newAbsoluteMaxSize);
        this.newAbsoluteMaxSize = newAbsoluteMaxSize;
    }

    public int getWarningBlocks() {
        return warningBlocks;
    }

    public void setWarningBlocks(int warningBlocks) {
        this.packetData.writeInt(1, warningBlocks);
        this.warningBlocks = warningBlocks;
    }

    public int getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(int warningTime) {
        this.packetData.writeInt(2, warningTime);
        this.warningTime = warningTime;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.INITIALIZE_BORDER_PACKET;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
