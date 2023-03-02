package net.alis.protocoller.bukkit.util.reflection;

import net.alis.protocoller.bukkit.data.InitialData;
import net.alis.protocoller.packet.PacketType;
import org.jetbrains.annotations.Nullable;

public class NetworkReflection {

    @Nullable
    public static Class<?> packet$getPacketClass(String packetName, PacketType.State packetState) {
        switch (packetState) {
            case PLAY_OUT:
            case PLAY_OUT_CLIENTBOUND:
            case PLAY_IN:
                return Reflection.getClassOr(
                    InitialData.INSTANCE.getPacketsPackage() + ".game." + packetName,
                    InitialData.INSTANCE.getPacketsPackage() + "." + packetName
            );
            case LOGIN_OUT:
            case LOGIN_IN:
                return Reflection.getClassOr(
                    InitialData.INSTANCE.getPacketsPackage() + ".login." + packetName,
                    InitialData.INSTANCE.getPacketsPackage() + "." + packetName
            );
            case HANDSHAKE_IN:
            case HANDSHAKE_OUT:
                return Reflection.getClassOr(
                    InitialData.INSTANCE.getPacketsPackage() + ".handshake." + packetName,
                    InitialData.INSTANCE.getPacketsPackage() + "." + packetName
            );
            case STATUS_IN:
            case STATUS_OUT:
                return Reflection.getClassOr(
                    InitialData.INSTANCE.getPacketsPackage() + ".status." + packetName,
                    InitialData.INSTANCE.getPacketsPackage() + "." + packetName
            );
            default: return null;
        }
    }

}
