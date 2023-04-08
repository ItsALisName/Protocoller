package net.alis.protocoller.samples.effect;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatFormat;

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

    public static MobEffectInfo getById(int id) {
        for(MobEffectInfo info : values()) {
            if(info.id == id) return info;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getMobEffectInfoEnum(), this.id);
    }
}

