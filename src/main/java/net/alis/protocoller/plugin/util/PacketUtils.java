package net.alis.protocoller.plugin.util;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.plugin.data.InitialData;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

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
                        InitialData.get().getPacketsPackage() + ".game." + packetName,
                        InitialData.get().getPacketsPackage() + "." + packetName,
                        true
                );
            case LOGIN_SERVERBOUND:
            case LOGIN_CLIENTBOUND:
                return BaseReflection.getClassOr(
                        InitialData.get().getPacketsPackage() + ".login." + packetName,
                        InitialData.get().getPacketsPackage() + "." + packetName,
                        true
                );
            case HANDSHAKE_CLIENTBOUND:
            case HANDSHAKE_SERVERBOUND:
                return BaseReflection.getClassOr(
                        InitialData.get().getPacketsPackage() + ".handshake." + packetName,
                        InitialData.get().getPacketsPackage() + "." + packetName,
                        true
                );
            case STATUS_CLIENTBOUND:
            case STATUS_SERVERBOUND:
                return BaseReflection.getClassOr(
                        InitialData.get().getPacketsPackage() + ".status." + packetName,
                        InitialData.get().getPacketsPackage() + "." + packetName,
                        true
                );
            default: return null;
        }
    }

    public static @NotNull String buildPacketDataReport(@NotNull PacketDataContainer data) {
        StringBuilder report = new StringBuilder("\n[*] Processed packet details:");
        report.append("\nPacket name: ").append(data.getType().getPacketName());
        report.append("\nPacket id: ").append(data.getType().getPacketId());
        report.append("\nPacket state: ").append(data.getType().getState().name());
        report.append("\nPacket class: ").append(data.getType().getPacketClass().getName());
        report.append("\nPacket data:");
        for(Field field : data.getRawPacket().getClass().getDeclaredFields()) {
            field.setAccessible(true);
            report.append("\n    Parameter: ").append(field.getName()).append(" <-> ").append(field.getType().getSimpleName()).append(" <-> ").append((Object) BaseReflection.readField(data.getRawPacket(), field, true));
        }
        return report.toString();
    }

    public static @NotNull String buildPacketDataReport(@NotNull Object data) {
        StringBuilder report = new StringBuilder("\n[*] Processed packet details:");
        report.append("\nPacket name: ").append(data.getClass().getSimpleName());
        report.append("\nPacket id: ").append("Unknown");
        report.append("\nPacket state: ").append("Unknown");
        report.append("\nPacket class: ").append(data.getClass().getName());
        report.append("\nPacket data:");
        for(Field field : data.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            report.append("\n    Parameter: ").append(field.getName()).append(" <-> ").append(field.getType().getSimpleName()).append(" <-> ").append((Object) BaseReflection.readField(data, field, true));
        }
        return report.toString();
    }

}
