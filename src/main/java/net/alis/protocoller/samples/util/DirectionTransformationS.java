package net.alis.protocoller.samples.util;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;

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
        Utils.checkClassSupportability(clazz(), "DirectionTransformationS", false);
        for(DirectionTransformationS s : values()) {
            if(s.ordinal() == id) return s;
        }
        return null;
    }

    public Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.ordinal());
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getPointSEnum();
    }
}
