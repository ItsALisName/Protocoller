package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.authlib.GameProfile;

public class PacketLoginOutSuccess implements Packet {

    private final PacketDataSerializer packetData;
    private GameProfile gameProfile;

    public PacketLoginOutSuccess(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.gameProfile = packetData.readGameProfile(0);
    }

    public PacketLoginOutSuccess(GameProfile gameProfile) {
        this.packetData = new PacketDataSerializer(PacketCreator.get(getPacketType()).create(null, gameProfile.createOriginal()));
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
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
