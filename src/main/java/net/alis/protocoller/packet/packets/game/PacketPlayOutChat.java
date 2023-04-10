package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.md_5.bungee.api.MessageType;

import java.util.UUID;

public class PacketPlayOutChat implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private ChatComponent message;
    private MessageType messageType;
    private @Nullable UUID sender;

    public PacketPlayOutChat(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.message = new ChatComponent(ChatSerializer.fromComponent(packetData.readIChatBaseComponent(0)));
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_12)){
            this.messageType = MessageType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getChatMessageTypeEnum()).ordinal());
        } else {
            this.messageType = MessageType.getById(packetData.readInt(0));
        }
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16_4n5)) {
            this.sender = packetData.readUUID(0);
        }
    }

    public PacketPlayOutChat(ChatComponent message, MessageType messageType, @Nullable UUID sender) {
        PacketBuilder builder = PacketBuilder.get(getPacketType());
        switch (builder.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, message.asIChatBaseComponent(), messageType.getId()));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, message.asIChatBaseComponent(), messageType.original()));
                break;
            }
            case 3: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, message.asIChatBaseComponent(), messageType.original(), sender));
                break;
            }
            default:{
                this.packetData = null;
                break;
            }
        }
        this.message = message;
        this.messageType = messageType;
        this.sender = sender;
    }

    public PacketPlayOutChat(ChatComponent message, MessageType messageType) {
        this(message, messageType, null);
    }

    public ChatComponent getMessage() {
        return message;
    }

    public void setMessage(ChatComponent message) {
        this.packetData.writeIChatBaseComponent(0, message);
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_12)) {
            this.packetData.writeEnumConstant(0, messageType.original());
        } else {
            this.packetData.writeInt(0, messageType.getId());
        }
        this.messageType = messageType;
    }

    @Nullable
    public UUID getSender() {
        return sender;
    }

    public void setSender(@Nullable UUID sender) {
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16_4n5)) {
            this.packetData.writeUUID(0, sender);
        }
        this.sender = sender;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.CHAT;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
