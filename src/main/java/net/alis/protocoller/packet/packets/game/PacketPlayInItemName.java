package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInItemName implements Packet {

    private final PacketDataContainer packetData;
    private String itemName;

    public PacketPlayInItemName(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.itemName = packetData.readString(0);
    }

    public PacketPlayInItemName(String itemName) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, itemName));
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.packetData.writeString(0, itemName);
        this.itemName = itemName;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ITEM_NAME;
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
