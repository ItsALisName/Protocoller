package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInHeldItemSlot implements Packet {

    private final PacketDataContainer packetData;
    private int selectedSlot;

    public PacketPlayInHeldItemSlot(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.selectedSlot = packetData.readInt(0);
    }

    public PacketPlayInHeldItemSlot(int selectedSlot) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(new IndexedParam[]{new IndexedParam<>(selectedSlot, 0)}));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, selectedSlot));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.selectedSlot = selectedSlot;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.packetData.writeInt(0, selectedSlot);
        this.selectedSlot = selectedSlot;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.HELD_ITEM_SLOT;
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
