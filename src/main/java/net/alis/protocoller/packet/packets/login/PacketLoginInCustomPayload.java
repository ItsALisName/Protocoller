package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.LoginInPacket;
import net.alis.protocoller.samples.network.MinecraftPacketDataSerializer;
import org.jetbrains.annotations.NotNull;

public class PacketLoginInCustomPayload implements LoginInPacket {

    private final PacketDataContainer packetData;
    private int queryId;
    private MinecraftPacketDataSerializer originalSerializer;

    public PacketLoginInCustomPayload(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.queryId = packetData.readInt(0);
        this.originalSerializer = packetData.readOriginalDataSerializer(0);
    }

    public PacketLoginInCustomPayload(int queryId, MinecraftPacketDataSerializer originalSerializer) {
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(queryId, 0), new IndexedParam<>(originalSerializer.createOriginal(), 0)};
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, queryId, originalSerializer.createOriginal()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.queryId = queryId;
        this.originalSerializer = originalSerializer;
    }

    public PacketLoginInCustomPayload(@NotNull MinecraftPacketDataSerializer serializer) {
        this.queryId = serializer.readVarIntFromBuffer();
        this.originalSerializer = serializer.read$0((buffer) -> {
            int i = buffer.readableBytes();
            if (i >= 0 && i <= 1048576) {
                return new MinecraftPacketDataSerializer(buffer.readBytes(i));
            } else {
                throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
            }
        });
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?, ?>[] params = {new IndexedParam<>(this.queryId, 0), new IndexedParam<>(serializer.createOriginal(), 0)};
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, this.queryId, serializer.createOriginal()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.packetData.writeInt(0, queryId);
        this.queryId = queryId;
    }

    public MinecraftPacketDataSerializer getOriginalSerializer() {
        return originalSerializer;
    }

    public void setOriginalSerializer(MinecraftPacketDataSerializer originalSerializer) {
        this.packetData.writeOriginalDataSerializer(0, originalSerializer);
        this.originalSerializer = originalSerializer;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Login.Client.CUSTOM_PAYLOAD;
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
