package net.alis.protocoller.packet;

public interface MinecraftPacketType {

    String getPacketName();

    PacketType.State getState();

    byte getPacketId();

    Class<?> getPacketClass();

}
