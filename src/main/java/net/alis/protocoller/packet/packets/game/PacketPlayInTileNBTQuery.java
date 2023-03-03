package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.util.annotations.AddedSince;

import static net.alis.protocoller.bukkit.enums.Version.v1_13;

@AddedSince(v1_13)
public class PacketPlayInTileNBTQuery implements Packet {

    private final PacketDataSerializer packetData;
    private int transactionId;
    private BlockPosition position;

    public PacketPlayInTileNBTQuery(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.transactionId = packetData.readInt(0);
        this.position = packetData.readBlockPosition(0);
    }

    public PacketPlayInTileNBTQuery(int transactionId, BlockPosition position) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(transactionId, 0),
                        new IndexedParam<>(position.createOriginal(), 0)
                };
                this.packetData = new PacketDataSerializer(creator.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, transactionId, position.createOriginal()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.transactionId = transactionId;
        this.position = position;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.packetData.writeInt(0, transactionId);
        this.transactionId = transactionId;
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.TILE_NBT_QUERY;
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
