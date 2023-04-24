package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ClientboundServerDataPacket implements PlayOutPacket {

    private final PacketBuilder builder = PacketBuilder.get(getPacketType());

    private final PacketDataContainer packetData;
    private Optional<ChatComponent> description;
    private Optional<byte[]> favicon;
    private boolean previewsChat;

    public ClientboundServerDataPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        switch (builder.getPacketLevel().getLevel()) {
            case 1:
            case 2: {
                this.description = Optional.of(new ChatComponent(ChatSerializer.fromComponent(packetData.readOptional(0).get())));
                this.favicon = Optional.of(((String)packetData.readOptional(1).get()).getBytes());
                this.previewsChat = packetData.readBoolean(0);
                break;
            }
            case 3: {
                this.description = Optional.of(new ChatComponent(ChatSerializer.fromComponent(packetData.readIChatBaseComponent(0))));
                this.favicon = Optional.of(((byte[]) packetData.readOptional(0).get()));
                this.previewsChat = packetData.readBoolean(0);
                break;
            }
        }
    }

    public ClientboundServerDataPacket(Optional<ChatComponent> description, Optional<byte[]> favicon, boolean previewsChat) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        switch (builder.getPacketLevel().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, description.get().asIChatBaseComponent(), new String(favicon.get()), previewsChat));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, description.get().asIChatBaseComponent(), new String(favicon.get()), previewsChat, false));
                break;
            }
            case 3: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, description.get().asIChatBaseComponent(), favicon, previewsChat));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.description = description;
        this.favicon = favicon;
        this.previewsChat = previewsChat;
    }

    public Optional<ChatComponent> getDescription() {
        return description;
    }

    public void setDescription(Optional<ChatComponent> description) {
        switch (builder.getPacketLevel().getLevel()) {
            case 1:
            case 2: {
                this.packetData.writeOptional(0, Optional.of(description.get().asIChatBaseComponent()));
            }
            case 3: {
                this.packetData.writeIChatBaseComponent(0, description.get());
            }
        }
        this.description = description;
    }

    public Optional<byte[]> getFavicon() {
        return favicon;
    }

    public void setFavicon(Optional<byte[]> favicon) {
        switch (builder.getPacketLevel().getLevel()) {
            case 1:
            case 2: {
                this.packetData.writeOptional(1, Optional.of(new String(favicon.get())));
            }
            case 3: {
                this.packetData.writeOptional(0, favicon);
            }
        }
        this.favicon = favicon;
    }

    public boolean isPreviewsChat() {
        return previewsChat;
    }

    public void setPreviewsChat(boolean previewsChat) {
        this.packetData.writeBoolean(0, previewsChat);
        this.previewsChat = previewsChat;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SERVER_DATA_PACKET;
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
