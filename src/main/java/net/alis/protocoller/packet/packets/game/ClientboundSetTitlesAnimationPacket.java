package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.TitleData;
import org.jetbrains.annotations.NotNull;

public class ClientboundSetTitlesAnimationPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int fadeInTicks;
    private int stayTicks;
    private int fadeOutTicks;

    public ClientboundSetTitlesAnimationPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.fadeInTicks = packetData.readInt(0);
        this.stayTicks = packetData.readInt(1);
        this.fadeOutTicks = packetData.readInt(2);
    }

    public ClientboundSetTitlesAnimationPacket(TitleData titleData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, this.getFadeInTicks(), titleData.getStayTicks(), titleData.getFadeOutTicks()));
        this.fadeInTicks = titleData.getFadeInTicks();
        this.stayTicks = titleData.getStayTicks();
        this.fadeOutTicks = titleData.getFadeOutTicks();
    }

    public int getFadeInTicks() {
        return fadeInTicks;
    }

    public void setFadeInTicks(int fadeInTicks) {
        this.packetData.writeInt(0, fadeInTicks);
        this.fadeInTicks = fadeInTicks;
    }

    public int getStayTicks() {
        return stayTicks;
    }

    public void setStayTicks(int stayTicks) {
        this.packetData.writeInt(1, stayTicks);
        this.stayTicks = stayTicks;
    }

    public int getFadeOutTicks() {
        return fadeOutTicks;
    }

    public void setFadeOutTicks(int fadeOutTicks) {
        this.packetData.writeInt(2, fadeOutTicks);
        this.fadeOutTicks = fadeOutTicks;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.SET_TITLES_ANIMATION_PACKET;
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
