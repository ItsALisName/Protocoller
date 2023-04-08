package net.alis.protocoller.samples.attributes;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

public class AttributeBase implements ObjectSample {
    protected double fallback;
    protected boolean tracked;
    protected String translationKey;

    protected AttributeBase() {}

    public AttributeBase(String translationKey, double fallback) {
        this.fallback = fallback;
        this.translationKey = translationKey;
    }

    public AttributeBase(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        this.fallback = accessor.read(0, double.class);
        this.tracked = accessor.read(0, boolean.class);
        this.translationKey = accessor.read(0, String.class);
    }

    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public void setFallback(double fallback) {
        this.fallback = fallback;
    }

    public double getFallback() {
        return this.fallback;
    }

    public boolean isTracked() {
        return this.tracked;
    }

    public AttributeBase setTracked(boolean tracked) {
        this.tracked = tracked;
        return this;
    }

    public double getValue(double value) {
        return value;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassesContainer.get().getAttributeBaseClass(), String.class, double.class),
                this.translationKey, this.fallback
        );
    }
}

