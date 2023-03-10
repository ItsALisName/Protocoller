package net.alis.protocoller.samples.boss;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

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

    public static BarStyle getById(int id) {
        for(BarStyle style : values()) {
            if(style.id == id) return style;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getBarStyleEnum(), this.id);
    }
}
