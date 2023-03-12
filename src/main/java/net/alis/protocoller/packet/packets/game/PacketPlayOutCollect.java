package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutCollect implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int entityId;
    private int collectorId;
    private int stackAmount;

    private final boolean legacyPacket = GlobalProvider.instance().getServer().getVersion().lessThanOrEqualTo(Version.v1_9);

    public PacketPlayOutCollect(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.collectorId = packetData.readInt(1);
        this.stackAmount = 0;
        if(!legacyPacket) {
            this.stackAmount = packetData.readInt(2);
        }
    }

    public PacketPlayOutCollect(int entityId, int collectorId, int stackAmount) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, entityId, collectorId));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, entityId, collectorId, stackAmount));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.entityId = entityId;
        this.collectorId = collectorId;
        this.stackAmount = stackAmount;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.packetData.writeInt(0, entityId);
        this.entityId = entityId;
    }

    public int getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(int collectorId) {
        this.packetData.writeInt(1, collectorId);
        this.collectorId = collectorId;
    }

    @Nullable
    public Entity getEntity() {
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.entityId);
    }

    public int getStackAmount() {
        return stackAmount;
    }

    public void setStackAmount(int stackAmount) {
        if(!legacyPacket) this.packetData.writeInt(2, stackAmount);
        this.stackAmount = stackAmount;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.COLLECT;
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
