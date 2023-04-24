package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class ClientboundSetSimulationDistancePacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private int simulationDistance;

    public ClientboundSetSimulationDistancePacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.simulationDistance = packetData.readInt(0);
    }

    public ClientboundSetSimulationDistancePacket(int simulationDistance) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, simulationDistance));
        this.simulationDistance = simulationDistance;
    }

    public int getSimulationDistance() {
        return simulationDistance;
    }

    public void setSimulationDistance(int simulationDistance) {
        this.packetData = new ClientboundSetSimulationDistancePacket(simulationDistance).packetData;
        this.simulationDistance = simulationDistance;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_SIMULATION_DISTANCE_PACKET;
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
