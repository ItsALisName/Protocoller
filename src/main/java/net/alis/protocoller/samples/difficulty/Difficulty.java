package net.alis.protocoller.samples.difficulty;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import org.jetbrains.annotations.Nullable;

public enum Difficulty {
    PEACEFUL(0, "peaceful"),
    EASY(1, "easy"),
    NORMAL(2, "normal"),
    HARD(3, "hard");

    private final int id;
    private final String name;

    Difficulty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public ChatComponent component() {
        return new ChatComponent("options.difficulty." + this.name);
    }

    public static Difficulty getById(int id) {
        for (Difficulty difficulty : values()) {
            if (difficulty.id == id)
                return difficulty;
        }
        return null;
    }

    @Nullable
    public static Difficulty getByName(String name) {
        for (Difficulty difficulty : values()) {
            if (difficulty.name.equals(name))
                return difficulty;
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getDifficultyEnum(), this.id);
    }
}
