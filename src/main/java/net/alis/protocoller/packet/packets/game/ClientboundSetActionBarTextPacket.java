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
import org.jetbrains.annotations.NotNull;

public class ClientboundSetActionBarTextPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private ChatComponent text;

    public ClientboundSetActionBarTextPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.text = packetData.readIChatBaseComponent(0);
    }

    public ClientboundSetActionBarTextPacket(@NotNull ChatComponent text) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, text.asIChatBaseComponent()));
        this.text = text;
    }

    public ChatComponent getText() {
        return text;
    }

    public void setText(ChatComponent text) {
        this.packetData.writeIChatBaseComponent(0, text);
        this.text = text;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_ACTIONBAR_TEXT_PACKET;
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
