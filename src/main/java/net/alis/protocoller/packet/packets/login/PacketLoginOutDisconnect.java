package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.network.chat.ChatComponent;
import net.alis.protocoller.parent.network.chat.ChatSerializer;

public class PacketLoginOutDisconnect implements Packet {

    private final PacketDataSerializer packetData;
    private ChatComponent reason;

    public PacketLoginOutDisconnect(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.reason = new ChatComponent(ChatSerializer.fromComponent(packetData.readIChatBaseComponent(0)));
    }

    public PacketLoginOutDisconnect(ChatComponent reason) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, reason.createIChatBaseComponent()));
        this.reason = reason;
    }

    public ChatComponent getReason() {
        return reason;
    }

    public void setReason(ChatComponent reason) {
        this.packetData.writeIChatBaseComponent(0, reason);
        this.reason = reason;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Login.Server.DISCONNECT;
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
