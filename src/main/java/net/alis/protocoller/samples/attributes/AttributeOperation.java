package net.alis.protocoller.samples.attributes;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum AttributeOperation {
    ADDITION(0),
    MULTIPLY_BASE(1),
    MULTIPLY_TOTAL(2);

    private final int id;

    AttributeOperation(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Contract(pure = true)
    public static @Nullable AttributeOperation getById(int id) {
        Utils.checkClassSupportability(clazz(), "AttributeOperation", false);
        for(AttributeOperation operation : values()) {
            if ((operation.id == id)) return operation;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), "AttributeOperation", false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getAttributeOperationEnum();
    }

}
