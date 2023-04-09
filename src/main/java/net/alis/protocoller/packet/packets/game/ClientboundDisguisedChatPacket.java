package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.network.chat.ChatBoundNetwork;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import org.jetbrains.annotations.NotNull;

public class ClientboundDisguisedChatPacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private ChatComponent message;
    private ChatBoundNetwork chatType;

    public ClientboundDisguisedChatPacket(@NotNull PacketDataContainer packetData) {
        this.packetData = packetData;
        this.message = new ChatComponent(ChatSerializer.fromComponent(packetData.readIChatBaseComponent(0)));
        this.chatType = new ChatBoundNetwork((Object) packetData.readObject(0, ClassAccessor.get().getChatBoundNetworkClass()));
    }

    public ClientboundDisguisedChatPacket(@NotNull ChatComponent message, @NotNull ChatBoundNetwork chatType) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, message.asIChatBaseComponent(), chatType.createOriginal()));
        this.message = message;
        this.chatType = chatType;
    }

    public ChatComponent getMessage() {
        return message;
    }

    public void setMessage(ChatComponent message) {
        this.packetData = new ClientboundDisguisedChatPacket(message, chatType).packetData;
        this.message = message;
    }

    public ChatBoundNetwork getChatType() {
        return chatType;
    }

    public void setChatType(ChatBoundNetwork chatType) {
        this.packetData = new ClientboundDisguisedChatPacket(message, chatType).packetData;
        this.chatType = chatType;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.DISGUISED_CHAT_PACKET;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
