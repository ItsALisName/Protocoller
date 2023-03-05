package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

import java.util.UUID;

public class PacketPlayInSpectate implements Packet {

    private final PacketDataContainer packetData;
    private UUID targetUUID;

    public PacketPlayInSpectate(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.targetUUID = packetData.readObject(0, UUID.class);
    }

    public PacketPlayInSpectate(UUID targetUUID) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, targetUUID));
        this.targetUUID = targetUUID;
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }

    public void setTargetUUID(UUID targetUUID) {
        this.packetData.writeObject(0, targetUUID);
        this.targetUUID = targetUUID;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.SPECTATE;
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
