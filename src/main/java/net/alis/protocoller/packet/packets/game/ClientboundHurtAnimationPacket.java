package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import org.jetbrains.annotations.NotNull;

public class ClientboundHurtAnimationPacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private int id;
    private float yaw;

    public ClientboundHurtAnimationPacket(@NotNull PacketDataContainer packetData) {
        this.packetData = packetData;
        this.id = packetData.readInt(0);
        this.yaw = packetData.readFloat(0);
    }

    public ClientboundHurtAnimationPacket(int id, float yaw) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, id, yaw));
        this.id = id;
        this.yaw = yaw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.packetData = new ClientboundHurtAnimationPacket(id, yaw).packetData;
        this.id = id;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.packetData = new ClientboundHurtAnimationPacket(id, yaw).packetData;
        this.yaw = yaw;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.HURT_ANIMATION_PACKET;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
