package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.TitleData;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import org.jetbrains.annotations.NotNull;

public class ClientboundSetSubtitleTextPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private ChatComponent subtitle;

    public ClientboundSetSubtitleTextPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.subtitle = packetData.readIChatBaseComponent(0);
    }

    public ClientboundSetSubtitleTextPacket(@NotNull TitleData titleData) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, titleData.getSubtitle().asIChatBaseComponent()));
        this.subtitle = titleData.getSubtitle();
    }

    public ChatComponent getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@NotNull TitleData title) {
        this.packetData.writeIChatBaseComponent(0, title.getSubtitle());
        this.subtitle = title.getSubtitle();
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_SUBTITLE_TEXT_PACKET;
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
