package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.Utils;
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
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(signedMessageBody);
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            this.content = object.readField(0, String.class);
        } else {
            this.content = new ChatMessageContent(object.readField(0, ClassAccessor.get().getChatMessageContentClass())).getContent();
        }
        this.timeStamp = object.readField(0, Instant.class);
        this.salt = object.readField(0, Long.TYPE);
        this.lastSeen = new LastSeenMessages(object.readField(0, ClassAccessor.get().getLastSeenMessagesClass()));
    }

    public SignedMessageBody(String content, Instant timeStamp, long salt, LastSeenMessages lastSeen) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
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
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            return Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, String.class, Instant.class, Long.TYPE, LastSeenMessages.clazz()),
                    this.content, this.timeStamp, this.salt, this.lastSeen.createOriginal()
            );
        } else {
            return Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, ChatMessageContent.clazz(), Instant.class, Long.TYPE, LastSeenMessages.clazz()),
                    new ChatMessageContent(this.content, new ChatComponent(this.content)).createOriginal(), this.timeStamp, this.salt, this.lastSeen.createOriginal()
            );
        }
    }

    public static SignedMessageBody unsigned(String content) {
        return new SignedMessageBody(content, Instant.now(), 0L, new LastSeenMessages(new ArrayList<>()));
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getSignedMessageBodyClass();
    }

    public static class Packed implements ObjectSample{

        private String content;
        private Instant timeStamp;
        private long salt;
        private LastSeenMessages.Cache lastSeen;

        public Packed(Object packed) {
            Utils.checkClassSupportability(clazz(), "SignedMessageBody$Packed", false);
            AccessedObject object = new AccessedObject(packed);
            this.content = object.readField(0, String.class);
            this.timeStamp = object.readField(0, Instant.class);
            this.salt = object.readField(0, long.class);
            Object cache = object.readField(0, ClassAccessor.get().getLastSeenMessagesCacheClass());
            if(cache != null) this.lastSeen = new LastSeenMessages.Cache(cache);
        }

        public Packed(String content, Instant timeStamp, long salt, LastSeenMessages.Cache lastSeen) {
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

        public LastSeenMessages.Cache getLastSeen() {
            return lastSeen;
        }

        public void setLastSeen(LastSeenMessages.Cache lastSeen) {
            this.lastSeen = lastSeen;
        }

        @Override
        public Object createOriginal() {
            return Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, String.class, Instant.class, long.class, ClassAccessor.get().getLastSeenMessagesCacheClass()),
                    this.content, this.timeStamp, this.salt, this.lastSeen.createOriginal()
            );
        }

        public static Class<?> clazz() {
            return ClassAccessor.get().getSignedMessageBodyPackedClass();
        }
    }
}
