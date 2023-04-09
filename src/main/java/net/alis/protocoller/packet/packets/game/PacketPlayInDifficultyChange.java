package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.difficulty.Difficulty;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInDifficultyChange implements PlayInPacket {

    private final PacketDataContainer packetData;
    private Difficulty difficulty;

    public PacketPlayInDifficultyChange(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.difficulty = Difficulty.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getDifficultyEnum()).ordinal());
    }

    public PacketPlayInDifficultyChange(@NotNull Difficulty difficulty) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, difficulty.original()));
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(@NotNull Difficulty difficulty) {
        this.packetData.writeEnumConstant(0, difficulty.original());
        this.difficulty = difficulty;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.DIFFICULTY_CHANGE;
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
