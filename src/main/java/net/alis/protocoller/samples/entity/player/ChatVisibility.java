package net.alis.protocoller.samples.entity.player;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;

public enum ChatVisibility {
    FULL(0, "options.chat.visibility.full"),
    SYSTEM(1, "options.chat.visibility.system"),
    HIDDEN(2, "options.chat.visibility.hidden");

    private final int id;

    private final String optionName;

    ChatVisibility(int id, String key) {
        this.id = id;
        this.optionName = key;
    }

    public int getId() {
        return this.id;
    }

    public String getOptionName() {
        return optionName;
    }

    public static ChatVisibility getById(int id) {
        for(ChatVisibility chatVisibility : values()) {
            if(chatVisibility.id == id) return chatVisibility;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getChatVisibilityEnum(), this.id);
    }
}

