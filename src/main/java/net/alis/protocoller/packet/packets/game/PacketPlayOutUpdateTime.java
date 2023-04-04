package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutUpdateTime implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private long time;
    private long timeOfDay;

    public PacketPlayOutUpdateTime(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.time = packetData.readLong(0);
        this.timeOfDay = packetData.readLong(1);
    }

    public PacketPlayOutUpdateTime(long time, long timeOfDay, boolean doDayLightCycle) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, time, timeOfDay, doDayLightCycle));
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
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
