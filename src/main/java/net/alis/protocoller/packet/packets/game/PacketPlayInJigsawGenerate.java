package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.core.BlockPosition;

public class PacketPlayInJigsawGenerate implements Packet {

    private final PacketDataSerializer packetData;
    private BlockPosition position;
    private int maxDepth;
    private boolean keepJigsaws;

    public PacketPlayInJigsawGenerate(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.maxDepth = packetData.readInt(0);
        this.keepJigsaws = packetData.readBoolean(0);
    }

    public PacketPlayInJigsawGenerate(BlockPosition position, int maxDepth, boolean keepJigsaws) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(position.createOriginal(), 0),
                        new IndexedParam<>(maxDepth, 0),
                        new IndexedParam<>(keepJigsaws, 0)
                };
                this.packetData = new PacketDataSerializer(creator.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, position.createOriginal(), maxDepth, keepJigsaws));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.position = position;
        this.maxDepth = maxDepth;
        this.keepJigsaws = keepJigsaws;
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.packetData.writeInt(0, maxDepth);
        this.maxDepth = maxDepth;
    }

    public boolean isKeepJigsaws() {
        return keepJigsaws;
    }

    public void setKeepJigsaws(boolean keepJigsaws) {
        this.packetData.writeBoolean(0, keepJigsaws);
        this.keepJigsaws = keepJigsaws;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.JIGSAW_GENERATE;
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
