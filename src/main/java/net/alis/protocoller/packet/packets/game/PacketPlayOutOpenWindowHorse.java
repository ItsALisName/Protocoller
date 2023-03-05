package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutOpenWindowHorse implements Packet {

    private final PacketDataContainer packetData;
    private int syncId;
    private int slotCount;
    private int horseId;

    public PacketPlayOutOpenWindowHorse(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.syncId = packetData.readInt(0);
        this.slotCount = packetData.readInt(1);
        this.horseId = packetData.readInt(2);
    }

    public PacketPlayOutOpenWindowHorse(int syncId, int slotCount, int horseId) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, syncId, slotCount, horseId));
        this.syncId = syncId;
        this.slotCount = slotCount;
        this.horseId = horseId;
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.packetData.writeInt(0, syncId);
        this.syncId = syncId;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.packetData.writeInt(1, slotCount);
        this.slotCount = slotCount;
    }

    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horseId) {
        this.packetData.writeInt(2, horseId);
        this.horseId = horseId;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.OPEN_WINDOW_HORSE;
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
