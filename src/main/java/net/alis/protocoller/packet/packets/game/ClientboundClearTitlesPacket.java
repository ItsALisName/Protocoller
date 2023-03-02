package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class ClientboundClearTitlesPacket implements Packet {

    private final PacketDataSerializer packetData;
    private boolean reset;

    public ClientboundClearTitlesPacket(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.reset = packetData.readBoolean(0);
    }

    public ClientboundClearTitlesPacket(boolean reset) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, reset));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
