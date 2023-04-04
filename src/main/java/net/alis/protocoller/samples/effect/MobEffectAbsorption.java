package net.alis.protocoller.samples.effect;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.samples.attributes.AttributeBase;
import net.alis.protocoller.samples.attributes.AttributeModifier;
import net.alis.protocoller.util.AccessedObject;

import java.util.Map;

public class MobEffectAbsorption extends MobEffectList{
    public MobEffectAbsorption(MobEffectInfo category, int color) {
        super(category, color);
    }

    public MobEffectAbsorption(MobEffectList parent) {
        this(parent.getCategory(), parent.getColor());
    }

    public MobEffectAbsorption(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        Map<Object, Object> attMap = accessor.readSuperclass(0, Map.class);
        for(Map.Entry<Object, Object> en : attMap.entrySet()) {
            this.attributeModifierMap.put(new AttributeBase(en.getKey()), new AttributeModifier(en.getValue()));
        }
        this.category = MobEffectInfo.getById(((Enum<?>)accessor.readSuperclass(0, ClassesContainer.get().getMobEffectInfoEnum())).ordinal());
        this.color = accessor.readSuperclass(0, int.class);
    }

    @Override
    public Object createOriginal() {
        return BaseReflection.callConstructor(
                BaseReflection.getConstructor(ClassesContainer.get().getMobEffectAbsorptionClass(), ClassesContainer.get().getMobEffectInfoEnum(), int.class),
                this.getCategory().original(), this.getColor()
        );
    }

}
