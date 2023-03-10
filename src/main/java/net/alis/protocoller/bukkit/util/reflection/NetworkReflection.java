package net.alis.protocoller.bukkit.util.reflection;

import net.alis.protocoller.bukkit.data.InitialData;
import net.alis.protocoller.packet.PacketType;
import org.jetbrains.annotations.Nullable;

public class NetworkReflection {

    @Nullable
    public static Class<?> packet$getPacketClass(String packetName, PacketType.State packetState) {
        switch (packetState) {
            case PLAY_SERVERBOUND:
            case CLIENTBOUND:
            case PLAY_CLIENTBOUND:
                return Reflection.getClassOr(
                    InitialData.INSTANCE.getPacketsPackage() + ".game." + packetName,
                    InitialData.INSTANCE.getPacketsPackage() + "." + packetName
            );
            case LOGIN_SERVERBOUND:
            case LOGIN_CLIENTBOUND:
                return Reflection.getClassOr(
                    InitialData.INSTANCE.getPacketsPackage() + ".login." + packetName,
                    InitialData.INSTANCE.getPacketsPackage() + "." + packetName
            );
            case HANDSHAKE_CLIENTBOUND:
            case HANDSHAKE_SERVERBOUND:
                return Reflection.getClassOr(
                    InitialData.INSTANCE.getPacketsPackage() + ".handshake." + packetName,
                    InitialData.INSTANCE.getPacketsPackage() + "." + packetName
            );
            case STATUS_CLIENTBOUND:
            case STATUS_SERVERBOUND:
                return Reflection.getClassOr(
                    InitialData.INSTANCE.getPacketsPackage() + ".status." + packetName,
                    InitialData.INSTANCE.getPacketsPackage() + "." + packetName
            );
            default: return null;
        }
    }

}
