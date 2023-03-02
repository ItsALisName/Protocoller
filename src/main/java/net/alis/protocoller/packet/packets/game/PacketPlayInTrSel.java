package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInTrSel implements Packet {

    private final PacketDataSerializer packetData;
    private int tradeId;

    public PacketPlayInTrSel(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.tradeId = packetData.readInt(0);
    }

    public PacketPlayInTrSel(int tradeId) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(tradeId, 0)};
                this.packetData = new PacketDataSerializer(converter.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.create(null, tradeId));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
