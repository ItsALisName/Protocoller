package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class ClientboundPlayerCombatEndPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int attackerId;
    private int timeSinceLastAttack;

    public ClientboundPlayerCombatEndPacket(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.attackerId = packetData.readInt(0);
        this.timeSinceLastAttack = packetData.readInt(1);
    }

    public ClientboundPlayerCombatEndPacket(int attackerId, int timeSinceLastAttack) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, attackerId, timeSinceLastAttack));
        this.attackerId = attackerId;
        this.timeSinceLastAttack = timeSinceLastAttack;
    }

    public int getAttackerId() {
        return attackerId;
    }

    public void setAttackerId(int attackerId) {
        this.packetData.writeInt(0, attackerId);
        this.attackerId = attackerId;
    }

    public int getTimeSinceLastAttack() {
        return timeSinceLastAttack;
    }

    public void setTimeSinceLastAttack(int timeSinceLastAttack) {
        this.packetData.writeInt(1, timeSinceLastAttack);
        this.timeSinceLastAttack = timeSinceLastAttack;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.PLAYER_COMBAT_END_PACKET;
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
