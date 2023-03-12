package net.alis.protocoller.samples.attributes;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

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
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getAttributeOperationEnum(), this.id);
    }

}
