package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayOutKeepAlive implements Packet {

    private final PacketDataSerializer packetData;
    private long id;
    private final boolean isVeryLegacy = GlobalProvider.instance().getServer().isVeryLegacy();

    public PacketPlayOutKeepAlive(PacketDataSerializer packetData) {
        this.packetData = packetData;
        if(isVeryLegacy) {
            this.id = packetData.readInt(0);
        } else {
            this.id = packetData.readLong(0);
        }
    }

    public PacketPlayOutKeepAlive(long id) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        if(isVeryLegacy) {
            this.packetData = new PacketDataSerializer(converter.create(null, Math.toIntExact(id)));
        } else {
            this.packetData = new PacketDataSerializer(converter.create(null, id));
        }
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(isVeryLegacy) {
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
