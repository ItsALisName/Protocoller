package net.alis.protocoller.packet.packets.login;

import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.LoginInPacket;
import net.alis.protocoller.samples.MinecraftEncryption;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import java.security.PublicKey;

public class PacketLoginInEncryptionBegin implements LoginInPacket {

    private final PacketDataContainer packetData;
    private byte[] publicKey;
    private byte[] nonce;

    public PacketLoginInEncryptionBegin(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.publicKey = packetData.readByteArray(0);
        this.nonce = packetData.readByteArray(1);
    }

    public PacketLoginInEncryptionBegin(@NotNull SecretKey secretKey, PublicKey publicKey, byte[] nonce) throws Exception {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        this.publicKey = MinecraftEncryption.readBytes(publicKey, secretKey.getEncoded());
        this.nonce = MinecraftEncryption.readBytes(publicKey, nonce);
        if(converter.getPacketLevel().getLevel() > 0) {
            this.packetData = new PacketDataSerializer(converter.buildPacket(null, secretKey, publicKey, nonce));
        } else {
            IndexedParam<?,?>[] params = {
                new IndexedParam<>(this.publicKey, 0),
                new IndexedParam<>(this.nonce, 1)
            };
            this.packetData = new PacketDataSerializer(converter.buildPacket(params));
        }
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.packetData.writeByteArray(0, publicKey);
        this.publicKey = publicKey;
    }

    public void setPublicKey(@NotNull SecretKey secretKey, PublicKey publicKey) throws Exception {
        this.publicKey = MinecraftEncryption.readBytes(publicKey, secretKey.getEncoded());
        this.packetData.writeByteArray(0, this.publicKey);
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setNonce(byte[] nonce) {
        this.packetData.writeByteArray(1, nonce);
        this.nonce = nonce;
    }

    public void setNonce(PublicKey key, byte[] data) throws Exception {
        this.nonce = MinecraftEncryption.readBytes(key, data);
        this.packetData.writeByteArray(1, this.nonce);
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Login.Client.ENCRYPTION_BEGIN;
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
