package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.entity.player.ProfilePublicKey;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import java.util.UUID;

public class RemoteChatSession implements ObjectSample {

    private UUID sessionId;
    private ProfilePublicKey profilePublicKey;

    public RemoteChatSession(Object remoteChatSession) {
        AccessedObject object = new AccessedObject(remoteChatSession);
        this.sessionId = object.readField(0, UUID.class);
        this.profilePublicKey = new ProfilePublicKey(object.readField(0, ClassAccessor.get().getProfilePublicKeyClass()));
    }

    public RemoteChatSession(UUID sessionId, ProfilePublicKey profilePublicKey) {
        this.sessionId = sessionId;
        this.profilePublicKey = profilePublicKey;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public ProfilePublicKey getProfilePublicKey() {
        return profilePublicKey;
    }

    public void setProfilePublicKey(ProfilePublicKey profilePublicKey) {
        this.profilePublicKey = profilePublicKey;
    }

    public RemoteChatSessionData asData() {
        return new RemoteChatSessionData(this.sessionId, this.profilePublicKey.getData());
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getRemoteChatSessionClass(), false, UUID.class, ClassAccessor.get().getProfilePublicKeyClass()),
                this.sessionId, this.profilePublicKey.createOriginal()
        );
    }
}
