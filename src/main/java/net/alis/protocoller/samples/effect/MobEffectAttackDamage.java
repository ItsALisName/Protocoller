package net.alis.protocoller.samples.effect;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.attributes.AttributeBase;
import net.alis.protocoller.samples.attributes.AttributeModifier;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MobEffectAttackDamage extends MobEffectList {
    protected final double modifier;

    public MobEffectAttackDamage(MobEffectInfo category, int color, double modifier) {
        super(category, color);
        this.modifier = modifier;
    }

    public MobEffectAttackDamage(@NotNull MobEffectList parent, double modifier) {
        this(parent.getCategory(), parent.getColor(), modifier);
    }

    public MobEffectAttackDamage(Object original) {
        Utils.checkClassSupportability(clazz(), "MobEffectAttackDamage", false);
        AccessedObject accessor = new AccessedObject(original);
        this.modifier = accessor.readField(0, double.class);
        Map<Object, Object> attMap = accessor.readSuperclassField(0, Map.class);
        for(Map.Entry<Object, Object> en : attMap.entrySet()) {
            this.attributeModifierMap.put(new AttributeBase(en.getKey()), new AttributeModifier(en.getValue()));
        }
        this.category = MobEffectInfo.getById(((Enum<?>)accessor.readSuperclassField(0, MobEffectInfo.clazz())).ordinal());
        this.color = accessor.readSuperclassField(0, int.class);
    }

    public double getModifier() {
        return modifier;
    }

    @Override
    public double enhance(int amplifier, AttributeModifier modifier) {
        return this.modifier * (double)(amplifier + 1);
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, MobEffectInfo.clazz(), int.class, double.class),
                this.getCategory().original(), this.getColor(), this.modifier
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getMobEffectAttackDamageClass();
    }

}
