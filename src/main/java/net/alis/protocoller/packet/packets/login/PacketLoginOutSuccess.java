package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.LoginOutPacket;
import net.alis.protocoller.samples.authlib.GameProfile;
import org.jetbrains.annotations.NotNull;

public class PacketLoginOutSuccess implements LoginOutPacket {

    private final PacketDataContainer packetData;
    private GameProfile gameProfile;

    public PacketLoginOutSuccess(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.gameProfile = packetData.readGameProfile(0);
    }

    public PacketLoginOutSuccess(@NotNull GameProfile gameProfile) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, gameProfile.createOriginal()));
        this.gameProfile = gameProfile;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public void setGameProfile(GameProfile gameProfile) {
        this.packetData.writeGameProfile(0, gameProfile);
        this.gameProfile = gameProfile;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Login.Server.SUCCESS;
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
