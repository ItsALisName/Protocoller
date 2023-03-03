package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

import java.util.UUID;

public class PacketPlayInSpectate implements Packet {

    private final PacketDataSerializer packetData;
    private UUID targetUUID;

    public PacketPlayInSpectate(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.targetUUID = packetData.readObject(0, UUID.class);
    }

    public PacketPlayInSpectate(UUID targetUUID) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, targetUUID));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
