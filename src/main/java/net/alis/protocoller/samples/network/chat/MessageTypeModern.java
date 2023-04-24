package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ApproximateData;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import java.lang.reflect.Field;

public class MessageTypeModern implements ObjectSample {

    private ChatDecoration chat;
    private ChatDecoration narration;

    public MessageTypeModern(ChatDecoration chat, ChatDecoration narration) {
        this.chat = chat;
        this.narration = narration;
    }

    public MessageTypeModern(Object chatMessageType) {
        AccessedObject object = new AccessedObject(chatMessageType);
        this.chat = new ChatDecoration(object.readField(0, ChatDecoration.clazz()));
        this.narration = new ChatDecoration(object.readField(1, ChatDecoration.clazz()));
    }

    public ChatDecoration getChat() {
        return chat;
    }

    public void setChat(ChatDecoration chat) {
        this.chat = chat;
    }

    public ChatDecoration getNarration() {
        return narration;
    }

    public void setNarration(ChatDecoration narration) {
        this.narration = narration;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, ChatDecoration.clazz(), ChatDecoration.clazz()),
                this.chat.createOriginal(), this.narration.createOriginal()
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getChatMessageTypeEnum();
    }

    public static Object CHAT,
            SAY_COMMAND,
            MSG_COMMAND_INCOMING,
            MSG_COMMAND_OUTGOING,
            TEAM_MSG_COMMAND_INCOMING,
            TEAM_MSG_COMMAND_OUTGOING,
            EMOTE_COMMAND,
            RAW;

    public static void init() {
        if(ApproximateData.get().getPreVersion().greaterThanOrEqualTo(Version.v1_19)){
            CHAT = Reflect.readField(null, Reflect.getField(ClassAccessor.get().getChatMessageTypeEnum(), "c", false), true);
            SAY_COMMAND = Reflect.readField(null, Reflect.getField(ClassAccessor.get().getChatMessageTypeEnum(), "d", false), true);
            MSG_COMMAND_INCOMING = Reflect.readField(null, Reflect.getField(ClassAccessor.get().getChatMessageTypeEnum(), "e", false), true);
            MSG_COMMAND_OUTGOING = Reflect.readField(null, Reflect.getField(ClassAccessor.get().getChatMessageTypeEnum(), "f", false), true);
            TEAM_MSG_COMMAND_INCOMING = Reflect.readField(null, Reflect.getField(ClassAccessor.get().getChatMessageTypeEnum(), "g", false), true);
            TEAM_MSG_COMMAND_OUTGOING = Reflect.readField(null, Reflect.getField(ClassAccessor.get().getChatMessageTypeEnum(), "h", true), true);
            EMOTE_COMMAND = Reflect.readField(null, Reflect.getField(ClassAccessor.get().getChatMessageTypeEnum(), "i", true), true);
            Field rawType = Reflect.getField(ClassAccessor.get().getChatMessageTypeEnum(), "RAW", true);
            if (rawType != null) RAW = Reflect.readField(null, rawType, true);
        }
    }

}
