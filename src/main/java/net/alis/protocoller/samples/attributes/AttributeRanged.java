package net.alis.protocoller.samples.attributes;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.util.MathHelper;
import net.alis.protocoller.util.AccessedObject;

public class AttributeRanged extends AttributeBase {
    private final double min;
    public double max;

    public AttributeRanged(String translationKey, double fallback, double min, double max) {
        super(translationKey, fallback);
        this.min = min;
        this.max = max;
        if (min > max) {
            new ExceptionBuilder().getAttributeExceptions().customMessage("Minimum value cannot be bigger than maximum value!").throwException();
        } else if (fallback < min) {
            new ExceptionBuilder().getAttributeExceptions().customMessage("Default value cannot be lower than minimum value!").throwException();
        } else if (fallback > max) {
            new ExceptionBuilder().getAttributeExceptions().customMessage("Default value cannot be bigger than maximum value!").throwException();
        }
    }

    public AttributeRanged(AttributeBase parent, double min, double max) {
        this(parent.getTranslationKey(), parent.getFallback(), min, max);
    }

    public AttributeRanged(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        this.min = accessor.readField(0, double.class);
        this.max = accessor.readField(1, double.class);
        this.fallback = accessor.readSuperclassField(0, double.class);
        this.tracked = accessor.readSuperclassField(0, boolean.class);
        this.translationKey = accessor.readSuperclassField(0, String.class);
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public double getValue(double value) {
        return Double.isNaN(value) ? this.min : MathHelper.clamp(value, this.min, this.max);
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, String.class, double.class, double.class, double.class),
                this.getTranslationKey(), this.getFallback(), this.min, this.max
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getAttributeRangedClass();
    }
}

