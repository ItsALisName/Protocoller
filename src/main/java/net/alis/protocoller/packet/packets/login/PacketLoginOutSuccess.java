package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.authlib.GameProfile;

public class PacketLoginOutSuccess implements Packet {

    private final PacketDataContainer packetData;
    private GameProfile gameProfile;

    public PacketLoginOutSuccess(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.gameProfile = packetData.readGameProfile(0);
    }

    public PacketLoginOutSuccess(GameProfile gameProfile) {
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
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
