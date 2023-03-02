package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInCloseWindow implements Packet {

    private final PacketDataSerializer packetData;
    private int syncId;

    public PacketPlayInCloseWindow(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.syncId = packetData.readInt(0);
    }

    public PacketPlayInCloseWindow(int syncId) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, syncId));
        this.syncId = syncId;
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.packetData.writeInt(0, syncId);
        this.syncId = syncId;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.CLOSE_WINDOW;
    }

    @Override
    public PacketDataSerializer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
