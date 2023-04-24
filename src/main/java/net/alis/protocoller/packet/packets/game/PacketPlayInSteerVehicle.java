package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInSteerVehicle implements PlayInPacket {

    private final PacketDataContainer packetData;
    private float sideways;
    private float forward;
    private boolean jumping;
    private boolean sneaking;

    public PacketPlayInSteerVehicle(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.sideways = packetData.readFloat(0);
        this.forward = packetData.readFloat(1);
        this.jumping = packetData.readBoolean(0);
        this.sneaking = packetData.readBoolean(1);
    }

    public PacketPlayInSteerVehicle(float sideways, float forward, boolean jumping, boolean sneaking) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getPacketLevel().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(sideways, 0),
                        new IndexedParam<>(forward, 1),
                        new IndexedParam<>(jumping, 0),
                        new IndexedParam<>(sneaking, 1)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, sideways, forward, jumping, sneaking));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.sideways = sideways;
        this.forward = forward;
        this.jumping = jumping;
        this.sneaking = sneaking;
    }

    public float getSideways() {
        return sideways;
    }

    public void setSideways(float sideways) {
        this.packetData.writeFloat(0, sideways);
        this.sideways = sideways;
    }

    public float getForward() {
        return forward;
    }

    public void setForward(float forward) {
        this.packetData.writeFloat(1, forward);
        this.forward = forward;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.packetData.writeBoolean(0, jumping);
        this.jumping = jumping;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.packetData.writeBoolean(1, sneaking);
        this.sneaking = sneaking;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.STEER_VEHICLE;
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
