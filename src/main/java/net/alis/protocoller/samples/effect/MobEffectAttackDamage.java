package net.alis.protocoller.samples.effect;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.attributes.AttributeBase;
import net.alis.protocoller.samples.attributes.AttributeModifier;
import net.alis.protocoller.util.ObjectAccessor;

import java.util.Map;

public class MobEffectAttackDamage extends MobEffectList {
    protected final double modifier;

    public MobEffectAttackDamage(MobEffectInfo category, int color, double modifier) {
        super(category, color);
        this.modifier = modifier;
    }

    public MobEffectAttackDamage(MobEffectList parent, double modifier) {
        this(parent.getCategory(), parent.getColor(), modifier);
    }

    public MobEffectAttackDamage(Object original) {
        ObjectAccessor accessor = new ObjectAccessor(original);
        this.modifier = accessor.read(0, double.class);
        Map<Object, Object> attMap = accessor.readSuperclass(0, Map.class);
        for(Map.Entry<Object, Object> en : attMap.entrySet()) {
            this.attributeModifierMap.put(new AttributeBase(en.getKey()), new AttributeModifier(en.getValue()));
        }
        this.category = MobEffectInfo.getById(((Enum<?>)accessor.readSuperclass(0, ClassesContainer.INSTANCE.getMobEffectInfoEnum())).ordinal());
        this.color = accessor.readSuperclass(0, int.class);
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
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getMobEffectAttackDamageClass(), ClassesContainer.INSTANCE.getMobEffectInfoEnum(), int.class, double.class),
                this.getCategory().original(), this.getColor(), this.modifier
        );
    }
}
