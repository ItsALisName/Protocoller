package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInDifficultyLock implements PlayInPacket {

    private final PacketDataContainer packetData;
    private boolean difficultyLocked;

    public PacketPlayInDifficultyLock(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.difficultyLocked = packetData.readBoolean(0);
    }

    public PacketPlayInDifficultyLock(boolean difficultyLocked) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        if(converter.getPacketLevel().getLevel() != 0) {
            this.packetData = new PacketDataSerializer(converter.buildPacket(null, difficultyLocked));
        } else {
            IndexedParam<?,?>[] params = {
                    new IndexedParam<>(difficultyLocked, 0)
            };
            this.packetData = new PacketDataSerializer(converter.buildPacket(params));
        }
        this.difficultyLocked = difficultyLocked;
    }

    public boolean isDifficultyLocked() {
        return difficultyLocked;
    }

    public void setDifficultyLocked(boolean difficultyLocked) {
        this.packetData.writeBoolean(0, difficultyLocked);
        this.difficultyLocked = difficultyLocked;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.DIFFICULTY_LOCK;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
