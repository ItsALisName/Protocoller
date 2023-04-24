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

public class ClientboundSetBorderLerpSizePacket implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private double oldSize;
    private double newSize;
    private long lerpTime;

    public ClientboundSetBorderLerpSizePacket(PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.oldSize = packetData.readDouble(0);
        this.newSize = packetData.readDouble(1);
        this.lerpTime = packetData.readLong(0);
    }

    public ClientboundSetBorderLerpSizePacket(IWorldBorder border) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, ((ProtocolWorldBorder)border).getHandle().getOriginal()));
        this.oldSize = border.getSize();
        this.newSize = border.getNewSize();
        this.lerpTime = border.getLerpTime();
    }

    public double getOldSize() {
        return oldSize;
    }

    public void setOldSize(double oldSize) {
        this.packetData.writeDouble(0, oldSize);
        this.oldSize = oldSize;
    }

    public double getNewSize() {
        return newSize;
    }

    public void setNewSize(double newSize) {
        this.packetData.writeDouble(1, newSize);
        this.newSize = newSize;
    }

    public long getLerpTime() {
        return lerpTime;
    }

    public void setLerpTime(long lerpTime) {
        this.packetData.writeLong(0, lerpTime);
        this.lerpTime = lerpTime;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_BORDER_LERP_SIZE_PACKET;
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
