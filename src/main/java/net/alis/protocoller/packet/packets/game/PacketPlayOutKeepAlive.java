package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutKeepAlive implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private long id;
    private final boolean legacyPacket = GlobalProvider.instance().getServer().isVeryLegacy();

    public PacketPlayOutKeepAlive(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(legacyPacket) {
            this.id = packetData.readInt(0);
        } else {
            this.id = packetData.readLong(0);
        }
    }

    public PacketPlayOutKeepAlive(long id) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        if(legacyPacket) {
            this.packetData = new PacketDataSerializer(converter.buildPacket(null, Math.toIntExact(id)));
        } else {
            this.packetData = new PacketDataSerializer(converter.buildPacket(null, id));
        }
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(legacyPacket) {
            this.packetData.writeInt(0, Math.toIntExact(id));
        } else {
            this.packetData.writeLong(0, id);
        }
        this.id = id;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.KEEP_ALIVE;
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
