package net.alis.protocoller.samples.entity.block;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum TileEntityCommandType {
    SEQUENCE(0),
    AUTO(1),
    REDSTONE(2);

    private final int id;

    TileEntityCommandType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Contract(pure = true)
    public static @Nullable TileEntityCommandType getById(int id) {
        Utils.checkClassSupportability(clazz(), "TileEntityCommandType", false);
        for(TileEntityCommandType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getTileEntityCommandTypeEnum();
    }
}
