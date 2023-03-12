package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutBed implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private BlockPosition position;
    private int entityId;

    public PacketPlayOutBed(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.entityId = packetData.readInt(0);
    }

    public PacketPlayOutBed(@NotNull BlockPosition position, int entityId) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, GlobalProvider.instance().getData().getEntitiesContainer().getEntity(entityId), position.createOriginal()));
        this.position = position;
        this.entityId = entityId;
    }

    @Nullable
    public Entity getEntity() {
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.entityId);
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.packetData.writeInt(0, entityId);
        this.entityId = entityId;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.BED;
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
