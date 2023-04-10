package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class ClientboundClearTitlesPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private boolean reset;

    public ClientboundClearTitlesPacket(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.reset = packetData.readBoolean(0);
    }

    public ClientboundClearTitlesPacket(boolean reset) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, reset));
        this.reset = reset;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.packetData.writeBoolean(0, reset);
        this.reset = reset;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.CLEAR_TITLES_PACKET;
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
