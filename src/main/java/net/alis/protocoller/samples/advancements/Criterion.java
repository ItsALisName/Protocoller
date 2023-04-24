package net.alis.protocoller.samples.advancements;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;

public class Criterion implements ObjectSample {

    @Nullable
    private Object trigger; // Soon

    public Criterion(Object triggerOrOriginal) {
        if(triggerOrOriginal.getClass().equals(clazz())) {
            this.trigger = new AccessedObject(triggerOrOriginal).readField(0, ClassAccessor.get().getCriterionInstanceClass());
            return;
        }
        if(!triggerOrOriginal.getClass().equals(ClassAccessor.get().getCriterionInstanceClass())) {
            ExceptionBuilder.throwException(new UnsupportedOperationException("Class \"" + this.getClass().getName() + "\" can be created only with \"" + ClassAccessor.get().getCriterionInstanceClass().getName() + "\""), true);
        }
        this.trigger = triggerOrOriginal;
    }

    public Criterion() {
        this.trigger = null;
    }

    @Nullable
    public Object getTrigger() {
        return trigger;
    }

    public void setTrigger(@Nullable Object trigger) {
        this.trigger = trigger;
    }

    @Override
    public Object createOriginal() {
        if(this.trigger == null) {
            return Reflect.callConstructor(Reflect.getConstructor(clazz(), false));
        } else {
            return Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, ClassAccessor.get().getCriterionInstanceClass()),
                    this.trigger
            );
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getCriterionClass();
    }
}
