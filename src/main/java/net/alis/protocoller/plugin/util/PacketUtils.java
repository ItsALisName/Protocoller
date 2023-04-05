package net.alis.protocoller.plugin.util;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.plugin.data.InitialData;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketUtils {

    public static void checkPacketCompatibility(@NotNull MinecraftPacketType input, @NotNull MinecraftPacketType required) {
        if (input.getPacketId() != required.getPacketId()) {
            new ExceptionBuilder().getPacketExceptions().illegalPacketCast(required.getPacketClass(), input.getPacketClass()).throwException();
        }
    }

    @Nullable
    public static Class<?> packet$getPacketClass(String packetName, PacketType.@NotNull State packetState) {
        switch (packetState) {
            case PLAY_SERVERBOUND:
            case CLIENTBOUND:
            case PLAY_CLIENTBOUND:
                return BaseReflection.getClassOr(
                        InitialData.INSTANCE.getPacketsPackage() + ".game." + packetName,
                        InitialData.INSTANCE.getPacketsPackage() + "." + packetName,
                        true
                );
            case LOGIN_SERVERBOUND:
            case LOGIN_CLIENTBOUND:
                return BaseReflection.getClassOr(
                        InitialData.INSTANCE.getPacketsPackage() + ".login." + packetName,
                        InitialData.INSTANCE.getPacketsPackage() + "." + packetName,
                        true
                );
            case HANDSHAKE_CLIENTBOUND:
            case HANDSHAKE_SERVERBOUND:
                return BaseReflection.getClassOr(
                        InitialData.INSTANCE.getPacketsPackage() + ".handshake." + packetName,
                        InitialData.INSTANCE.getPacketsPackage() + "." + packetName,
                        true
                );
            case STATUS_CLIENTBOUND:
            case STATUS_SERVERBOUND:
                return BaseReflection.getClassOr(
                        InitialData.INSTANCE.getPacketsPackage() + ".status." + packetName,
                        InitialData.INSTANCE.getPacketsPackage() + "." + packetName,
                        true
                );
            default: return null;
        }
    }

}
