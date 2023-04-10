package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInCloseWindow implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int syncId;

    public PacketPlayInCloseWindow(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.syncId = packetData.readInt(0);
    }

    public PacketPlayInCloseWindow(int syncId) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, syncId));
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
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
