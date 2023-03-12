package net.alis.protocoller.samples.util;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

public enum DirectionTransformationS {
    P123(0, 1, 2),
    P213(1, 0, 2),
    P132(0, 2, 1),
    P231(1, 2, 0),
    P312(2, 0, 1),
    P321(2, 1, 0);

    private final int[] mappings;

    DirectionTransformationS(int xMapping, int yMapping, int zMapping) {
        this.mappings = new int[] { xMapping, yMapping, xMapping };
    }

    public int getMapping(int oldAxis) {
        return this.mappings[oldAxis];
    }

    public static DirectionTransformationS getById(int id) {
        for(DirectionTransformationS s : values()) {
            if(s.ordinal() == id) return s;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getPointSEnum(), this.ordinal());
    }
}
