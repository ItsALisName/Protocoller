package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class ClientboundBlockChangedAckPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int sequence;

    public ClientboundBlockChangedAckPacket(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.sequence = packetData.readInt(0);
    }

    public ClientboundBlockChangedAckPacket(int sequence) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, sequence));
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.packetData.writeInt(0, sequence);
        this.sequence = sequence;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.BLOCK_CHANGED_ACK_PACKET;
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
