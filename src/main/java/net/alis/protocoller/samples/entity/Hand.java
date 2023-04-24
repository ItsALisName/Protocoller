package net.alis.protocoller.samples.entity;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Hand {
    MAIN_HAND(0), OFF_HAND(1);

    private final int id;

    Hand(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Contract(pure = true)
    public static @Nullable Hand getById(int id) {
        Utils.checkClassSupportability(clazz(), "Hand", false);
        for(Hand s : Hand.values())
            if(s.id == id) return s;
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getHandEnum();
    }
}
