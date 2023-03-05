package net.alis.protocoller.bukkit.network.packet;

import net.alis.protocoller.bukkit.data.PacketBuilders;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.util.ObjectAccessor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.Objects;

public class PacketBuilder {

    private final MinecraftPacketType type;
    private final ConstructorLevelIndicator indicator;
    private final Constructor<?> constructor;
    private final Version version;

    public PacketBuilder(MinecraftPacketType type, ConstructorLevelIndicator indicator, Version latest) {
        this.type = type;
        this.indicator = indicator;
        this.version = latest;
        this.constructor = Reflection.getConstructor(type.getPacketClass(), indicator.getTypes());
    }

    public Object buildPacket(@Nullable IndexedParam<?, ?>[] parameters, @Nullable Object... objects) {
        if(this.indicator.getLevel() > 0) {
            return Reflection.callConstructor(this.constructor, objects);
        } else {
            ObjectAccessor accessor = new ObjectAccessor(Reflection.classNewInstance(this.type.getPacketClass()));
            for(IndexedParam<?, ?> param : parameters) {
                accessor.write(param.getIndex(), param.getObject());
            }
            return accessor.getObject();
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

    public static PacketBuilder get(MinecraftPacketType type) {
        for(PacketBuilder converter : PacketBuilders.INSTANCE.getPacketCreators()) {
            if(converter.getType().getPacketId() == type.getPacketId()) {
                return converter;
            }
        }
        throw new RuntimeException("Failed to find PacketBuilder for packet: '" + type.getPacketName() + "'!");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PacketBuilder that = (PacketBuilder) o;
        return type.getPacketName().equalsIgnoreCase(that.type.getPacketName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, indicator, constructor);
    }
}
