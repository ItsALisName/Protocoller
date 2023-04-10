package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInSetCreativeSlot implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int slot;
    private ItemStack itemStack;

    public PacketPlayInSetCreativeSlot(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.slot = packetData.readInt(0);
        this.itemStack = packetData.readMinecraftItemStack(0);
    }

    public PacketPlayInSetCreativeSlot(int slot, ItemStack itemStack) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                    new IndexedParam<>(slot, 0),
                    new IndexedParam<>(MinecraftReflection.getMinecraftItemStack(itemStack), 0)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, slot, MinecraftReflection.getMinecraftItemStack(itemStack)));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.packetData.writeInt(0, slot);
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.packetData.writeMinecraftItemStack(0, itemStack);
        this.itemStack = itemStack;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.SET_CREATIVE_SLOT;
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
