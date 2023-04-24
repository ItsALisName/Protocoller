package net.alis.protocoller.samples.boss;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatFormat;

public enum BarColor {
    PINK(0, "pink", ChatFormat.RED),
    BLUE(1, "blue", ChatFormat.BLUE),
    RED(2, "red", ChatFormat.DARK_RED),
    GREEN(3, "green", ChatFormat.GREEN),
    YELLOW(4, "yellow", ChatFormat.YELLOW),
    PURPLE(5, "purple", ChatFormat.DARK_BLUE),
    WHITE(6, "white", ChatFormat.WHITE);

    private final int id;
    private final String name;
    private final ChatFormat format;

    BarColor(int id, String name, ChatFormat format) {
        this.id = id;
        this.name = name;
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public ChatFormat getFormat() {
        return this.format;
    }

    public String getName() {
        return this.name;
    }

    public static BarColor getById(int id) {
        Utils.checkClassSupportability(clazz(), "BarColor", false);
        for(BarColor color : values()) {
            if(color.id == id) return color;
        }
        return null;
    }

    public Enum<?> original() {
        Utils.checkClassSupportability(clazz(), "BarColor", false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getBarColorEnum();
    }
}
