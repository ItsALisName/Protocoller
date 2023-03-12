package net.alis.protocoller.samples.entity.block;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
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
        for(TileEntityStructureUpdateType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getTileEntityStructureUpdateType(), this.id);
    }
}
