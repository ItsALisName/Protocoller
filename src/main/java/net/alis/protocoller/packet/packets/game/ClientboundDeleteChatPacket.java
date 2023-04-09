package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.network.chat.MessageSignature;

public class ClientboundDeleteChatPacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private MessageSignature messageSignature;

    public ClientboundDeleteChatPacket(PacketDataContainer packetData) {
        this.packetData = packetData;
        if(GlobalProvider.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_19_1n2)) {
            this.messageSignature = new MessageSignature((Object) packetData.readObject(0, ClassAccessor.get().getMessageSignatureClass()));
        } else {
            MessageSignature.Storage s = new MessageSignature.Storage(packetData.readObject(0, ClassAccessor.get().getMessageSignatureStorageClass()));
            if(s.getFullSignature() != null) this.messageSignature = s.getFullSignature();
        }
    }

    public ClientboundDeleteChatPacket(MessageSignature messageSignature) {
        PacketBuilder builder = PacketBuilder.get(getPacketType());
        if(GlobalProvider.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_19_1n2)) {
            this.packetData = new PacketDataSerializer(builder.buildPacket(null, messageSignature.createOriginal()));
        } else {
            this.packetData = new PacketDataSerializer(builder.buildPacket(null, new MessageSignature.Storage(1, messageSignature).createOriginal()));
        }
        this.messageSignature = messageSignature;
    }

    public MessageSignature getMessageSignature() {
        return messageSignature;
    }

    public void setMessageSignature(MessageSignature messageSignature) {
        this.packetData = new ClientboundDeleteChatPacket(messageSignature).packetData;
        this.messageSignature = messageSignature;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.DELETE_CHAT_PACKET;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return null;
    }
}
