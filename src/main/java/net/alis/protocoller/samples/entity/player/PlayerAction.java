package net.alis.protocoller.samples.entity.player;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;

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

    public static PlayerAction getById(int id) {
        for(PlayerAction action : values()) {
            if(action.id == id) return action;
        }
        return null;
    }

    public Enum<?> original() {
        return BaseReflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getPlayerActionEnum(), this.id);
    }

}
