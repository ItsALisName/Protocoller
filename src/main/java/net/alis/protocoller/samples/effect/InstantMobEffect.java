package net.alis.protocoller.samples.effect;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.attributes.AttributeBase;
import net.alis.protocoller.samples.attributes.AttributeModifier;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class InstantMobEffect extends MobEffectList {
    public InstantMobEffect(MobEffectInfo category, int color) {
        super(category, color);
    }

    public InstantMobEffect(@NotNull MobEffectList parent) {
        this(parent.getCategory(), parent.getColor());
    }

    public InstantMobEffect(Object original) {
        Utils.checkClassSupportability(clazz(), "InstantMobEffect", false);
        AccessedObject accessor = new AccessedObject(original);
        Map<Object, Object> attMap = accessor.readSuperclassField(0, Map.class);
        for(Map.Entry<Object, Object> en : attMap.entrySet()) {
            this.attributeModifierMap.put(new AttributeBase(en.getKey()), new AttributeModifier(en.getValue()));
        }
        this.category = MobEffectInfo.getById(((Enum<?>)accessor.readSuperclassField(0, MobEffectInfo.clazz())).ordinal());
        this.color = accessor.readSuperclassField(0, int.class);
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, MobEffectInfo.clazz(), int.class),
                this.getCategory().original(), this.getColor()
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getInstantMobEffectClass();
    }
}
