package net.alis.protocoller.samples.entity.block;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum TileEntityJigsawJointType {

    ROLLABLE(0, "rollable"),
    ALIGNED(1, "aligned");

    private final int id;
    private final String name;

    TileEntityJigsawJointType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public static @Nullable TileEntityJigsawJointType getById(int id) {
        Utils.checkClassSupportability(clazz(), "TileEntityJigsawJointType", false);
        for(TileEntityJigsawJointType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getTileEntityJigsawJointypeEnum();
    }
}
