package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInKeepAlive implements Packet {

    private final PacketDataSerializer packetData;
    private long id;

    public PacketPlayInKeepAlive(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.id = packetData.readLong(0);
    }

    public PacketPlayInKeepAlive(long id) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                this.packetData = new PacketDataSerializer(creator.create(new IndexedParam[]{new IndexedParam<>(id, 0)}));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, id));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.packetData.writeLong(0, id);
        this.id = id;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.KEEP_ALIVE;
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
