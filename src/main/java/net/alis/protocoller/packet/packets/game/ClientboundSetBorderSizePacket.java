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

public class ClientboundSetBorderSizePacket implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private double size;

    public ClientboundSetBorderSizePacket(PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.size = packetData.readDouble(0);
    }

    public ClientboundSetBorderSizePacket(IWorldBorder border) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, ((ProtocolWorldBorder)border).getHandle().getOriginal()));
        this.size = border.getSize();
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.packetData.writeDouble(0, size);
        this.size = size;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_BORDER_SIZE_PACKET;
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
