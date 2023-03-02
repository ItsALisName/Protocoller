package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutUpdateTime implements Packet {

    private final PacketDataSerializer packetData;
    private long time;
    private long timeOfDay;

    public PacketPlayOutUpdateTime(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.time = packetData.readLong(0);
        this.timeOfDay = packetData.readLong(1);
    }

    public PacketPlayOutUpdateTime(long time, long timeOfDay, boolean doDayLightCycle) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, time, timeOfDay, doDayLightCycle));
        this.time = time;
        this.timeOfDay = timeOfDay;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.packetData.writeLong(0, time);
        this.time = time;
    }

    public long getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(long timeOfDay) {
        this.packetData.writeLong(1, timeOfDay);
        this.timeOfDay = timeOfDay;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.UPDATE_TIME;
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
