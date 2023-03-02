package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInEnchantItem implements Packet {

    private final PacketDataSerializer packetData;
    private int syncId;
    private int buttonId;

    public PacketPlayInEnchantItem(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.syncId = packetData.readInt(0);
        this.buttonId = packetData.readInt(1);
    }

    public PacketPlayInEnchantItem(int syncId, int buttonId) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        if(converter.getConstructorIndicator().getLevel() != 0) {
            this.packetData = new PacketDataSerializer(converter.create(null, syncId, buttonId));
        } else {
            IndexedParam<?,?>[] params = {
                    new IndexedParam<>(syncId, 0),
                    new IndexedParam<>(buttonId, 1)
            };
            this.packetData = new PacketDataSerializer(converter.create(params));
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
    public PacketDataSerializer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
