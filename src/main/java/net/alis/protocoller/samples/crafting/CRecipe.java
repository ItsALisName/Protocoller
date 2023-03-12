package net.alis.protocoller.samples.crafting;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.AlMinecraftReflection;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.resources.MinecraftKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class CRecipe implements Recipe {

    private ItemStack result;
    private MinecraftKey minecraftKey;

    public CRecipe() {}

    public CRecipe(ItemStack result, MinecraftKey minecraftKey) {
        this.result = result;
        this.minecraftKey = minecraftKey;
    }

    @NotNull
    @Override
    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public @Nullable MinecraftKey getMinecraftKey() {
        return minecraftKey;
    }

    public void setMinecraftKey(MinecraftKey minecraftKey) {
        this.minecraftKey = minecraftKey;
    }

    public static class RecipeSerializer {

        private static Method getKeyMethod, getItemStackMethod;
        public static void init() {
            getKeyMethod = Reflection.getMethodNullParams(ClassesContainer.INSTANCE.getIRecipeClass(), ClassesContainer.INSTANCE.getMinecraftKeyClass());
            getItemStackMethod = Reflection.getMethodNullParams(ClassesContainer.INSTANCE.getIRecipeClass(), ClassesContainer.INSTANCE.getMinecraftItemStackClass());
        }

        public static CRecipe fromIRecipe(Object iRecipe) {
            CRecipe recipe = new CRecipe();
            recipe.setResult(AlMinecraftReflection.itemStackFromMinecraftItemStack(Reflection.callMethod(null, getItemStackMethod)));
            if(getKeyMethod != null){
                recipe.setMinecraftKey(new MinecraftKey((Object) Reflection.callMethod(null, getKeyMethod)));
            }
            return recipe;
        }
    }

}
