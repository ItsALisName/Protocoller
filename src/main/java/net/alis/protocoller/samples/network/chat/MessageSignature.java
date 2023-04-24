package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.MinecraftEncryption;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import net.alis.protocoller.util.annotations.NotOnAllVersions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class MessageSignature implements ObjectSample {

    private @NotOnAllVersions byte[] bytes; // On 1.19.1 and higher

    private @NotOnAllVersions UUID uuid; // On 1.19 and lower
    private @NotOnAllVersions Instant instant; // On 1.19 and lower
    private @NotOnAllVersions MinecraftEncryption.SignatureData signatureData; // On 1.19 and lower

    //For 1.19.1 and higher
    public MessageSignature(byte @NotNull [] bytes) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.bytes = bytes;
    }

    //For 1.19 and lower
    public MessageSignature(UUID uuid, Instant instant, MinecraftEncryption.SignatureData signatureData) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.uuid = uuid;
        this.instant = instant;
        this.signatureData = signatureData;
    }

    public MessageSignature(Object messageSignature) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject accessor = new AccessedObject(messageSignature);
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_1n2)) {
            this.bytes = accessor.readField(0, byte[].class);
        } else {
            this.uuid = accessor.readField(0, UUID.class);
            this.instant = accessor.readField(0, Instant.class);
            this.signatureData = new MinecraftEncryption.SignatureData(accessor.readField(0, MinecraftEncryption.SignatureData.clazz()));
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public ByteBuffer getByteBuffer() {
        return ByteBuffer.wrap(this.bytes);
    }

    public boolean equals(Object object) {
        if (this != object) {
            if (object instanceof MessageSignature) {
                MessageSignature messageSignature = (MessageSignature)object;
                return Arrays.equals(this.bytes, messageSignature.bytes);
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(this.bytes);
    }

    public String toString() {
        return Base64.getEncoder().encodeToString(this.bytes);
    }

    @Override
    public Object createOriginal() {
        Object response = null;
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_1n2)) {
            response = Reflect.callConstructor(Reflect.getConstructor(clazz(), false, byte[].class), (Object) this.bytes);
        } else {
            response = Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, UUID.class, Instant.class, MinecraftEncryption.SignatureData.clazz()),
                    this.uuid, this.instant, this.signatureData.createOriginal()
            );
        }
        return response;
    }
    
    public static Class<?> clazz() {
        return ClassAccessor.get().getMessageSignatureClass();
    }

    public static class Storage implements ObjectSample {

        private int id;
        private MessageSignature fullSignature;

        public Storage(Object storage) {
            Utils.checkClassSupportability(Storage.clazz(), super.getClass().getSimpleName(), false);
            AccessedObject object = new AccessedObject(storage);
            this.id = object.readField(0, Integer.TYPE);
            this.fullSignature = new MessageSignature((Object) object.readField(0, MessageSignature.clazz()));
        }

        public Storage(int id, @Nullable MessageSignature fullSignature) {
            Utils.checkClassSupportability(Storage.clazz(), super.getClass().getSimpleName(), false);
            this.id = id;
            this.fullSignature = fullSignature;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MessageSignature getFullSignature() {
            return fullSignature;
        }

        public void setFullSignature(MessageSignature fullSignature) {
            this.fullSignature = fullSignature;
        }

        @Override
        public Object createOriginal() {
            return Reflect.callConstructor(
                    Reflect.getConstructor(Storage.clazz(), false, Integer.TYPE, MessageSignature.clazz()),
                    this.id, this.fullSignature.createOriginal()
            );
        }
        
        public static Class<?> clazz() {
            return ClassAccessor.get().getMessageSignatureStorageClass();
        }
    }
}
