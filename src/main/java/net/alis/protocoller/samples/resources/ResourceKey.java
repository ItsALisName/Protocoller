package net.alis.protocoller.samples.resources;

import com.google.common.collect.Maps;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import java.util.Collections;
import java.util.Map;

public class ResourceKey<T> implements ObjectSample {
    private static final Map<String, ResourceKey<?>> RESOURCE_KEY_MAP = Collections.synchronizedMap(Maps.newIdentityHashMap());

    private final MinecraftKey registry;

    private final MinecraftKey value;

    private static <T> ResourceKey<T> add(MinecraftKey k1, MinecraftKey k2) {
        String s = (k1 + ":" + k2).intern();
        return (ResourceKey<T>) RESOURCE_KEY_MAP.computeIfAbsent(s, var2 -> new ResourceKey(k1, k2));
    }

    public ResourceKey(MinecraftKey k1, MinecraftKey k2) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.registry = k1;
        this.value = k2;
    }

    public ResourceKey(Object original) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject accessor = new AccessedObject(original);
        this.registry = new MinecraftKey((Object) accessor.readField(0, MinecraftKey.clazz()));
        this.value = new MinecraftKey((Object) accessor.readField(1, MinecraftKey.clazz()));
    }

    public String toString() {
        return "ResourceKey[" + this.registry + " / " + this.value + ']';
    }

    public MinecraftKey getRegistry() {
        return registry;
    }

    public MinecraftKey getValue() {
        return this.value;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(Reflect.getConstructor(clazz(), false, MinecraftKey.clazz(), MinecraftKey.clazz()), this.registry.createOriginal(), this.value.createOriginal());
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getResourceKeyClass();
    }
}
