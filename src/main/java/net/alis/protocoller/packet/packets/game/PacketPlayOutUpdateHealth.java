package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutUpdateHealth implements Packet {

    private final PacketDataSerializer packetData;
    private float health;
    private int food;
    private float saturation;

    public PacketPlayOutUpdateHealth(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.health = packetData.readFloat(0);
        this.food = packetData.readInt(0);
        this.saturation = packetData.readFloat(1);
    }

    public PacketPlayOutUpdateHealth(float health, int food, float saturation) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, health, food, saturation));
        this.health = health;
        this.food = food;
        this.saturation = saturation;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.packetData.writeFloat(0, health);
        this.health = health;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.packetData.writeInt(0, food);
        this.food = food;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.packetData.writeFloat(1, saturation);
        this.saturation = saturation;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.UPDATE_HEALTH;
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
