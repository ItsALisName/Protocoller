package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.reflection.AlMinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import org.bukkit.inventory.ItemStack;

public class PacketPlayInSetCreativeSlot implements Packet {

    private final PacketDataContainer packetData;
    private int slot;
    private ItemStack itemStack;

    public PacketPlayInSetCreativeSlot(PacketDataContainer packetData) {
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
                    new IndexedParam<>(AlMinecraftReflection.getMinecraftItemStack(itemStack), 0)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, slot, AlMinecraftReflection.getMinecraftItemStack(itemStack)));
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
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
