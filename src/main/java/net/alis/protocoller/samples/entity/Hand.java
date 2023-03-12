package net.alis.protocoller.samples.entity;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
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
        for(Hand s : Hand.values())
            if(s.id == id) return s;
        return null;
    }

    public @NotNull Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getHandEnum(), this.id);
    }
}
