package net.alis.protocoller.samples.crafting;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.plugin.util.reflection.Reflect;
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
            getKeyMethod = Reflect.getMethod(ClassAccessor.get().getIRecipeClass(), 0, ClassAccessor.get().getMinecraftKeyClass(), true);
            getItemStackMethod = Reflect.getMethod(ClassAccessor.get().getIRecipeClass(), 0, ClassAccessor.get().getMinecraftItemStackClass(), true);
        }

        public static CRecipe fromIRecipe(Object iRecipe) {
            CRecipe recipe = new CRecipe();
            recipe.setResult(MinecraftReflection.itemStackFromMinecraftItemStack(Reflect.callMethod(null, getItemStackMethod, false)));
            if(getKeyMethod != null){
                recipe.setMinecraftKey(new MinecraftKey((Object) Reflect.callMethod(null, getKeyMethod, false)));
            }
            return recipe;
        }
    }

}
