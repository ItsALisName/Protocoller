package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.difficulty.Difficulty;

public class PacketPlayInDifficultyChange implements Packet {

    private final PacketDataSerializer packetData;
    private Difficulty difficulty;

    public PacketPlayInDifficultyChange(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.difficulty = Difficulty.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getDifficultyEnum()).ordinal());
    }

    public PacketPlayInDifficultyChange(Difficulty difficulty) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, difficulty.original()));
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.packetData.writeEnumConstant(0, difficulty.original());
        this.difficulty = difficulty;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.DIFFICULTY_CHANGE;
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
