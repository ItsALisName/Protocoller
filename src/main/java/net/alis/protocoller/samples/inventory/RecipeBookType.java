package net.alis.protocoller.samples.inventory;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;

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

    public static RecipeBookType getById(int id) {
        for(RecipeBookType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getRecipeBookTypeEnum(), this.id);
    }
}
