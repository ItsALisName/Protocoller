package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.util.PacketUtils;
import org.jetbrains.annotations.NotNull;

public class ClientboundLevelChunkWithLightPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int x;
    private int z;
    private ClientboundLevelChunkPacketData chunkPacketData;
    private ClientboundLightUpdatePacketData lightUpdatePacketData;

    public ClientboundLevelChunkWithLightPacket(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.x = packetData.readInt(0);
        this.z = packetData.readInt(1);
        this.chunkPacketData = new ClientboundLevelChunkPacketData((Object) packetData.readObject(0, PacketType.Play.Server.LEVEL_CHUNK_PACKET_DATA.getPacketClass()));
        this.lightUpdatePacketData = new ClientboundLightUpdatePacketData((Object) packetData.readObject(0, PacketType.Play.Server.LIGHT_UPDATE_PACKET_DATA.getPacketClass()));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.packetData.writeInt(0, x);
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.packetData.writeInt(1, z);
        this.z = z;
    }

    public ClientboundLevelChunkPacketData getChunkPacketData() {
        return chunkPacketData;
    }

    public void setChunkPacketData(ClientboundLevelChunkPacketData chunkPacketData) {
        this.packetData.writeObject(0, chunkPacketData.getRawPacket());
        this.chunkPacketData = chunkPacketData;
    }

    public ClientboundLightUpdatePacketData getLightUpdatePacketData() {
        return lightUpdatePacketData;
    }

    public void setLightUpdatePacketData(ClientboundLightUpdatePacketData lightUpdatePacketData) {
        this.packetData.writeObject(0, lightUpdatePacketData.getRawPacket());
        this.lightUpdatePacketData = lightUpdatePacketData;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.LEVEL_CHUNK_WITH_LIGHT_PACKET;
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
