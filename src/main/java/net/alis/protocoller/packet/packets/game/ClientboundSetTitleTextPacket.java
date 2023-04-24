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

public class ClientboundSetTitleTextPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private ChatComponent title;

    public ClientboundSetTitleTextPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.title = packetData.readIChatBaseComponent(0);
    }

    public ClientboundSetTitleTextPacket(@NotNull TitleData title) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, title.getTitle().asIChatBaseComponent()));
        this.title = title.getTitle();
    }

    public ChatComponent getTitle() {
        return title;
    }

    public void setTitle(@NotNull TitleData title) {
        this.packetData.writeIChatBaseComponent(0, title.getTitle());
        this.title = title.getTitle();
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_TITLE_TEXT_PACKET;
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
