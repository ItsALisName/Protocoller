package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutBlockBreakAnimation implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int entityId;
    private BlockPosition position;
    private int progress;

    public PacketPlayOutBlockBreakAnimation(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.position = packetData.readBlockPosition(0);
        this.progress = packetData.readInt(1);
    }

    public PacketPlayOutBlockBreakAnimation(int entityId, @NotNull BlockPosition position, int progress) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, entityId, position.createOriginal(), progress));
        this.entityId = entityId;
        this.position = position;
        this.progress = progress;
    }

    @Nullable
    public Entity getEntity() {
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.entityId);
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        if(GlobalProvider.instance().getData().getEntitiesContainer().isIdPresent(entityId)){
            this.packetData.writeInt(0, entityId);
            this.entityId = entityId;
        }
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.packetData.writeInt(1, progress);
        this.progress = progress;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.BLOCK_BREAK_ANIMATION;
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
