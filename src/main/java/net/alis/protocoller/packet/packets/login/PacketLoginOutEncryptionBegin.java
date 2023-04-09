package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.LoginOutPacket;
import net.alis.protocoller.samples.MinecraftEncryption;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;

public class PacketLoginOutEncryptionBegin implements LoginOutPacket {

    private final PacketDataContainer packetData;
    private String serverId;
    private byte[] publicKey;
    private byte[] nonce;
    private final PacketBuilder converter = PacketBuilder.get(getPacketType());

    public PacketLoginOutEncryptionBegin(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.serverId = packetData.readString(0);
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.publicKey = packetData.readPublicKey(0).getEncoded();
                this.nonce = packetData.readByteArray(0);
                break;
            }
            case 2: {
                this.publicKey = packetData.readByteArray(0);
                this.nonce = packetData.readByteArray(1);
                break;
            }
        }
    }

    public PacketLoginOutEncryptionBegin(String serverId, byte[] publicKey, byte[] nonce) throws Exception {
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, serverId, MinecraftEncryption.getPublicKey(publicKey), nonce));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, serverId, publicKey, nonce));
                break;
            }
            default: this.packetData = null; break;
        }
        this.serverId = serverId;
        this.publicKey = publicKey;
        this.nonce = nonce;
    }

    public PacketLoginOutEncryptionBegin(String serverId, PublicKey publicKey, byte[] nonce) {
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, serverId, publicKey, nonce));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, serverId, publicKey.getEncoded(), nonce));
                break;
            }
            default: this.packetData = null; break;
        }
        this.serverId = serverId;
        this.publicKey = publicKey.getEncoded();
        this.nonce = nonce;
    }

    public String getServerId() {
        return serverId;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setServerId(String serverId) {
        this.packetData.writeString(0, serverId);
        this.serverId = serverId;
    }

    public void setPublicKey(byte[] publicKey) throws Exception {
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData.writeObject(0, MinecraftEncryption.getPublicKey(publicKey));
                break;
            }
            case 2: {
                this.packetData.writeByteArray(0, publicKey);
                break;
            }
        }
        this.publicKey = publicKey;
    }

    public void setPublicKey(PublicKey key) {
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData.writeObject(0, key);
                break;
            }
            case 2: {
                this.packetData.writeByteArray(0, key.getEncoded());
                break;
            }
        }
        this.publicKey = key.getEncoded();
    }

    public void setNonce(byte[] nonce) {
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: this.packetData.writeByteArray(0, nonce); break;
            case 2: this.packetData.writeByteArray(1, nonce); break;
        }
        this.nonce = nonce;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Login.Server.ENCRYPTION_BEGIN;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
