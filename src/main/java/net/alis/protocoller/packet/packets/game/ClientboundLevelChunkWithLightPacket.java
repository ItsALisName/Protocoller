package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.v0_0_5.server.level.chunk.ProtocolChunk;
import net.alis.protocoller.samples.server.world.level.chunk.IChunk;
import net.alis.protocoller.samples.server.world.level.lighting.ILightEngine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;

public class ClientboundLevelChunkWithLightPacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int x;
    private int z;
    private ClientboundLevelChunkPacketData chunkPacketData;
    private ClientboundLightUpdatePacketData lightUpdatePacketData;

    public ClientboundLevelChunkWithLightPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.x = packetData.readInt(0);
        this.z = packetData.readInt(1);
        this.chunkPacketData = new ClientboundLevelChunkPacketData((Object) packetData.readObject(0, PacketType.Play.Server.LEVEL_CHUNK_PACKET_DATA.getPacketClass()));
        this.lightUpdatePacketData = new ClientboundLightUpdatePacketData((Object) packetData.readObject(0, PacketType.Play.Server.LIGHT_UPDATE_PACKET_DATA.getPacketClass()));
    }

    public ClientboundLevelChunkWithLightPacket(@NotNull IChunk chunk, @NotNull ILightEngine lightProvider, @Nullable BitSet skyBits, @Nullable BitSet blockBits, boolean nonEdge) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, ((ProtocolChunk)chunk).getHandle().getOriginal(), lightProvider.original(), skyBits, blockBits, nonEdge));
        this.x = chunk.getCoordinates().getX();
        this.z = chunk.getCoordinates().getZ();
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
