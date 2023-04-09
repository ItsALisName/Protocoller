package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import java.time.Instant;
import java.util.ArrayList;

public class SignedMessageBody implements ObjectSample {

    private String content;
    private Instant timeStamp;
    private long salt;
    private LastSeenMessages lastSeen;

    public SignedMessageBody(Object signedMessageBody) {
        AccessedObject object = new AccessedObject(signedMessageBody);
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            this.content = object.read(0, String.class);
        } else {
            this.content = new ChatMessageContent(object.read(0, ClassAccessor.get().getChatMessageContentClass())).getContent();
        }
        this.timeStamp = object.read(0, Instant.class);
        this.salt = object.read(0, Long.TYPE);
        this.lastSeen = new LastSeenMessages(object.read(0, ClassAccessor.get().getLastSeenMessagesClass()));
    }

    public SignedMessageBody(String content, Instant timeStamp, long salt, LastSeenMessages lastSeen) {
        this.content = content;
        this.timeStamp = timeStamp;
        this.salt = salt;
        this.lastSeen = lastSeen;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public LastSeenMessages getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LastSeenMessages lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public Object createOriginal() {
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            return Reflect.callConstructor(
                    Reflect.getConstructor(ClassAccessor.get().getSignedMessageBodyClass(), String.class, Instant.class, Long.TYPE, ClassAccessor.get().getLastSeenMessagesClass()),
                    this.content, this.timeStamp, this.salt, this.lastSeen.createOriginal()
            );
        } else {
            return Reflect.callConstructor(
                    Reflect.getConstructor(ClassAccessor.get().getSignedMessageBodyClass(), ClassAccessor.get().getChatMessageContentClass(), Instant.class, Long.TYPE, ClassAccessor.get().getLastSeenMessagesClass()),
                    new ChatMessageContent(this.content, new ChatComponent(this.content)).createOriginal(), this.timeStamp, this.salt, this.lastSeen.createOriginal()
            );
        }
    }

    public static SignedMessageBody unsigned(String content) {
        return new SignedMessageBody(content, Instant.now(), 0L, new LastSeenMessages(new ArrayList<>()));
    }
}
