package net.alis.protocoller.bukkit.util.reflection;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import org.bukkit.inventory.ItemStack;

public class AlMinecraftReflection {

    public static ItemStack getItemStackFromCraftStack(Object craftItemStack) {
        return (ItemStack) craftItemStack;
    }

    public static Object getCraftItemStack(ItemStack stack) {
        return ClassesContainer.INSTANCE.getCraftItemStackClass().cast(stack);
    }

    public static ItemStack getItemStackFromMinecraftStack(Object minecraftStack) {
        return (ItemStack) getCraftItemStackFromMinecraftStack(minecraftStack);
    }

    public static Object getMinecraftItemStackFromItemStack(ItemStack stack) {

        return getCraftItemStack(stack);
    }

    public static Object getCraftItemStackFromMinecraftStack(Object minecraftStack) {
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getCraftItemStackClass(), ClassesContainer.INSTANCE.getMinecraftItemStackClass()),
                minecraftStack
        );
    }

}
