package net.alis.protocoller.samples.entity;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.md_5.bungee.chat.ComponentSerializer;
import net.alis.protocoller.samples.network.chat.ChatComponent;

public enum MainHand {
    LEFT(0, new ChatComponent("options.mainHand.left")),
    RIGHT(1, new ChatComponent("options.mainHand.right"));

    private final int id;
    private final ChatComponent optionName;

    MainHand(int id, ChatComponent optionName) {
        this.id = id;
        this.optionName = optionName;
    }

    public MainHand reverse() {
        if (this == LEFT) return RIGHT;
        return LEFT;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return ComponentSerializer.toString(this.optionName.get());
    }

    public ChatComponent getOptionName() {
        return this.optionName;
    }

    public static MainHand getById(int id) {
        for(MainHand hand : values()) {
            if(hand.id == id) return hand;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getMainHandEnum(), this.id);
    }
}
