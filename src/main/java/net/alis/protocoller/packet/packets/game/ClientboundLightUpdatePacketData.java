package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.List;

public class ClientboundLightUpdatePacketData implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private BitSet skyYMask;
    private BitSet blockYMask;
    private BitSet emptySkyYMask;
    private BitSet emptyBlockYMask;
    private List<byte[]> skyUpdates;
    private List<byte[]> blockUpdates;
    private boolean trustEdges;

    public ClientboundLightUpdatePacketData(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.skyYMask = packetData.readObject(0, BitSet.class);
        this.blockYMask = packetData.readObject(1, BitSet.class);
        this.emptySkyYMask = packetData.readObject(2, BitSet.class);
        this.emptyBlockYMask = packetData.readObject(3, BitSet.class);
        this.skyUpdates = (List<byte[]>) packetData.readList(0);
        this.blockUpdates = (List<byte[]>) packetData.readList(1);
        this.trustEdges = packetData.readBoolean(0);
    }

    public ClientboundLightUpdatePacketData(@NotNull Object packetData) {
        this.packetData = new PacketDataSerializer(packetData);
        this.skyYMask = this.packetData.readObject(0, BitSet.class);
        this.blockYMask = this.packetData.readObject(1, BitSet.class);
        this.emptySkyYMask = this.packetData.readObject(2, BitSet.class);
        this.emptyBlockYMask = this.packetData.readObject(3, BitSet.class);
        this.skyUpdates = (List<byte[]>) this.packetData.readList(0);
        this.blockUpdates = (List<byte[]>) this.packetData.readList(1);
        this.trustEdges = this.packetData.readBoolean(0);
    }

    public BitSet getSkyYMask() {
        return skyYMask;
    }

    public void setSkyYMask(BitSet skyYMask) {
        this.skyYMask = skyYMask;
        this.packetData.writeObject(0, skyYMask);
    }

    public BitSet getBlockYMask() {
        return blockYMask;
    }

    public void setBlockYMask(BitSet blockYMask) {
        this.packetData.writeObject(1, blockYMask);
        this.blockYMask = blockYMask;
    }

    public BitSet getEmptySkyYMask() {
        return emptySkyYMask;
    }

    public void setEmptySkyYMask(BitSet emptySkyYMask) {
        this.packetData.writeObject(2, emptySkyYMask);
        this.emptySkyYMask = emptySkyYMask;
    }

    public BitSet getEmptyBlockYMask() {
        return emptyBlockYMask;
    }

    public void setEmptyBlockYMask(BitSet emptyBlockYMask) {
        this.packetData.writeObject(3, emptyBlockYMask);
        this.emptyBlockYMask = emptyBlockYMask;
    }

    public List<byte[]> getSkyUpdates() {
        return skyUpdates;
    }

    public void setSkyUpdates(List<byte[]> skyUpdates) {
        this.packetData.writeList(0, skyUpdates);
        this.skyUpdates = skyUpdates;
    }

    public List<byte[]> getBlockUpdates() {
        return blockUpdates;
    }

    public void setBlockUpdates(List<byte[]> blockUpdates) {
        this.packetData.writeList(1, blockUpdates);
        this.blockUpdates = blockUpdates;
    }

    public boolean isTrustEdges() {
        return trustEdges;
    }

    public void setTrustEdges(boolean trustEdges) {
        this.packetData.writeBoolean(0, trustEdges);
        this.trustEdges = trustEdges;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.LIGHT_UPDATE_PACKET_DATA;
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
