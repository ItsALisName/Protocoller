package net.alis.protocoller.samples.effect;

import com.google.common.collect.Maps;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.attributes.AttributeBase;
import net.alis.protocoller.samples.attributes.AttributeModifier;
import net.alis.protocoller.samples.attributes.AttributeOperation;
import net.alis.protocoller.util.ObjectSample;
import net.alis.protocoller.util.AccessedObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobEffectList implements ObjectSample {
    protected Map<AttributeBase, AttributeModifier> attributeModifierMap = Maps.newHashMap();
    protected MobEffectInfo category;
    protected int color;

    protected MobEffectList() { }

    public MobEffectList(MobEffectInfo category, int color) {
        this.category = category;
        this.color = color;
    }

    public MobEffectList(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        Map<Object, Object> attMap = accessor.read(0, Map.class);
        for(Map.Entry<Object, Object> en : attMap.entrySet()) {
            this.attributeModifierMap.put(new AttributeBase(en.getKey()), new AttributeModifier(en.getValue()));
        }
        this.category = MobEffectInfo.getById(((Enum<?>)accessor.read(0, ClassAccessor.get().getMobEffectInfoEnum())).ordinal());
        this.color = accessor.read(0, int.class);
    }

    public MobEffectInfo getCategory() {
        return this.category;
    }

    public int getColor() {
        return this.color;
    }

    public void setAttributeMap(Map<AttributeBase, AttributeModifier> attributeModifierMap) {
        this.attributeModifierMap = attributeModifierMap;
    }

    public void setCategory(MobEffectInfo category) {
        this.category = category;
    }

    public MobEffectList addAttribute(AttributeBase attribute, String uuid, double amount, AttributeOperation operation, String name) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), name, amount, operation);
        this.attributeModifierMap.put(attribute, attributemodifier);
        return this;
    }

    public Map<AttributeBase, AttributeModifier> getAttributeMap() {
        return this.attributeModifierMap;
    }

    public double enhance(int amplifier, AttributeModifier modifier) {
        return modifier.getValue() * (double)(amplifier + 1);
    }

    public boolean isBeneficial() {
        return this.category == MobEffectInfo.BENEFICIAL;
    }

    @Override
    public Object createOriginal() {
        Object original = Reflect.callConstructor(Reflect.getConstructor(ClassAccessor.get().getMobEffectListClass(), ClassAccessor.get().getMobEffectInfoEnum(), int.class), this.category.original(), this.color);
        if(this.attributeModifierMap.size() > 0) {
            Map<Object, Object> atrMap = new HashMap<>();
            for(Map.Entry<AttributeBase, AttributeModifier> e : this.attributeModifierMap.entrySet()) {
                atrMap.put(e.getKey().createOriginal(), e.getValue().createOriginal());
            }
            Reflect.writeField(original, Reflect.getField(original.getClass(), 0, Map.class), atrMap, false);
        }
        return original;
    }
}

