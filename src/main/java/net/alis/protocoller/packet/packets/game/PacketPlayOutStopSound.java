package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.resources.MinecraftKey;
import net.alis.protocoller.parent.sounds.SoundCategory;

public class PacketPlayOutStopSound implements Packet {

    private final PacketDataContainer packetData;
    private MinecraftKey minecraftKey;
    private SoundCategory soundCategory;

    public PacketPlayOutStopSound(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.minecraftKey = packetData.readMinecraftKey(0);
        this.soundCategory = SoundCategory.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getSoundCategoryEnum()).ordinal());
    }

    public PacketPlayOutStopSound(MinecraftKey minecraftKey, SoundCategory soundCategory) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, minecraftKey.createOriginal(), soundCategory.original()));
        this.minecraftKey = minecraftKey;
        this.soundCategory = soundCategory;
    }

    public MinecraftKey getMinecraftKey() {
        return minecraftKey;
    }

    public void setMinecraftKey(MinecraftKey minecraftKey) {
        this.packetData.writeMinecraftKey(0, minecraftKey);
        this.minecraftKey = minecraftKey;
    }

    public SoundCategory getSoundCategory() {
        return soundCategory;
    }

    public void setSoundCategory(SoundCategory soundCategory) {
        this.packetData.writeEnumConstant(0, soundCategory.original());
        this.soundCategory = soundCategory;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.STOP_SOUND;
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
