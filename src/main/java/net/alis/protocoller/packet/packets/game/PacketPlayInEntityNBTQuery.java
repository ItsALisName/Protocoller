package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInEntityNBTQuery implements Packet {

    private final PacketDataSerializer packetData;
    private int transactionId;
    private int entityId;

    public PacketPlayInEntityNBTQuery(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.transactionId = packetData.readInt(0);
        this.entityId = packetData.readInt(1);
    }

    public PacketPlayInEntityNBTQuery(int transactionId, int entityId) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(transactionId, 0), new IndexedParam<>(entityId, 1)};
                this.packetData = new PacketDataSerializer(creator.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, transactionId, entityId));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
