package net.alis.protocoller.bukkit.network.packet;

import net.alis.protocoller.bukkit.data.PacketCreators;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.util.ObjectAccessor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.Objects;

public class PacketCreator {

    private final MinecraftPacketType type;
    private final ConstructorLevelIndicator indicator;
    private final Constructor<?> constructor;
    private final Version version;

    public PacketCreator(MinecraftPacketType type, ConstructorLevelIndicator indicator, Version latest) {
        this.type = type;
        this.indicator = indicator;
        this.version = latest;
        this.constructor = Reflection.getConstructor(type.getPacketClass(), indicator.getTypes());
    }

    public Object create(@Nullable IndexedParam<?, ?>[] parameters, @Nullable Object... objects) {
        if(this.indicator.getLevel() != 0) {
            return Reflection.callConstructor(this.constructor, objects);
        } else {
            ObjectAccessor accessor = new ObjectAccessor(Reflection.classNewInstance(this.type.getPacketClass()));
            for(IndexedParam<?, ?> param : parameters) {
                accessor.write(param.getIndex(), param.getObject());
            }
            return accessor;
        }
    }

    public Version getVersion() {
        return version;
    }

    public ConstructorLevelIndicator getConstructorIndicator() {
        return indicator;
    }

    public MinecraftPacketType getType() {
        return type;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public static PacketCreator get(MinecraftPacketType type) {
        for(PacketCreator converter : PacketCreators.INSTANCE.getPacketCreators()) {
            if(converter.getType().getPacketId() == type.getPacketId()) {
                return converter;
            }
        }
        throw new RuntimeException("Failed to find PacketCreator for packet: '" + type.getPacketName() + "'!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacketCreator that = (PacketCreator) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, indicator, constructor);
    }
}
