package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutCollect implements Packet {

    private final PacketDataSerializer packetData;
    private int entityId;
    private int collectorId;
    private int stackAmount;

    private final boolean is_1_9_orLower = GlobalProvider.instance().getServer().getVersion().ordinal() <= Version.v1_9.ordinal();

    public PacketPlayOutCollect(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.collectorId = packetData.readInt(1);
        this.stackAmount = 0;
        if(!is_1_9_orLower) {
            this.stackAmount = packetData.readInt(2);
        }
    }

    public PacketPlayOutCollect(int entityId, int collectorId, int stackAmount) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(converter.create(null, entityId, collectorId));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.create(null, entityId, collectorId, stackAmount));
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

    public int getStackAmount() {
        return stackAmount;
    }

    public void setStackAmount(int stackAmount) {
        if(!is_1_9_orLower) this.packetData.writeInt(2, stackAmount);
        this.stackAmount = stackAmount;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.COLLECT;
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
