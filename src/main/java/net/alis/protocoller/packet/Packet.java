package net.alis.protocoller.packet;

public interface Packet {

    MinecraftPacketType getPacketType();

    PacketDataSerializer getPacketData();

    Object getRawPacket();

}
