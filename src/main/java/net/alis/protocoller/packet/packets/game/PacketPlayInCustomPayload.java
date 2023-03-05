package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.network.MinecraftPacketDataSerializer;
import net.alis.protocoller.parent.resources.MinecraftKey;

public class PacketPlayInCustomPayload implements Packet {

    private final PacketDataContainer packetData;
    private MinecraftKey key;
    private MinecraftPacketDataSerializer data;

    private final boolean veryLegacy = GlobalProvider.instance().getServer().isVeryLegacy();

    public PacketPlayInCustomPayload(PacketDataContainer packetData) {
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
        PacketBuilder creator = PacketBuilder.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
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

    public MinecraftPacketDataSerializer getData() {
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
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
