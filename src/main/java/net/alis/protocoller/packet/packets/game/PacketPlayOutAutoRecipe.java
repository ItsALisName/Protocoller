package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.resources.MinecraftKey;
import org.jetbrains.annotations.NotNull;

public class PacketPlayOutAutoRecipe implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private int syncId;
    private MinecraftKey key;

    public PacketPlayOutAutoRecipe(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.syncId = packetData.readInt(0);
        this.key = packetData.readMinecraftKey(0);
    }

    public PacketPlayOutAutoRecipe(int syncId, @NotNull MinecraftKey key) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, syncId, key.createOriginal()));
        this.syncId = syncId;
        this.key = key;
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.packetData.writeInt(0, syncId);
        this.syncId = syncId;
    }

    public MinecraftKey getKey() {
        return key;
    }

    public void setKey(MinecraftKey key) {
        this.packetData.writeMinecraftKey(0, key);
        this.key = key;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.AUTO_RECIPE;
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
