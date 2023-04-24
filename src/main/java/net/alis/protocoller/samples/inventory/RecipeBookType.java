package net.alis.protocoller.samples.inventory;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum RecipeBookType {
    CRAFTING(0),
    FURNACE(1),
    BLAST_FURNACE(2),
    SMOKER(3);

    private final int id;

    RecipeBookType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static @Nullable RecipeBookType getById(int id) {
        Utils.checkClassSupportability(clazz(), "RecipeBookType", false);
        for(RecipeBookType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getRecipeBookTypeEnum();
    }
}
