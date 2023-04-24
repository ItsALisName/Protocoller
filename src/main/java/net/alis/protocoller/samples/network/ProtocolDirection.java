package net.alis.protocoller.samples.network;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum ProtocolDirection {

    SERVERBOUND(0), CLIENTBOUND(1);

    private final int id;

    ProtocolDirection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ProtocolDirection reverse() {
        return this == CLIENTBOUND ? SERVERBOUND : CLIENTBOUND;
    }

    @Contract(pure = true)
    public static @Nullable ProtocolDirection getById(int id) {
        for(ProtocolDirection s : ProtocolDirection.values())
            if(s.id == id) return s;
        return null;
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getProtocolDirectionEnum();
    }
}
