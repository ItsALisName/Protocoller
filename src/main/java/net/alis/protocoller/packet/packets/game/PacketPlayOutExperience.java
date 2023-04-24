package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutExperience implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private float barProgress;
    private int experienceLevel;
    private int experience;

    public PacketPlayOutExperience(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.barProgress = packetData.readFloat(0);
        this.experienceLevel = packetData.readInt(0);
        this.experience = packetData.readInt(1);
    }

    public PacketPlayOutExperience(float barProgress, int experienceLevel, int experience) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, barProgress, experienceLevel, experience));
        this.barProgress = barProgress;
        this.experienceLevel = experienceLevel;
        this.experience = experience;
    }

    public float getBarProgress() {
        return barProgress;
    }

    public void setBarProgress(float barProgress) {
        this.packetData.writeFloat(0, barProgress);
        this.barProgress = barProgress;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.packetData.writeInt(0, experienceLevel);
        this.experienceLevel = experienceLevel;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.packetData.writeInt(1, experience);
        this.experience = experience;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.EXPERIENCE;
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
