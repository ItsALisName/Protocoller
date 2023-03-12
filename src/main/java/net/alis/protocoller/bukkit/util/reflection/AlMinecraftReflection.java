package net.alis.protocoller.bukkit.util.reflection;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.util.ObjectAccessor;
import org.bukkit.Chunk;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class AlMinecraftReflection {

    public static Object getCraftEntity(Entity entity) {
        return ClassesContainer.INSTANCE.getCraftEntityClass().cast(entity);
    }

    public static Object getMinecraftEntity(Entity entity) {
        return new ObjectAccessor(getCraftEntity(entity)).read(0, ClassesContainer.INSTANCE.getMinecraftEntityClass());
    }

    public static Entity entityFromMinecraftEntity(Object minecraftEntity) {
        return new ObjectAccessor(minecraftEntity).read(0, ClassesContainer.INSTANCE.getCraftEntityClass());
    }

    public static Entity entityFromCraftEntity(Object craftEntity) {
        return (Entity) craftEntity;
    }

    public static Object getCraftItemStack(ItemStack stack) {
        return ClassesContainer.INSTANCE.getCraftItemStackClass().cast(stack);
    }

    public static Object getMinecraftItemStack(ItemStack stack) {
        return new ObjectAccessor(getCraftItemStack(stack)).read(0, ClassesContainer.INSTANCE.getMinecraftItemStackClass());
    }

    public static ItemStack itemStackFromMinecraftItemStack(Object minecraftItemStack) {
        return new ObjectAccessor(minecraftItemStack).read(0, ClassesContainer.INSTANCE.getCraftItemStackClass());
    }

    public static ItemStack itemStackFromCraftItemStack(Object craftItemStack) {
        return (ItemStack) craftItemStack;
    }

    public static Object getCraftAdvancement(Advancement advancement) {
        return ClassesContainer.INSTANCE.getCraftAdvancementClass().cast(advancement);
    }

    public static Object getMinecraftAdvancement(Advancement advancement) {
        return new ObjectAccessor(getCraftAdvancement(advancement)).read(0, ClassesContainer.INSTANCE.getMinecraftAdvancementClass());
    }

    public static Advancement advancementFromMinecraftAdvancement(Object minecraftAdvancement) {
        return new ObjectAccessor(minecraftAdvancement).read(0, Advancement.class);
    }

    public static Advancement advancementFromCraftAdvancement(Object craftAdvancement) {
        return (Advancement) craftAdvancement;
    }

    public static Object getCraftAdvancementProgress(AdvancementProgress progress) {
        return ClassesContainer.INSTANCE.getCraftAdvancementProgress().cast(progress);
    }

    public static Object getMinecraftAdvancementProgress(AdvancementProgress progress) {
        return new ObjectAccessor(getCraftAdvancementProgress(progress)).read(0, ClassesContainer.INSTANCE.getMinecraftAdvancementProgress());
    }

    public static @NotNull AdvancementProgress advancementProgressFromMinecraftProgress(Advancement advancement, Player player, Object minecraftAP) {
        ObjectAccessor entityPlayer = new ObjectAccessor(PlayerReflection.getEntityPlayer(player));
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getCraftAdvancementProgress(), ClassesContainer.INSTANCE.getCraftAdvancementClass(), ClassesContainer.INSTANCE.getAdvancementPlayerDataClass(), ClassesContainer.INSTANCE.getMinecraftAdvancementProgress()),
                getCraftAdvancement(advancement), entityPlayer.read(0, ClassesContainer.INSTANCE.getAdvancementPlayerDataClass()), minecraftAP
        );
    }

    public static AdvancementProgress advancementProgressFromCraftProgress(Object craftProgress) {
        return (AdvancementProgress) craftProgress;
    }

    public static <CraftBlock> CraftBlock getCraftBlock(Block block) {
        return (CraftBlock) ClassesContainer.INSTANCE.getCraftBlockClass().cast(block);
    }

    public static Block blockFromCraftBlock(Object craftBlock) {
        return (Block) craftBlock;
    }

    public static Object getCraftChunk(Chunk chunk) {
        return ClassesContainer.INSTANCE.getCraftChunkClass().cast(chunk);
    }

    public static Object getMinecraftChunk(Chunk chunk) {
        return ((WeakReference<?>)Reflection.readField(getCraftChunk(chunk), 0, WeakReference.class)).get();
    }

    public static Chunk chunkFromMinecraftChunk(Object minecraftChunk) {
        return Reflection.readField(minecraftChunk, 0, Chunk.class);
    }

    public static Chunk chunkFromCraftChunk(Object craftChunk) {
        return (Chunk) craftChunk;
    }

}
