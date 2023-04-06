package net.alis.protocoller.plugin.network.packet;

import net.alis.protocoller.plugin.data.PacketBuilders;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.Objects;

public class PacketBuilder {

    private final MinecraftPacketType type;
    private final ConstructorLevelIndicator indicator;
    private final Constructor<?> constructor;
    private final Version version;

    public PacketBuilder(@NotNull MinecraftPacketType type, @NotNull ConstructorLevelIndicator indicator, Version latest) {
        this.type = type;
        this.indicator = indicator;
        this.version = latest;
        this.constructor = BaseReflection.getConstructor(type.getPacketClass(), true, indicator.getTypes());
    }

    public Object buildPacket(@Nullable IndexedParam<?, ?>[] parameters, @Nullable Object... objects) {
        if(this.indicator.getLevel() > 0) {
            return BaseReflection.callConstructor(this.constructor, objects);
        } else {
            AccessedObject accessor = new AccessedObject(BaseReflection.classNewInstance(this.type.getPacketClass()));
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

    public static @NotNull PacketBuilder get(MinecraftPacketType type) {
        for(PacketBuilder converter : PacketBuilders.get().getPacketCreators()) {
            if(converter.getType().getPacketId() == type.getPacketId()) {
                return converter;
            }
        }
        return new ExceptionBuilder().getPacketExceptions().missingPacketBuilder(type).throwException();
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
