package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInTrSel implements Packet {

    private final PacketDataContainer packetData;
    private int tradeId;

    public PacketPlayInTrSel(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.tradeId = packetData.readInt(0);
    }

    public PacketPlayInTrSel(int tradeId) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(tradeId, 0)};
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, tradeId));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.tradeId = tradeId;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.packetData.writeInt(0, tradeId);
        this.tradeId = tradeId;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.TR_SEL;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
