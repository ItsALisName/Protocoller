package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class ClientboundPlayerCombatEndPacket implements Packet {

    private final PacketDataSerializer packetData;
    private int attackerId;
    private int timeSinceLastAttack;

    public ClientboundPlayerCombatEndPacket(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.attackerId = packetData.readInt(0);
        this.timeSinceLastAttack = packetData.readInt(1);
    }

    public ClientboundPlayerCombatEndPacket(int attackerId, int timeSinceLastAttack) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, attackerId, timeSinceLastAttack));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
