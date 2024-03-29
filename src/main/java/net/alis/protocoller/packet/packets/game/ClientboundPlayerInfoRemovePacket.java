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

import java.util.List;
import java.util.UUID;

public class ClientboundPlayerInfoRemovePacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private List<UUID> playersList;

    public ClientboundPlayerInfoRemovePacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.playersList = (List<UUID>) packetData.readList(0);
    }

    public ClientboundPlayerInfoRemovePacket(List<UUID> playersList) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, playersList));
        this.playersList = playersList;
    }

    public List<UUID> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<UUID> playersList) {
        this.packetData = new ClientboundPlayerInfoRemovePacket(playersList).packetData;
        this.playersList = playersList;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.PLAYER_INFO_REMOVE_PACKET;
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
