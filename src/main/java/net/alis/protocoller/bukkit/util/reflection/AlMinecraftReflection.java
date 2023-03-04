package net.alis.protocoller.bukkit.util.reflection;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.util.ObjectAccessor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

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

}
