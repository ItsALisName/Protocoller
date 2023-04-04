package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.craftbukkit.MagicNumbersSample;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutBlockAction implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private BlockPosition position;
    private Material block;
    private int type;
    private int dataId;

    public PacketPlayOutBlockAction(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.block = MagicNumbersSample.getMaterialFromBlock(packetData.readObject(0, ClassesContainer.get().getMinecraftBlockClass()));
        this.type = packetData.readInt(0);
        this.dataId = packetData.readInt(1);
    }

    public PacketPlayOutBlockAction(@NotNull BlockPosition position, Material block, int type, int data) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, position.createOriginal(), MagicNumbersSample.getBlock(block), type, data));
        this.position = position;
        this.block = block;
        this.type = type;
        this.dataId = data;
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public Material getBlock() {
        return block;
    }

    public void setBlock(Material block) {
        this.packetData.writeObject(0, MagicNumbersSample.getBlock(block));
        this.block = block;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.packetData.writeInt(0, type);
        this.type = type;
    }

    public int getDataId() {
        return dataId;
    }

    public void setData(int data) {
        this.packetData.writeInt(1, data);
        this.dataId = data;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.BLOCK_ACTION;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.getData().getRawPacket();
    }
}
