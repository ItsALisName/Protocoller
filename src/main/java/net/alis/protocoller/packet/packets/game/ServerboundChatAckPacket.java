package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class ServerboundChatAckPacket implements Packet {

    private final PacketDataSerializer packetData;
    private int decodedInt$0;

    public ServerboundChatAckPacket(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.decodedInt$0 = packetData.readInt(0);
    }

    public ServerboundChatAckPacket(int decodedInt$0) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, decodedInt$0));
        this.decodedInt$0 = decodedInt$0;
    }

    public int getDecodedInt$0() {
        return decodedInt$0;
    }

    public void setDecodedInt$0(int decodedInt$0) {
        this.packetData.writeInt(0, decodedInt$0);
        this.decodedInt$0 = decodedInt$0;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.CHAT_ACK_PACKET;
    }

    @Override
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
