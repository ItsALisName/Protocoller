package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class ClientboundPingPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int id;

    public ClientboundPingPacket(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.id = packetData.readInt(0);

    }

    public ClientboundPingPacket(int id) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, id));
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.packetData.writeInt(0, id);
        this.id = id;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.PING_PACKET;
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
