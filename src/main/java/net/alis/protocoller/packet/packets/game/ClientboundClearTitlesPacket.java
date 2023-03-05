package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class ClientboundClearTitlesPacket implements Packet {

    private final PacketDataContainer packetData;
    private boolean reset;

    public ClientboundClearTitlesPacket(PacketDataContainer packetData) {
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
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
