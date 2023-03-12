package net.alis.protocoller.samples.effect;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.ChatFormat;

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
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getMobEffectInfoEnum(), this.id);
    }
}

