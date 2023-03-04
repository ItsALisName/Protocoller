package net.alis.protocoller.parent.entity.block;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

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
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getTileEntityCommandTypeEnum(), this.id);
    }
}
