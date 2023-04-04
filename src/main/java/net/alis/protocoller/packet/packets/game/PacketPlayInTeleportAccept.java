package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.network.packet.IndexedParam;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInTeleportAccept implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int teleportId;

    public PacketPlayInTeleportAccept(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.teleportId = packetData.readInt(0);
    }

    public PacketPlayInTeleportAccept(int teleportId) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(teleportId, 0)};
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, teleportId));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.teleportId = teleportId;
    }

    public int getTeleportId() {
        return teleportId;
    }

    public void setTeleportId(int teleportId) {
        this.packetData.writeInt(0, teleportId);
        this.teleportId = teleportId;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.TELEPORT_ACCEPT;
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
