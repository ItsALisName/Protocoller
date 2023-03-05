package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class ClientboundSetSimulationDistancePacket implements Packet {

    private final PacketDataContainer packetData;
    private int decodedInt$0;

    public ClientboundSetSimulationDistancePacket(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.decodedInt$0 = packetData.readInt(0);
    }

    public ClientboundSetSimulationDistancePacket(int decodedInt$0) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, decodedInt$0));
        this.decodedInt$0 = decodedInt$0;
    }

    public int getDecodedInt$0() {
        return decodedInt$0;
    }

    public void setDecodedInt$0(int decodedInt$0) {
        this.packetData.writeInt(0, decodedInt$0);
        this.decodedInt$0 = decodedInt$0;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_SIMULATION_DISTANCE_PACKET;
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
