package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutExperience implements Packet {

    private final PacketDataSerializer packetData;
    private float barProgress;
    private int experienceLevel;
    private int experience;

    public PacketPlayOutExperience(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.barProgress = packetData.readFloat(0);
        this.experienceLevel = packetData.readInt(0);
        this.experience = packetData.readInt(1);
    }

    public PacketPlayOutExperience(float barProgress, int experienceLevel, int experience) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, barProgress, experienceLevel, experience));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
