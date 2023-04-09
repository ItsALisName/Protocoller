package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.inventory.RecipeBookType;

import org.jetbrains.annotations.NotNull;

public class PacketPlayInRecipeSettings implements PlayInPacket {

    private final PacketDataContainer packetData;
    private RecipeBookType category;
    private boolean guiOpen;
    private boolean filteringCraftable;

    public PacketPlayInRecipeSettings(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.category = RecipeBookType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getRecipeBookTypeEnum()).ordinal());
        this.guiOpen = packetData.readBoolean(0);
        this.filteringCraftable = packetData.readBoolean(1);
    }

    public PacketPlayInRecipeSettings(RecipeBookType category, boolean guiOpen, boolean filteringCraftable) {
        PacketBuilder builder = PacketBuilder.get(getPacketType());
        switch (builder.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(category.original(), 0),
                        new IndexedParam<>(guiOpen, 0),
                        new IndexedParam<>(filteringCraftable, 1)
                };
                this.packetData = new PacketDataSerializer(builder.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, category.original(), guiOpen, filteringCraftable));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.category = category;
        this.guiOpen = guiOpen;
        this.filteringCraftable = filteringCraftable;
    }

    public RecipeBookType getCategory() {
        return category;
    }

    public void setCategory(@NotNull RecipeBookType category) {
        this.packetData.writeEnumConstant(0, category.original());
        this.category = category;
    }

    public boolean isGuiOpen() {
        return guiOpen;
    }

    public void setGuiOpen(boolean guiOpen) {
        this.packetData.writeBoolean(0, guiOpen);
        this.guiOpen = guiOpen;
    }

    public boolean isFilteringCraftable() {
        return filteringCraftable;
    }

    public void setFilteringCraftable(boolean filteringCraftable) {
        this.packetData.writeBoolean(1, filteringCraftable);
        this.filteringCraftable = filteringCraftable;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.RECIPE_SETTINGS;
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
