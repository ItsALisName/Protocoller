package net.alis.protocoller.samples.attributes;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
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
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        } else if (fallback < min) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        } else if (fallback > max) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }

    public AttributeRanged(AttributeBase parent, double min, double max) {
        this(parent.getTranslationKey(), parent.getFallback(), min, max);
    }

    public AttributeRanged(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        this.min = accessor.read(0, double.class);
        this.max = accessor.read(1, double.class);
        this.fallback = accessor.readSuperclass(0, double.class);
        this.tracked = accessor.readSuperclass(0, boolean.class);
        this.translationKey = accessor.readSuperclass(0, String.class);
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
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getAttributeRangedClass(), String.class, double.class, double.class, double.class),
                this.getTranslationKey(), this.getFallback(), this.min, this.max
        );
    }
}

