package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.network.MinecraftPacketDataSerializer;
import net.alis.protocoller.samples.resources.MinecraftKey;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInCustomPayload implements PlayInPacket {

    private final PacketDataContainer packetData;
    private MinecraftKey key;
    private MinecraftPacketDataSerializer data;

    private final boolean veryLegacy = IProtocolAccess.get().getServer().isVeryLegacy();

    public PacketPlayInCustomPayload(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(!veryLegacy){
            this.key = packetData.readMinecraftKey(0);
        } else {
            String stringKey = packetData.readString(0);
            this.key = new MinecraftKey(stringKey.split("\\|")[0].toLowerCase(), stringKey.split("\\|")[1].toLowerCase());
        }
        this.data = packetData.readOriginalDataSerializer(0);
    }

    public PacketPlayInCustomPayload(MinecraftKey key, MinecraftPacketDataSerializer data) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getPacketLevel().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                        new IndexedParam<>(key.toLegacyString(), 0),
                        new IndexedParam<>(data.createOriginal(), 0)
                };
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, key.createOriginal(), data.createOriginal()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.key = key;
        this.data = data;
    }

    public MinecraftKey getKey() {
        return key;
    }

    public void setKey(MinecraftKey key) {
        if(!veryLegacy) {
            this.packetData.writeMinecraftKey(0, key);
        } else {
            this.packetData.writeString(0, key.toLegacyString());
        }
        this.key = key;
    }

    public MinecraftPacketDataSerializer getMinecraftData() {
        return data;
    }

    public void setData(MinecraftPacketDataSerializer data) {
        this.packetData.writeOriginalDataSerializer(0, data);
        this.data = data;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.CUSTOM_PAYLOAD;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.getData().getRawPacket();
    }
}
