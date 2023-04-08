package net.alis.protocoller.samples.attributes;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;

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

    public static AttributeOperation getById(int id) {
        for(AttributeOperation operation : values()) {
            if ((operation.id == id)) return operation;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getAttributeOperationEnum(), this.id);
    }

}
