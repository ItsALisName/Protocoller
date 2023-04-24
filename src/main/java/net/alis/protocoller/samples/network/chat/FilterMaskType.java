package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum FilterMaskType {

    PASS_THROUGH(0, "pass_through"),
    FULLY_FILTERED(1, "fully_filtered"),
    PARTIALLY_FILTERED(2, "partially_filtered");

    private final int id;
    private final String serializedName;

    FilterMaskType(int id, String serializedName) {
        this.id = id;
        this.serializedName = serializedName;
    }

    public int getId() {
        return id;
    }

    public String getSerializedName() {
        return serializedName;
    }

    @Contract(pure = true)
    public static @Nullable FilterMaskType getById(int id) {
        Utils.checkClassSupportability(clazz(), "FilterMaskType", false);
        for(FilterMaskType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.id);
    }
    
    public static Class<?> clazz() {
        return ClassAccessor.get().getFilterMaskTypeEnum();
    }

}
