package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutHeldItemSlot implements Packet {

    private final PacketDataSerializer packetData;
    private int slot;

    public PacketPlayOutHeldItemSlot(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.slot = packetData.readInt(0);
    }

    public PacketPlayOutHeldItemSlot(int slot) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, slot));
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.packetData.writeInt(0, slot);
        this.slot = slot;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.HELD_ITEM_SLOT;
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
