package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.LoginInPacket;
import net.alis.protocoller.samples.authlib.GameProfile;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PacketLoginInStart implements LoginInPacket {

    private final PacketDataContainer packetData;
    private GameProfile gameProfile;

    private final PacketBuilder converter = PacketBuilder.get(getPacketType());

    public PacketLoginInStart(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(converter.getConstructorIndicator().getLevel() == 2) {
            this.gameProfile = new GameProfile(((Optional<UUID>) packetData.readOptional(0)).get(), packetData.readString(0));
        } else {
            this.gameProfile = packetData.readGameProfile(0);
        }
    }

    public PacketLoginInStart(GameProfile gameProfile) {
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, gameProfile.createOriginal()));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, gameProfile.getName(), Optional.ofNullable(gameProfile.getId())));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.gameProfile = gameProfile;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public void setGameProfile(GameProfile gameProfile) {
        if(converter.getConstructorIndicator().getLevel() >= 2) {
            this.packetData.writeString(0, gameProfile.getName());
            this.packetData.writeOptional(0, Optional.ofNullable(gameProfile.getId()));
        } else {
            this.packetData.writeGameProfile(0, gameProfile);
        }
        this.gameProfile = gameProfile;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Login.Client.START;
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
