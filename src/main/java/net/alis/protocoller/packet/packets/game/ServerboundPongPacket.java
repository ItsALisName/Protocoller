package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class ServerboundPongPacket implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int parameter;

    public ServerboundPongPacket(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.parameter = packetData.readInt(0);
    }

    public ServerboundPongPacket(int parameter) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, parameter));
        this.parameter = parameter;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.packetData.writeInt(0, parameter);
        this.parameter = parameter;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.PONG_PACKET;
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
