package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutWindowData implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int syncId;
    private int propertyId;
    private int value;

    public PacketPlayOutWindowData(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.syncId = packetData.readInt(0);
        this.propertyId = packetData.readInt(1);
        this.value = packetData.readInt(2);
    }

    public PacketPlayOutWindowData(int syncId, int propertyId, int value) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, syncId, propertyId, value));
        this.syncId = syncId;
        this.propertyId = propertyId;
        this.value = value;
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.packetData.writeInt(0, syncId);
        this.syncId = syncId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.packetData.writeInt(1, propertyId);
        this.propertyId = propertyId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.packetData.writeInt(2, value);
        this.value = value;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.WINDOW_DATA;
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
