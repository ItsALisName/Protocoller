package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInKeepAlive implements PlayInPacket {

    private final PacketDataContainer packetData;
    private long id;

    public PacketPlayInKeepAlive(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.id = packetData.readLong(0);
    }

    public PacketPlayInKeepAlive(long id) {
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(new IndexedParam[]{new IndexedParam<>(id, 0)}));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, id));
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
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
