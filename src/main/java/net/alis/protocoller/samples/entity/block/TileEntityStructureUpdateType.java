package net.alis.protocoller.samples.entity.block;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum TileEntityStructureUpdateType {

    UPDATE_DATA(0),
    SAVE_AREA(1),
    LOAD_AREA(2),
    SCAN_AREA(3);

    private final int id;

    TileEntityStructureUpdateType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Contract(pure = true)
    public static @Nullable TileEntityStructureUpdateType getById(int id) {
        Utils.checkClassSupportability(clazz(), "TileEntityStructureUpdateType", false);
        for(TileEntityStructureUpdateType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getTileEntityStructureUpdateType();
    }
}
