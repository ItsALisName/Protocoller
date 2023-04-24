package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;

public class ChatBound implements ObjectSample {

    private MessageTypeModern chatType;
    private ChatComponent name;
    private @Nullable ChatComponent targetName;

    public ChatBound(Object chatBound) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(chatBound);
        this.chatType = new MessageTypeModern(object.readField(0, MessageTypeModern.clazz()));
        this.name = new ChatComponent(ChatSerializer.fromComponent(object.readField(0, ChatComponent.clazz())));
        Object tName = object.readField(1, ChatComponent.clazz());
        if(tName != null) this.targetName = new ChatComponent(ChatSerializer.fromComponent(tName));
    }

    public ChatBound(MessageTypeModern chatType, ChatComponent name, @Nullable ChatComponent targetName) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.chatType = chatType;
        this.name = name;
        this.targetName = targetName;
    }

    public MessageTypeModern getChatType() {
        return chatType;
    }

    public void setChatType(MessageTypeModern chatType) {
        this.chatType = chatType;
    }

    public ChatComponent getName() {
        return name;
    }

    public void setName(ChatComponent name) {
        this.name = name;
    }

    @Nullable
    public ChatComponent getTargetName() {
        return targetName;
    }

    public void setTargetName(@Nullable ChatComponent targetName) {
        this.targetName = targetName;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, MessageTypeModern.clazz(), ChatComponent.clazz(), ChatComponent.clazz()),
                this.chatType.createOriginal(), this.name.asIChatBaseComponent(), this.targetName != null ? this.targetName.asIChatBaseComponent() : null
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getChatBoundClass();
    }

}
