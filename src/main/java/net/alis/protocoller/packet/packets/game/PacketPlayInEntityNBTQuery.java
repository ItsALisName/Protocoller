package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayInEntityNBTQuery implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int transactionId;
    private int entityId;

    public PacketPlayInEntityNBTQuery(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.transactionId = packetData.readInt(0);
        this.entityId = packetData.readInt(1);
    }

    public PacketPlayInEntityNBTQuery(int transactionId, int entityId) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(transactionId, 0), new IndexedParam<>(entityId, 1)};
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, transactionId, entityId));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.transactionId = transactionId;
        this.entityId = entityId;
    }

    @Nullable
    public Entity getEntity() {
        return GlobalProvider.get().getServer().getEntityList().getEntity(this.entityId);
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.packetData.writeInt(0, transactionId);
        this.transactionId = transactionId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.packetData.writeInt(1, entityId);
        this.entityId = entityId;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ENTITY_NBT_QUERY;
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
