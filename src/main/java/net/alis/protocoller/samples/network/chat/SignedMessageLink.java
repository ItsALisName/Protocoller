package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SignedMessageLink implements ObjectSample {

    private int index;
    private UUID sender;
    private UUID sessionId;

    public SignedMessageLink(Object signedMessageLink) {
        AccessedObject object = new AccessedObject(signedMessageLink);
        this.index = object.read(0, int.class);
        this.sender = object.read(0, UUID.class);
        this.sessionId = object.read(1, UUID.class);
    }

    public SignedMessageLink(int index, UUID sender, UUID sessionId) {
        this.index = index;
        this.sender = sender;
        this.sessionId = sessionId;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull SignedMessageLink unsigned(UUID sender) {
        return root(sender, Utils.NIL_UUID);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull SignedMessageLink root(UUID sender, UUID sessionId) {
        return new SignedMessageLink(0, sender, sessionId);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isDescendantOf(@NotNull SignedMessageLink link) {
        return this.index > link.index && this.sender.equals(link.sender) && this.sessionId.equals(link.sessionId);
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassesContainer.get().getSignedMessageLinkClass(), Integer.TYPE, UUID.class, UUID.class),
                this.index, this.sender, this.sessionId
        );
    }


}
