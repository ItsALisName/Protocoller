package net.alis.protocoller.bukkit.exceptions;

public class MissingPacketTypeException extends RuntimeException {

    public MissingPacketTypeException(Class<?> packetClass) {
        super("Unable to determine packet type from object: " + packetClass.getSimpleName() + " (Class: " + packetClass.getName() + ")");
    }

}
