package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

import java.util.List;
import java.util.UUID;

public class ClientboundPlayerInfoRemovePacket implements Packet {

    private final PacketDataSerializer packetData;
    private List<UUID> playersList;

    public ClientboundPlayerInfoRemovePacket(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.playersList = packetData.readList(0);
    }

    public ClientboundPlayerInfoRemovePacket(List<UUID> playersList) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, playersList));
        this.playersList = playersList;
    }

    public List<UUID> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<UUID> playersList) {
        this.packetData.writeList(0, playersList);
        this.playersList = playersList;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.PLAYER_INFO_REMOVE_PACKET;
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
