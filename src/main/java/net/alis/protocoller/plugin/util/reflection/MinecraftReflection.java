package net.alis.protocoller.plugin.util.reflection;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.util.AccessedObject;
import org.bukkit.Chunk;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class MinecraftReflection {

    public static Object getCraftEntity(Entity entity) {
        return ClassesContainer.get().getCraftEntityClass().cast(entity);
    }

    public static Object getMinecraftEntity(Entity entity) {
        return new AccessedObject(getCraftEntity(entity)).read(0, ClassesContainer.get().getMinecraftEntityClass());
    }

    public static Entity entityFromMinecraftEntity(Object minecraftEntity) {
        return new AccessedObject(minecraftEntity).read(0, ClassesContainer.get().getCraftEntityClass());
    }

    public static Entity entityFromCraftEntity(Object craftEntity) {
        return (Entity) craftEntity;
    }

    public static Object getCraftItemStack(ItemStack stack) {
        return ClassesContainer.get().getCraftItemStackClass().cast(stack);
    }

    public static Object getMinecraftItemStack(ItemStack stack) {
        return new AccessedObject(getCraftItemStack(stack)).read(0, ClassesContainer.get().getMinecraftItemStackClass());
    }

    public static ItemStack itemStackFromMinecraftItemStack(Object minecraftItemStack) {
        return new AccessedObject(minecraftItemStack).read(0, ClassesContainer.get().getCraftItemStackClass());
    }

    public static ItemStack itemStackFromCraftItemStack(Object craftItemStack) {
        return (ItemStack) craftItemStack;
    }

    public static Object getCraftAdvancement(Advancement advancement) {
        return ClassesContainer.get().getCraftAdvancementClass().cast(advancement);
    }

    public static Object getMinecraftAdvancement(Advancement advancement) {
        return new AccessedObject(getCraftAdvancement(advancement)).read(0, ClassesContainer.get().getMinecraftAdvancementClass());
    }

    public static Advancement advancementFromMinecraftAdvancement(Object minecraftAdvancement) {
        return new AccessedObject(minecraftAdvancement).read(0, Advancement.class);
    }

    public static Advancement advancementFromCraftAdvancement(Object craftAdvancement) {
        return (Advancement) craftAdvancement;
    }

    public static Object getCraftAdvancementProgress(AdvancementProgress progress) {
        return ClassesContainer.get().getCraftAdvancementProgress().cast(progress);
    }

    public static Object getMinecraftAdvancementProgress(AdvancementProgress progress) {
        return new AccessedObject(getCraftAdvancementProgress(progress)).read(0, ClassesContainer.get().getMinecraftAdvancementProgress());
    }

    public static @NotNull AdvancementProgress advancementProgressFromMinecraftProgress(Advancement advancement, Player player, Object minecraftAP) {
        AccessedObject entityPlayer = new AccessedObject(PlayerReflection.getEntityPlayer(player));
        return BaseReflection.callConstructor(
                BaseReflection.getConstructor(ClassesContainer.get().getCraftAdvancementProgress(), ClassesContainer.get().getCraftAdvancementClass(), ClassesContainer.get().getAdvancementPlayerDataClass(), ClassesContainer.get().getMinecraftAdvancementProgress()),
                getCraftAdvancement(advancement), entityPlayer.read(0, ClassesContainer.get().getAdvancementPlayerDataClass()), minecraftAP
        );
    }

    public static AdvancementProgress advancementProgressFromCraftProgress(Object craftProgress) {
        return (AdvancementProgress) craftProgress;
    }

    public static <CraftBlock> CraftBlock getCraftBlock(Block block) {
        return (CraftBlock) ClassesContainer.get().getCraftBlockClass().cast(block);
    }

    public static Block blockFromCraftBlock(Object craftBlock) {
        return (Block) craftBlock;
    }

    public static Object getCraftChunk(Chunk chunk) {
        return ClassesContainer.get().getCraftChunkClass().cast(chunk);
    }

    public static Object getMinecraftChunk(Chunk chunk) {
        return ((WeakReference<?>) BaseReflection.readField(getCraftChunk(chunk), 0, WeakReference.class)).get();
    }

    public static Chunk chunkFromMinecraftChunk(Object minecraftChunk) {
        return BaseReflection.readField(minecraftChunk, 0, Chunk.class);
    }

    public static Chunk chunkFromCraftChunk(Object craftChunk) {
        return (Chunk) craftChunk;
    }

}
