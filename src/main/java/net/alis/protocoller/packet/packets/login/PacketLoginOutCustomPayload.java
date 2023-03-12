package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.LoginOutPacket;
import net.alis.protocoller.samples.network.MinecraftPacketDataSerializer;
import net.alis.protocoller.samples.resources.MinecraftKey;
import org.jetbrains.annotations.NotNull;

public class PacketLoginOutCustomPayload implements LoginOutPacket {

    private final PacketDataContainer packetData;
    private int queryId;
    private MinecraftKey channel;
    private MinecraftPacketDataSerializer payload;

    public PacketLoginOutCustomPayload(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.channel = packetData.readMinecraftKey(0);
        this.payload = packetData.readOriginalDataSerializer(0);
    }

    public PacketLoginOutCustomPayload(int queryId, MinecraftKey channel, MinecraftPacketDataSerializer payload) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                    new IndexedParam<>(queryId, 0), new IndexedParam<>(channel.createOriginal(), 0), new IndexedParam<>(payload.createOriginal(), 0)
                };
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, queryId, channel.createOriginal(), payload.createOriginal()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.queryId = queryId;
        this.channel = channel;
        this.payload = payload;
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.packetData.writeInt(0, queryId);
        this.queryId = queryId;
    }

    public MinecraftKey getChannel() {
        return channel;
    }

    public void setChannel(MinecraftKey channel) {
        this.packetData.writeMinecraftKey(0, channel);
        this.channel = channel;
    }

    public MinecraftPacketDataSerializer getPayload() {
        return payload;
    }

    public void setPayload(MinecraftPacketDataSerializer payload) {
        this.packetData.writeOriginalDataSerializer(0, payload);
        this.payload = payload;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Login.Server.CUSTOM_PAYLOAD;
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
