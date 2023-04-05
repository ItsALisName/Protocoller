package net.alis.protocoller.samples.util;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;

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
        return BaseReflection.readEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getPointSEnum(), this.ordinal());
    }
}
