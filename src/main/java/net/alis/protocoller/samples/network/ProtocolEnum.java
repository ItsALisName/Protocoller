package net.alis.protocoller.samples.network;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum ProtocolEnum {

    HANDSHAKING((byte) -1),
    PLAY((byte) 0),
    STATUS((byte) 1),
    LOGIN((byte) 2);

    private final byte id;

    ProtocolEnum(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public static @Nullable ProtocolEnum get(int ordinal) {
        Utils.checkClassSupportability(clazz(), "ProtocolEnum", false);
        for(ProtocolEnum e : ProtocolEnum.values()) {
            if(e.ordinal() == ordinal) return e;
        }
        return null;
    }

    public static @NotNull Enum<?> original(@NotNull ProtocolEnum protocolEnum) {
        Utils.checkClassSupportability(clazz(), "ProtocolEnum", false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), protocolEnum.ordinal());
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getProtocolEnum();
    }
}
