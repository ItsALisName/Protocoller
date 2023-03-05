package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutExperience implements Packet {

    private final PacketDataContainer packetData;
    private float barProgress;
    private int experienceLevel;
    private int experience;

    public PacketPlayOutExperience(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.barProgress = packetData.readFloat(0);
        this.experienceLevel = packetData.readInt(0);
        this.experience = packetData.readInt(1);
    }

    public PacketPlayOutExperience(float barProgress, int experienceLevel, int experience) {
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
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
