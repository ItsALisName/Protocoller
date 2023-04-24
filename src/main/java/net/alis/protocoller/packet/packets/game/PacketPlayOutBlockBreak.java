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
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.craftbukkit.MagicNumbersSample;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutBlockBreak implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private BlockPosition position;
    private MaterialData state;
    private PacketPlayInBlockDig.PlayerDigType action;
    private boolean approved;

    public PacketPlayOutBlockBreak(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.state = MagicNumbersSample.getMaterialFromBlockData(packetData.readObject(0, ClassAccessor.get().getIBlockDataClass()));
        this.action = PacketPlayInBlockDig.PlayerDigType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getPlayerDigTypeEnum()).ordinal());
        this.approved = packetData.readBoolean(0);
    }

    public PacketPlayOutBlockBreak(@NotNull BlockPosition position, MaterialData state, PacketPlayInBlockDig.@NotNull PlayerDigType action, boolean approved) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, position.createOriginal(), MagicNumbersSample.getBlock(state), action.original(), approved, new String("")));
        this.position = position;
        this.state = state;
        this.action = action;
        this.approved = approved;
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

    public PacketPlayInBlockDig.PlayerDigType getAction() {
        return action;
    }

    public void setAction(PacketPlayInBlockDig.PlayerDigType action) {
        this.packetData.writeEnumConstant(0, action.original());
        this.action = action;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.packetData.writeBoolean(0, approved);
        this.approved = approved;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.BLOCK_BREAK;
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
