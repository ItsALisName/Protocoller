package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.network.chat.ChatComponent;

public class ClientboundSystemChatPacket implements PlayOutPacket {

    private PacketDataContainer packetData;

    private ChatComponent content;
    private int typeId;
    private boolean overlay;

    public ClientboundSystemChatPacket(PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        if(IProtocolAccess.get().getServer().getVersion().equals(Version.v1_19)) {
            this.typeId = packetData.readInt(0);
        } else {
            this.overlay = packetData.readBoolean(0);
        }
        this.content = new ChatComponent(packetData.readString(0));
    }

    private ClientboundSystemChatPacket(ChatComponent content, int typeId, boolean overlay) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder builder = PacketBuilder.get(getPacketType());
        switch (builder.getPacketLevel().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, content.asIChatBaseComponent(), typeId));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, content.asIChatBaseComponent(), overlay));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.content = content;
        this.typeId = typeId;
        this.overlay = overlay;
    }

    // For 1.19
    public ClientboundSystemChatPacket(ChatComponent content, int typeId) {
        this(content, typeId, false);
    }

    //For 1.19.1 and higher
    public ClientboundSystemChatPacket(ChatComponent content, boolean overlay) {
        this(content, -1, overlay);
    }

    public ChatComponent getContent() {
        return content;
    }

    public void setContent(ChatComponent content) {
        this.packetData = new ClientboundSystemChatPacket(content, typeId, overlay).packetData;
        this.content = content;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        if(IProtocolAccess.get().getServer().getVersion().equals(Version.v1_19))
            this.packetData = new ClientboundSystemChatPacket(content, typeId).packetData;
        this.typeId = typeId;
    }

    public boolean isOverlay() {
        return overlay;
    }

    public void setOverlay(boolean overlay) {
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_1n2))
            this.packetData = new ClientboundSystemChatPacket(content, overlay).packetData;
        this.overlay = overlay;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SYSTEM_CHAT_PACKET;
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
