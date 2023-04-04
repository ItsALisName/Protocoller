package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.craftbukkit.MagicNumbersSample;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutBlockChange implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private BlockPosition position;
    private MaterialData state;

    public PacketPlayOutBlockChange(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.state = MagicNumbersSample.getMaterialFromBlockData(packetData.readObject(0, ClassesContainer.get().getIBlockDataClass()));
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public MaterialData getState() {
        return state;
    }

    public void setState(MaterialData state) {
        this.packetData.writeObject(0, MagicNumbersSample.getBlock(state));
        this.state = state;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.BLOCK_CHANGE;
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
