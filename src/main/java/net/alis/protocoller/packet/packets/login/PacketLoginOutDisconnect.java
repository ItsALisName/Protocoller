package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.network.chat.ChatComponent;
import net.alis.protocoller.parent.network.chat.ChatSerializer;

public class PacketLoginOutDisconnect implements Packet {

    private final PacketDataContainer packetData;
    private ChatComponent reason;

    public PacketLoginOutDisconnect(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.reason = new ChatComponent(ChatSerializer.fromComponent(packetData.readIChatBaseComponent(0)));
    }

    public PacketLoginOutDisconnect(ChatComponent reason) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, reason.asIChatBaseComponent()));
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
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
