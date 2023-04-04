package net.alis.protocoller.plugin.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;

public class FastUtilLegacyAdapter {
    public static Int2ObjectMap<Object> newInt2ObjectMap() {
        return new Int2ObjectOpenHashMap<>();
    }

    public static Int2ObjectMap<?> newInt2ObjectMap(Object[] resources) {
        Int2ObjectMap<Object> map = newInt2ObjectMap();
        for(int i = 0; i < resources.length; i++) map.put(i, resources[i]);
        return map;
    }

    public static class Classes {
        public static Class<?> Int2ObjectMap;


        public static void init() {
            Int2ObjectMap  = BaseReflection.getClass("it.unimi.dsi.fastutil.ints.Int2ObjectMap");
        }
    }

}
