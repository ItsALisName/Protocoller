package net.alis.protocoller.samples.advancements;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.command.CustomFunction;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancementRewards implements ObjectSample {
    public static final AdvancementRewards EMPTY = new AdvancementRewards(0, new MinecraftKey[0], new MinecraftKey[0], CustomFunction.CacheableFunction.NONE);
    private int experience;
    private MinecraftKey[] loot;
    private MinecraftKey[] recipes;
    private CustomFunction.CacheableFunction function;

    public AdvancementRewards(Object advancementRewards) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(advancementRewards);
        this.experience = object.readField(0, int.class);
        Object[] keyLoot = object.readField(0, ClassAccessor.arrayOfClass(MinecraftKey.clazz()));
        Object[] keyRecipes = object.readField(1, ClassAccessor.arrayOfClass(MinecraftKey.clazz()));
        if(keyLoot != null && keyLoot.length > 0) {
            this.loot = new MinecraftKey[keyLoot.length];
            for(int i = 0; i < keyLoot.length; i++) {
                this.loot[i] = new MinecraftKey(keyLoot[i]);
            }
        }
        if(keyRecipes != null && keyRecipes.length > 0) {
            this.recipes = new MinecraftKey[keyRecipes.length];
            for(int i = 0; i < keyRecipes.length; i++) {
                this.recipes[i] = new MinecraftKey(keyRecipes[i]);
            }
        }
        Object func = object.readField(0, CustomFunction.CacheableFunction.clazz());
        if(func != null) this.function = new CustomFunction.CacheableFunction(func);
    }

    public AdvancementRewards(int experience, MinecraftKey[] loot, MinecraftKey[] recipes, CustomFunction.CacheableFunction function) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.experience = experience;
        this.loot = loot;
        this.recipes = recipes;
        this.function = function;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public MinecraftKey[] getLoot() {
        return loot;
    }

    public void setLoot(MinecraftKey[] loot) {
        this.loot = loot;
    }

    public void setRecipes(MinecraftKey[] recipes) {
        this.recipes = recipes;
    }

    public CustomFunction.CacheableFunction getFunction() {
        return function;
    }

    public void setFunction(CustomFunction.CacheableFunction function) {
        this.function = function;
    }

    public MinecraftKey[] getRecipes() {
        return this.recipes;
    }

    public String toString() {
        return "AdvancementRewards{experience=" + this.experience + ", loot=" + Arrays.toString(this.loot) + ", recipes=" + Arrays.toString((Object[])this.recipes) + ", function=" + this.function + "}";
    }

    @Override
    public Object createOriginal() {
        List<Object> lootKeys = new ArrayList<>();
        List<Object> recipeKeys = new ArrayList<>();
        if(this.loot != null && this.loot.length > 0) {
            for(MinecraftKey key : this.loot) {
                lootKeys.add(key.createOriginal());
            }
        }
        if(this.recipes != null && this.recipes.length > 0) {
            for(MinecraftKey key : this.recipes) {
                recipeKeys.add(key.createOriginal());
            }
        }
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, int.class, ClassAccessor.arrayOfClass(MinecraftKey.clazz()), ClassAccessor.arrayOfClass(MinecraftKey.clazz()), CustomFunction.CacheableFunction.clazz()),
                this.experience, lootKeys.toArray(), recipeKeys.toArray(), this.function != null ? this.function.createOriginal() : null
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getAdvancementRewardsClass();
    }

}
