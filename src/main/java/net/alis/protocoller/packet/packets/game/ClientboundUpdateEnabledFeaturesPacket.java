package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.resources.MinecraftKey;

import java.util.ArrayList;
import java.util.List;

public class ClientboundUpdateEnabledFeaturesPacket implements PlayOutPacket {

    private PacketDataContainer packetData;

    private List<MinecraftKey> features;

    public ClientboundUpdateEnabledFeaturesPacket(PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.features = new ArrayList<>();
        for(Object key : packetData.readList(0)) {
            this.features.add(new MinecraftKey(key));
        }
    }

    public ClientboundUpdateEnabledFeaturesPacket(List<MinecraftKey> features) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        List<Object> keys = new ArrayList<>();
        for(MinecraftKey key : features) keys.add(key.createOriginal());
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, keys));
        this.features = features;
    }

    public List<MinecraftKey> getFeatures() {
        return features;
    }

    public void setFeatures(List<MinecraftKey> features) {
        this.packetData = new ClientboundUpdateEnabledFeaturesPacket(features).packetData;
        this.features = features;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.UPDATE_ENABLED_FEATURES;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
