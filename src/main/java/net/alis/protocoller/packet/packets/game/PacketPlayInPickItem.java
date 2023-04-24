package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;

import org.jetbrains.annotations.NotNull;

public class PacketPlayInPickItem implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int slot;

    public PacketPlayInPickItem(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.slot = packetData.readInt(0);
    }

    public PacketPlayInPickItem(int slot) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getPacketLevel().getLevel()) {
            case 0: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(new IndexedParam[]{new IndexedParam<>(slot, 0)}));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, slot));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
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
        return PacketType.Play.Client.PICK_ITEM;
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
