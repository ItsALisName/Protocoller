package net.alis.protocoller.samples.entity.player;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Contract(pure = true)
    public static @Nullable ChatVisibility getById(int id) {
        Utils.checkClassSupportability(clazz(), "ChatVisibility", false);
        for(ChatVisibility chatVisibility : values()) {
            if(chatVisibility.id == id) return chatVisibility;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getChatVisibilityEnum();
    }
}

