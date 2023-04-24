package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.samples.sounds.SoundCategory;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutStopSound implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private MinecraftKey minecraftKey;
    private SoundCategory soundCategory;

    public PacketPlayOutStopSound(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.minecraftKey = packetData.readMinecraftKey(0);
        this.soundCategory = SoundCategory.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getSoundCategoryEnum()).ordinal());
    }

    public PacketPlayOutStopSound(@NotNull MinecraftKey minecraftKey, @NotNull SoundCategory soundCategory) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
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

    public void setSoundCategory(@NotNull SoundCategory soundCategory) {
        this.packetData.writeEnumConstant(0, soundCategory.original());
        this.soundCategory = soundCategory;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.STOP_SOUND;
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
