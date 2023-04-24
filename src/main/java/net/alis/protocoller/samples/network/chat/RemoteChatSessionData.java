package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.entity.player.ProfilePublicKeyData;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import java.util.UUID;

public class RemoteChatSessionData implements ObjectSample {

    private UUID sessionId;
    private ProfilePublicKeyData profilePublicKey;

    public RemoteChatSessionData(Object remoteChatSessionData) {
        AccessedObject object = new AccessedObject(remoteChatSessionData);
        this.sessionId = object.readField(0, UUID.class);
        this.profilePublicKey = new ProfilePublicKeyData(object.readField(0, ClassAccessor.get().getProfilePublicKeyDataClass()));
    }

    public RemoteChatSessionData(UUID sessionId, ProfilePublicKeyData profilePublicKey) {
        this.sessionId = sessionId;
        this.profilePublicKey = profilePublicKey;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public ProfilePublicKeyData getProfilePublicKey() {
        return profilePublicKey;
    }

    public void setProfilePublicKey(ProfilePublicKeyData profilePublicKey) {
        this.profilePublicKey = profilePublicKey;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getRemoteChatSessionDataClass(), false, UUID.class, ClassAccessor.get().getProfilePublicKeyDataClass()),
                this.sessionId, this.profilePublicKey.createOriginal()
        );
    }
}
