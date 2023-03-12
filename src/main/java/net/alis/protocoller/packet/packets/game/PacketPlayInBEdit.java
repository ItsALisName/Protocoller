package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.util.annotations.AddedSince;
import org.jetbrains.annotations.Nullable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

import static net.alis.protocoller.bukkit.enums.Version.v1_13;

@AddedSince(v1_13)
public class PacketPlayInBEdit implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int slot;
    private List<String> pages;
    private String title;

    private @Nullable ItemStack item;

    public PacketPlayInBEdit(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(!GlobalProvider.instance().getServer().isLegacy()) {
            this.slot = packetData.readInt(4);
            this.pages = packetData.readList(0);
            this.title = ((Optional<String>)packetData.readOptional(0)).get();
        } else {
            this.item = packetData.readMinecraftItemStack(0);
        }
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(@NotNull ItemStack item) {
        if(GlobalProvider.instance().getServer().isLegacy()) {
            this.packetData.writeMinecraftItemStack(0, item);
        }
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        if(!GlobalProvider.instance().getServer().isLegacy()) {
            this.packetData.writeInt(4, slot);
        }
        this.slot = slot;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        if(!GlobalProvider.instance().getServer().isLegacy()) {
            this.packetData.writeList(0, pages);
        }
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.packetData.writeOptional(0, Optional.of(title));
        this.title = title;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.BEDIT;
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
