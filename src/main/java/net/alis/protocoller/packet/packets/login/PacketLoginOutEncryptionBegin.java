package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.bukkit.exceptions.CryptographyException;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.MinecraftEncryption;

import java.security.PublicKey;

public class PacketLoginOutEncryptionBegin implements Packet {

    private final PacketDataSerializer packetData;
    private String serverId;
    private byte[] publicKey;
    private byte[] nonce;
    private final PacketCreator converter = PacketCreator.get(getPacketType());

    public PacketLoginOutEncryptionBegin(PacketDataSerializer packetData) {
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

    public PacketLoginOutEncryptionBegin(String serverId, byte[] publicKey, byte[] nonce) throws CryptographyException {
        switch (converter.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(converter.create(null, serverId, MinecraftEncryption.getPublicKey(publicKey), nonce));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.create(null, serverId, publicKey, nonce));
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
                this.packetData = new PacketDataSerializer(converter.create(null, serverId, publicKey, nonce));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.create(null, serverId, publicKey.getEncoded(), nonce));
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

    public void setPublicKey(byte[] publicKey) throws CryptographyException {
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
    public PacketDataSerializer getPacketData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
