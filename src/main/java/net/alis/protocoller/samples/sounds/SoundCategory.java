package net.alis.protocoller.samples.sounds;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
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

    private SoundCategory(String name, int id) {
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
        for(SoundCategory s : SoundCategory.values())
            if(s.id == id) return s;
        return null;
    }

    public @NotNull Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getSoundCategoryEnum(), this.id);
    }

}

