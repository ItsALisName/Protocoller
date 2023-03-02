package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInDifficultyLock implements Packet {

    private final PacketDataSerializer packetData;
    private boolean difficultyLocked;

    public PacketPlayInDifficultyLock(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.difficultyLocked = packetData.readBoolean(0);
    }

    public PacketPlayInDifficultyLock(boolean difficultyLocked) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        if(converter.getConstructorIndicator().getLevel() != 0) {
            this.packetData = new PacketDataSerializer(converter.create(null, difficultyLocked));
        } else {
            IndexedParam<?,?>[] params = {
                    new IndexedParam<>(difficultyLocked, 0)
            };
            this.packetData = new PacketDataSerializer(converter.create(params));
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
    public PacketDataSerializer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
