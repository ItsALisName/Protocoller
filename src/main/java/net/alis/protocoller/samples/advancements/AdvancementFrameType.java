package net.alis.protocoller.samples.advancements;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Nullable;

public enum AdvancementFrameType {

    TASK,
    CHALLENGE,
    GOAL;

    public @Nullable static AdvancementFrameType getById(int id) {
        for(AdvancementFrameType frameType : values()) if(frameType.ordinal() == id) return frameType;
        return null;
    }

    public Enum<?> original() {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.ordinal());
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getAdvancementFrameTypeEnum();
    }

}
