package net.alis.protocoller.samples.effect;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.attributes.AttributeBase;
import net.alis.protocoller.samples.attributes.AttributeModifier;
import net.alis.protocoller.util.AccessedObject;

import java.util.Map;

public class InstantMobEffect extends MobEffectList {
    public InstantMobEffect(MobEffectInfo category, int color) {
        super(category, color);
    }

    public InstantMobEffect(MobEffectList parent) {
        this(parent.getCategory(), parent.getColor());
    }

    public InstantMobEffect(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        Map<Object, Object> attMap = accessor.readSuperclass(0, Map.class);
        for(Map.Entry<Object, Object> en : attMap.entrySet()) {
            this.attributeModifierMap.put(new AttributeBase(en.getKey()), new AttributeModifier(en.getValue()));
        }
        this.category = MobEffectInfo.getById(((Enum<?>)accessor.readSuperclass(0, ClassesContainer.INSTANCE.getMobEffectInfoEnum())).ordinal());
        this.color = accessor.readSuperclass(0, int.class);
    }

    @Override
    public Object createOriginal() {
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getInstantMobEffectClass(), ClassesContainer.INSTANCE.getMobEffectInfoEnum(), int.class),
                this.getCategory().original(), this.getColor()
        );
    }
}
