package net.alis.protocoller.samples.entity.player;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class ProfilePublicKeyData implements ObjectSample {

    private Instant expiresAt;
    private PublicKey key;
    private byte[] keySignature;

    public ProfilePublicKeyData(Object profilePublicKeyData) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(profilePublicKeyData);
        this.expiresAt = object.readField(0, Instant.class);
        this.key = object.readField(0, PublicKey.class);
        this.keySignature = object.readField(0, byte[].class);
    }

    public ProfilePublicKeyData(Instant expiresAt, PublicKey key, byte[] keySignature) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.expiresAt = expiresAt;
        this.key = key;
        this.keySignature = keySignature;
    }

    public byte[] signedPayload(@NotNull UUID uuid) {
        byte[] encodedKey = this.key.getEncoded();
        byte[] abyte1 = new byte[24 + encodedKey.length];
        ByteBuffer bytebuffer = ByteBuffer.wrap(abyte1).order(ByteOrder.BIG_ENDIAN);
        bytebuffer.putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits()).putLong(this.expiresAt.toEpochMilli()).put(encodedKey);
        return abyte1;
    }

    public boolean hasExpired() {
        return this.expiresAt.isBefore(Instant.now());
    }

    public boolean hasExpired(Duration duration) {
        return this.expiresAt.plus(duration).isBefore(Instant.now());
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public PublicKey getKey() {
        return key;
    }

    public void setKey(PublicKey key) {
        this.key = key;
    }

    public byte[] getKeySignature() {
        return keySignature;
    }

    public void setKeySignature(byte[] keySignature) {
        this.keySignature = keySignature;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, Instant.class, PublicKey.class, byte[].class),
                this.expiresAt, this.key, this.keySignature
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getProfilePublicKeyDataClass();
    }

}
