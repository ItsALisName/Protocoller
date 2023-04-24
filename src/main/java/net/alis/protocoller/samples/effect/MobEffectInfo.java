package net.alis.protocoller.samples.effect;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatFormat;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum MobEffectInfo {
    BENEFICIAL(0, ChatFormat.BLUE),
    HARMFUL(1, ChatFormat.RED),
    NEUTRAL(2, ChatFormat.BLUE);

    private final int id;
    private final ChatFormat format;

    MobEffectInfo(int id, ChatFormat format) {
        this.id = id;
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public ChatFormat getFormat() {
        return this.format;
    }

    @Contract(pure = true)
    public static @Nullable MobEffectInfo getById(int id) {
        Utils.checkClassSupportability(clazz(), "MobEffectInfo", false);
        for(MobEffectInfo info : values()) {
            if(info.id == id) return info;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), "MobEffectInfo", false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getMobEffectInfoEnum();
    }
}

