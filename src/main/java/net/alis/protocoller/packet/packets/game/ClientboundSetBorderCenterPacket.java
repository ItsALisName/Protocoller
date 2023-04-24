package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.v0_0_5.server.level.border.ProtocolWorldBorder;
import net.alis.protocoller.samples.server.world.level.border.IWorldBorder;
import org.jetbrains.annotations.NotNull;

public class ClientboundSetBorderCenterPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private double newCenterX;
    private double newCenterZ;

    public ClientboundSetBorderCenterPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.newCenterX = packetData.readDouble(0);
        this.newCenterZ = packetData.readDouble(1);
    }

    public ClientboundSetBorderCenterPacket(@NotNull IWorldBorder border) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, ((ProtocolWorldBorder)border).getHandle().getOriginal()));
        this.newCenterX = border.getCenterX();
        this.newCenterZ = border.getCenterZ();
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

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_BORDER_CENTER_PACKET;
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
