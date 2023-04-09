package net.alis.protocoller.samples.entity.block;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;

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

    public static TileEntityCommandType getById(int id) {
        for(TileEntityCommandType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getTileEntityCommandTypeEnum(), this.id);
    }
}
