package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.util.annotations.AddedSince;
import org.jetbrains.annotations.NotNull;

import static net.alis.protocoller.plugin.enums.Version.v1_17;

@AddedSince(v1_17)
public class PacketPlayInLook implements PlayInPacket {

    private final PacketDataContainer packetData;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public PacketPlayInLook(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.yaw = packetData.readFloat(0);
        this.pitch = packetData.readFloat(1);
        this.onGround = packetData.readBoolean(0);
    }

    public PacketPlayInLook(float yaw, float pitch, boolean onGround) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, yaw, pitch, onGround));
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.packetData.writeFloat(0, yaw);
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.packetData.writeFloat(1, pitch);
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.packetData.writeBoolean(0, onGround);
        this.onGround = onGround;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.LOOK;
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
