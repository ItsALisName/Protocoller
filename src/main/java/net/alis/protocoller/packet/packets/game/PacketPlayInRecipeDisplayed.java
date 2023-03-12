package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.crafting.CRecipe;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.annotations.AddedSince;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import static net.alis.protocoller.bukkit.enums.Version.v1_12;

@AddedSince(v1_12)
public class PacketPlayInRecipeDisplayed implements PlayInPacket {

    private final PacketDataContainer packetData;
    private @Nullable MinecraftKey recipeKey;
    private @Nullable CRecipe recipe;
    
    private final boolean legacyPacket = GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_13);

    public PacketPlayInRecipeDisplayed(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), PacketType.Play.Client.RECIPE_DISPLAYED);
        this.packetData = packetData;
        if(legacyPacket) {
            this.recipeKey = null;
            this.recipe = CRecipe.RecipeSerializer.fromIRecipe(packetData.readObject(0, ClassesContainer.INSTANCE.getIRecipeClass()));
        } else {
            this.recipeKey = packetData.readMinecraftKey(0);
            this.recipe = null;
        }
    }

    @Nullable
    public MinecraftKey getRecipeKey() {
        return recipeKey;
    }

    public void setRecipeKey(@Nullable MinecraftKey recipeKey) {
        if(legacyPacket)
            this.packetData.writeMinecraftKey(0, recipeKey);
        this.recipeKey = recipeKey;
    }

    @Nullable
    public CRecipe getRecipe() {
        return recipe;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.RECIPE_DISPLAYED;
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
