package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.MinecraftEncryption;
import net.alis.protocoller.samples.network.chat.*;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class ClientboundPlayerChatPacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private final PacketBuilder builder = PacketBuilder.get(getPacketType());

    //Fields for 1.19
    private ChatComponent signedContent;
    private Optional<ChatComponent> unsignedContent;
    private int typeId;
    private ChatSender sender;
    private Instant timestamp;
    private MinecraftEncryption.SignatureData saltSignature;
    //Fields for 1.19

    //Field for 1.19.1 - 1.19.2
    private PlayerChatMessage content;
    //Field for 1.19.1 - 1.19.2

    private ChatBoundNetwork chatType;

    //Fields for 1.19.3 and higher
    private UUID sender$1;
    private int index;
    private @Nullable MessageSignature signature;
    private SignedMessageBody.Packed body;
    private @Nullable ChatComponent unsignedContent$1;
    private FilterMask filterMask;
    //Fields for 1.19.3 and higher

    public ClientboundPlayerChatPacket(PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = packetData;
        switch (builder.getPacketLevel().getLevel()) {
            case 1: {
                this.signedContent = packetData.readIChatBaseComponent(0);
                Optional<Object> unsContent = (Optional<Object>) packetData.readOptional(0);
                if(unsContent != null && unsContent.isPresent()) {
                    this.unsignedContent = Optional.of(new ChatComponent(ChatSerializer.fromComponent(unsContent.get())));
                }
                this.typeId = packetData.readInt(0);
                this.sender = new ChatSender(packetData.readObject(0, ChatSender.clazz()));
                this.timestamp = packetData.readObject(0, Instant.class);
                Object signData = packetData.readObject(0, ClassAccessor.get().getMinecraftEncryptionSignatureDataClass());
                if(signData != null) this.saltSignature = new MinecraftEncryption.SignatureData(signData);
                break;
            }
            case 2: {
                Object pMessage = packetData.readObject(0, ClassAccessor.get().getPlayerChatMessageClass());
                if(pMessage != null) this.content = new PlayerChatMessage(pMessage);
                Object cBound = packetData.readObject(0, ChatBoundNetwork.clazz());
                if(cBound != null) this.chatType = new ChatBoundNetwork(cBound);
                break;
            }
            case 3: {
                this.sender$1 = packetData.readUUID(0);
                this.index = packetData.readInt(0);
                Object msgSignature = packetData.readObject(0, MessageSignature.clazz());
                if(msgSignature != null) this.signature = new MessageSignature(msgSignature);
                Object mdBody = packetData.readObject(0, SignedMessageBody.Packed.clazz());
                if(mdBody != null) this.body = new SignedMessageBody.Packed(mdBody);
                Object unsContent = packetData.readIChatBaseComponent(0);
                if(unsContent != null) this.unsignedContent$1 = new ChatComponent(ChatSerializer.fromComponent(unsContent));
                this.filterMask = new FilterMask((Object) packetData.readObject(0, FilterMask.clazz()));
                break;
            }
        }
    }

    private ClientboundPlayerChatPacket(ChatComponent signedContent, Optional<ChatComponent> unsignedContent, int typeId, ChatSender sender, Instant timestamp, MinecraftEncryption.SignatureData saltSignature, PlayerChatMessage content, ChatBoundNetwork chatType, UUID sender$1, int index, @Nullable MessageSignature signature, SignedMessageBody.Packed body, @Nullable ChatComponent unsignedContent$1, FilterMask filterMask) {
        switch (builder.getPacketLevel().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, signedContent.asIChatBaseComponent(), Optional.of(unsignedContent.get().asIChatBaseComponent()), typeId, sender.createOriginal(), timestamp, saltSignature != null ?  saltSignature.createOriginal() : null));
                this.signedContent = signedContent;
                this.unsignedContent = unsignedContent;
                this.typeId = typeId;
                this.sender = sender;
                this.timestamp = timestamp;
                this.saltSignature = saltSignature;
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, content.createOriginal(), chatType.createOriginal()));
                this.content = content;
                this.chatType = chatType;
                break;
            }
            case 3: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, sender$1, index, signature.createOriginal(), body.createOriginal(), unsignedContent$1.asIChatBaseComponent(), filterMask.createOriginal(), chatType.createOriginal()));
                this.sender$1 = sender$1;
                this.index = index;
                this.signature = signature;
                this.body = body;
                this.unsignedContent$1 = unsignedContent$1;
                this.filterMask = filterMask;
                this.chatType = chatType;
                break;
            }
        }
    }

    //For 1.19
    public ClientboundPlayerChatPacket(ChatComponent signedContent, Optional<ChatComponent> unsignedContent, int typeId, ChatSender sender, Instant timestamp, MinecraftEncryption.SignatureData saltSignature) {
        this(signedContent, unsignedContent, typeId, sender, timestamp, saltSignature, null, null, null, -1, null, null, null, null);
    }

    //For 1.19.1 - 1.19.2
    public ClientboundPlayerChatPacket(PlayerChatMessage content, ChatBoundNetwork chatType) {
        this(null, null, -1, null, null, null, content, chatType, null, -1, null, null, null, null);
    }

    //For 1.19.3 and higher
    public ClientboundPlayerChatPacket(UUID sender$1, int index, @Nullable MessageSignature signature, SignedMessageBody.Packed body, @Nullable ChatComponent unsignedContent$1, FilterMask filterMask, ChatBoundNetwork chatType) {
        this(null, null, -1, null, null, null, null, chatType, sender$1, index, signature, body, unsignedContent$1, filterMask);
    }

    public ChatComponent getSignedContent() {
        return signedContent;
    }

    public void setSignedContent(ChatComponent signedContent) {
        if(builder.getPacketLevel().getLevel() == 1) {
            this.packetData = new ClientboundPlayerChatPacket(signedContent, unsignedContent, typeId, sender, timestamp, saltSignature).packetData;
            this.signedContent = signedContent;
        }
    }

    public Optional<ChatComponent> getUnsignedContent() {
        return unsignedContent;
    }

    public void setUnsignedContent(Optional<ChatComponent> unsignedContent) {
        if(builder.getPacketLevel().getLevel() == 1) {
            this.packetData = new ClientboundPlayerChatPacket(signedContent, unsignedContent, typeId, sender, timestamp, saltSignature).packetData;
            this.unsignedContent = unsignedContent;
        }
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        if(builder.getPacketLevel().getLevel() == 1) {
            this.packetData = new ClientboundPlayerChatPacket(signedContent, unsignedContent, typeId, sender, timestamp, saltSignature).packetData;
            this.typeId = typeId;
        }
    }

    public ChatSender getSender() {
        return sender;
    }

    public void setSender(ChatSender sender) {
        if(builder.getPacketLevel().getLevel() == 1) {
            this.packetData = new ClientboundPlayerChatPacket(signedContent, unsignedContent, typeId, sender, timestamp, saltSignature).packetData;
            this.sender = sender;
        }
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        if(builder.getPacketLevel().getLevel() == 1) {
            this.packetData = new ClientboundPlayerChatPacket(signedContent, unsignedContent, typeId, sender, timestamp, saltSignature).packetData;
            this.timestamp = timestamp;
        }
    }

    public MinecraftEncryption.SignatureData getSaltSignature() {
        return saltSignature;
    }

    public void setSaltSignature(MinecraftEncryption.SignatureData saltSignature) {
        if(builder.getPacketLevel().getLevel() == 1) {
            this.packetData = new ClientboundPlayerChatPacket(signedContent, unsignedContent, typeId, sender, timestamp, saltSignature).packetData;
            this.saltSignature = saltSignature;
        }
    }

    public PlayerChatMessage getContent() {
        return content;
    }

    public void setContent(PlayerChatMessage content) {
        if(builder.getPacketLevel().getLevel() == 2) {
            this.packetData = new ClientboundPlayerChatPacket(content, chatType).packetData;
            this.content = content;
        }
    }

    public ChatBoundNetwork getChatType() {
        return chatType;
    }

    public void setChatType(ChatBoundNetwork chatType) {
        if(builder.getPacketLevel().getLevel() == 2) {
            this.packetData = new ClientboundPlayerChatPacket(content, chatType).packetData;
            this.chatType = chatType;
            return;
        } else if(builder.getPacketLevel().getLevel() == 3) {
            this.packetData = new ClientboundPlayerChatPacket(sender$1, index, signature, body, unsignedContent$1, filterMask, chatType).packetData;
            this.chatType = chatType;
        }
    }

    public UUID getSender$1() {
        return sender$1;
    }

    public void setSender$1(UUID sender$1) {
        if(builder.getPacketLevel().getLevel() == 3) {
            this.packetData = new ClientboundPlayerChatPacket(sender$1, index, signature, body, unsignedContent$1, filterMask, chatType).packetData;
            this.sender$1 = sender$1;
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        if(builder.getPacketLevel().getLevel() == 3){
            this.packetData = new ClientboundPlayerChatPacket(sender$1, index, signature, body, unsignedContent$1, filterMask, chatType).packetData;
            this.index = index;
        }
    }

    @Nullable
    public MessageSignature getSignature() {
        return signature;
    }

    public void setSignature(@Nullable MessageSignature signature) {
        if(builder.getPacketLevel().getLevel() == 3){
            this.packetData = new ClientboundPlayerChatPacket(sender$1, index, signature, body, unsignedContent$1, filterMask, chatType).packetData;
            this.signature = signature;
        }
    }

    public SignedMessageBody.Packed getBody() {
        return body;
    }

    public void setBody(SignedMessageBody.Packed body) {
        if(builder.getPacketLevel().getLevel() == 3) {
            this.packetData = new ClientboundPlayerChatPacket(sender$1, index, signature, body, unsignedContent$1, filterMask, chatType).packetData;
            this.body = body;
        }
    }

    @Nullable
    public ChatComponent getUnsignedContent$1() {
        return unsignedContent$1;
    }

    public void setUnsignedContent$1(@Nullable ChatComponent unsignedContent$1) {
        if(builder.getPacketLevel().getLevel() == 3) {
            this.packetData = new ClientboundPlayerChatPacket(sender$1, index, signature, body, unsignedContent$1, filterMask, chatType).packetData;
            this.unsignedContent$1 = unsignedContent$1;
        }
    }

    public FilterMask getFilterMask() {
        return filterMask;
    }

    public void setFilterMask(FilterMask filterMask) {
        if(builder.getPacketLevel().getLevel() == 3) {
            this.packetData = new ClientboundPlayerChatPacket(sender$1, index, signature, body, unsignedContent$1, filterMask, chatType).packetData;
            this.filterMask = filterMask;
        }
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.PLAYER_CHAT_PACKET;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.getData().getRawPacket();
    }
}
