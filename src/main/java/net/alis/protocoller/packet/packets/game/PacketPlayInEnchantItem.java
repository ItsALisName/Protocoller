package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInEnchantItem implements Packet {

    private final PacketDataContainer packetData;
    private int syncId;
    private int buttonId;

    public PacketPlayInEnchantItem(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.syncId = packetData.readInt(0);
        this.buttonId = packetData.readInt(1);
    }

    public PacketPlayInEnchantItem(int syncId, int buttonId) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        if(converter.getConstructorIndicator().getLevel() != 0) {
            this.packetData = new PacketDataSerializer(converter.buildPacket(null, syncId, buttonId));
        } else {
            IndexedParam<?,?>[] params = {
                    new IndexedParam<>(syncId, 0),
                    new IndexedParam<>(buttonId, 1)
            };
            this.packetData = new PacketDataSerializer(converter.buildPacket(params));
        }
        this.syncId = syncId;
        this.buttonId = buttonId;
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.packetData.writeInt(0, syncId);
        this.syncId = syncId;
    }

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int buttonId) {
        this.packetData.writeInt(1, buttonId);
        this.buttonId = buttonId;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ENCHANT_ITEM;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
