package net.alis.protocoller.parent.entity.block;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

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

    public static TileEntityJigsawJointType getById(int id) {
        for(TileEntityJigsawJointType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getTileEntityJigsawJointypeEnum(), this.id);
    }
}
