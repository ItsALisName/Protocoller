package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.core.levelgen.ChunkBiomeData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClientboundChunksBiomesPacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private List<ChunkBiomeData> chunkBiomeData;

    public ClientboundChunksBiomesPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.chunkBiomeData = new ArrayList<>();
        for(Object cbd : packetData.readList(0)) {
            this.chunkBiomeData.add(new ChunkBiomeData(cbd));
        }
    }

    public ClientboundChunksBiomesPacket(@NotNull List<ChunkBiomeData> chunkBiomeData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        List<Object> originalCBD = new ArrayList<>();
        for(ChunkBiomeData data : chunkBiomeData) originalCBD.add(data.createOriginal());
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, originalCBD));
        this.chunkBiomeData = chunkBiomeData;
    }

    public List<ChunkBiomeData> getChunkBiomeData() {
        return chunkBiomeData;
    }

    public void setChunkBiomeData(List<ChunkBiomeData> chunkBiomeData) {
        this.packetData = new ClientboundChunksBiomesPacket(chunkBiomeData).packetData;
        this.chunkBiomeData = chunkBiomeData;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.CHUNK_BIOMES_PACKET;
    }
}
