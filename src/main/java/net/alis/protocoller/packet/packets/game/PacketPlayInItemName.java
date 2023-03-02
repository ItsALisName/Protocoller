package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInItemName implements Packet {

    private final PacketDataSerializer packetData;
    private String itemName;

    public PacketPlayInItemName(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.itemName = packetData.readString(0);
    }

    public PacketPlayInItemName(String itemName) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, itemName));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
