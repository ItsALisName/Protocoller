package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class ClientboundPingPacket implements Packet {

    private final PacketDataSerializer packetData;
    private int parameter;

    public ClientboundPingPacket(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.parameter = packetData.readInt(0);
    }

    public ClientboundPingPacket(int parameter) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, parameter));
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
        return PacketType.Play.Server.PING_PACKET;
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
