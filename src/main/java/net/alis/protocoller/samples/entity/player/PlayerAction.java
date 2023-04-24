package net.alis.protocoller.samples.entity.player;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum PlayerAction {

    PRESS_SHIFT_KEY(0),
    RELEASE_SHIFT_KEY(1),
    STOP_SLEEPING(2),
    START_SPRINTING(3),
    STOP_SPRINTING(4),
    START_RIDING_JUMP(5),
    STOP_RIDING_JUMP(6),
    OPEN_INVENTORY(7),
    START_FALL_FLYING(8);

    private final int id;

    PlayerAction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Contract(pure = true)
    public static @Nullable PlayerAction getById(int id) {
        Utils.checkClassSupportability(clazz(), "PlayerAction", false);
        for(PlayerAction action : values()) {
            if(action.id == id) return action;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getPlayerActionEnum(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getPlayerActionEnum();
    }

}
