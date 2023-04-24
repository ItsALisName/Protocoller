package net.alis.protocoller.samples.boss;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum BarStyle {
    PROGRESS(0, "progress"),
    NOTCHED_6(1, "notched_6"),
    NOTCHED_10(2, "notched_10"),
    NOTCHED_12(3, "notched_12"),
    NOTCHED_20(4, "notched_20");

    private final int id;
    private final String name;

    BarStyle(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    @Contract(pure = true)
    public static @Nullable BarStyle getById(int id) {
        Utils.checkClassSupportability(clazz(), "BarStyle", false);
        for(BarStyle style : values()) {
            if(style.id == id) return style;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), "BarStyle", false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getBarStyleEnum();
    }
}
