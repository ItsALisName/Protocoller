package net.alis.protocoller.samples.sounds;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum SoundCategory {
    MASTER("master", 0),
    MUSIC("music", 1),
    RECORD("record", 2),
    WEATHER("weather", 3),
    BLOCK("block", 4),
    HOSTILE("hostile", 5),
    NEUTRAL("neutral", 6),
    PLAYER("player", 7),
    AMBIENT("ambient", 8),
    VOICE("voice", 9);

    private final String name;
    private final int id;

    SoundCategory(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    @Contract(pure = true)
    public static @Nullable SoundCategory getById(int id) {
        Utils.checkClassSupportability(clazz(), "SoundCategory", false);
        for(SoundCategory s : SoundCategory.values())
            if(s.id == id) return s;
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getSoundCategoryEnum();
    }

}

