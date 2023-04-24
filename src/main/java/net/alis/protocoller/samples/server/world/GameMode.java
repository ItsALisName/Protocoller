package net.alis.protocoller.samples.server.world;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum GameMode {

    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    @Nullable
    public static GameMode getById(int id) {
        for(GameMode mode : values()) if(mode.ordinal() == id) return mode;
        return null;
    }

    public @NotNull Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.ordinal());
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getGamemodeEnum();
    }

}
