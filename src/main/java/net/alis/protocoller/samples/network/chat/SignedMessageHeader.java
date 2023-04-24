package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SignedMessageHeader implements ObjectSample {

    private @Nullable MessageSignature messageSignature;
    private UUID uuid;

    public SignedMessageHeader(@Nullable MessageSignature messageSignature, UUID uuid) {
        Utils.checkClassSupportability(clazz(), "SignedMessageHeader", false);
        this.messageSignature = messageSignature;
        this.uuid = uuid;
    }

    public SignedMessageHeader(Object signedMessageHeader) {
        Utils.checkClassSupportability(clazz(), "SignedMessageHeader", false);
        AccessedObject object = new AccessedObject(signedMessageHeader);
        Object msgSign = object.readField(0, MessageSignature.clazz());
        if(msgSign != null) this.messageSignature = new MessageSignature(msgSign);
        this.uuid = object.readField(0, UUID.class);
    }

    public MessageSignature getMessageSignature() {
        return messageSignature;
    }

    public void setMessageSignature(MessageSignature messageSignature) {
        this.messageSignature = messageSignature;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), true, MessageSignature.clazz(), UUID.class),
                this.messageSignature != null ? messageSignature.createOriginal() : null, this.uuid
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getSignedMessageHeaderClass();
    }
}
