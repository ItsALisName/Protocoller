package net.alis.protocoller.parent.network;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

public enum ProtocolEnum {

    HANDSHAKING((byte) -1),
    PLAY((byte) 0),
    STATUS((byte) 1),
    LOGIN((byte) 2);

    private final byte id;

    private ProtocolEnum(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public static ProtocolEnum get(int ordinal) {
        for(ProtocolEnum e : ProtocolEnum.values()) {
            if(e.ordinal() == ordinal) return e;
        }
        return null;
    }

    public static ProtocolEnum get(byte id) {
        for(ProtocolEnum e : ProtocolEnum.values()) {
            if(e.id == id) return e;
        }
        return null;
    }

    public static Enum<?> original(ProtocolEnum protocolEnum) {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getProtocolEnum(), protocolEnum.ordinal());
    }
}
