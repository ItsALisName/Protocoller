package net.alis.protocoller.plugin.util;

import net.alis.protocoller.packet.MinecraftPacketType;
import org.jetbrains.annotations.NotNull;

public class PacketUtils {

    public static void checkPacketCompatibility(@NotNull MinecraftPacketType input, @NotNull MinecraftPacketType required) {
        if (input.getPacketId() != required.getPacketId()) {
            throw new UnsupportedOperationException("Packet \"" + required.getPacketName() + "\" cannot be cast to \"" + input.getPacketName() + "\"!");
        }
    }

}
