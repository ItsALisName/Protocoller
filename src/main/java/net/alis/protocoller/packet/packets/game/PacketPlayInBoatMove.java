package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInBoatMove implements PlayInPacket {

    private final PacketDataContainer packetData;
    private boolean leftPaddling;
    private boolean rightPaddling;

    public PacketPlayInBoatMove(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.leftPaddling = packetData.readBoolean(0);
        this.rightPaddling = packetData.readBoolean(1);
    }

    public PacketPlayInBoatMove(boolean leftPaddling, boolean rightPaddling) {
        if(leftPaddling == rightPaddling) leftPaddling = !rightPaddling;
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, leftPaddling, rightPaddling));
        this.leftPaddling = leftPaddling;
        this.rightPaddling = rightPaddling;
    }

    public boolean isLeftPaddling() {
        return leftPaddling;
    }

    public void setLeftPaddling(boolean leftPaddling) {
        this.packetData.writeBoolean(0, leftPaddling);
        this.leftPaddling = leftPaddling;
    }

    public boolean isRightPaddling() {
        return rightPaddling;
    }

    public void setRightPaddling(boolean rightPaddling) {
        this.packetData.writeBoolean(1, rightPaddling);
        this.rightPaddling = rightPaddling;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.BOAT_MOVE;
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
