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

public class ClientboundSetBorderWarningDelayPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private int warningDelay;

    public ClientboundSetBorderWarningDelayPacket(PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.warningDelay = packetData.readInt(0);
    }

    public ClientboundSetBorderWarningDelayPacket(IWorldBorder border) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, ((ProtocolWorldBorder)border).getHandle().getOriginal()));
        this.warningDelay = border.getWarningTime();
    }

    public int getWarningDelay() {
        return warningDelay;
    }

    public void setWarningDelay(int warningDelay) {
        this.packetData.writeInt(0, warningDelay);
        this.warningDelay = warningDelay;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_BORDER_WARNING_DELAY_PACKET;
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
