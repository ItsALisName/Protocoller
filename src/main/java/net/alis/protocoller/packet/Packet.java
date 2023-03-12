package net.alis.protocoller.packet;

public interface Packet {

    MinecraftPacketType getPacketType();

    PacketDataContainer getData();

    Object getRawPacket();

}
