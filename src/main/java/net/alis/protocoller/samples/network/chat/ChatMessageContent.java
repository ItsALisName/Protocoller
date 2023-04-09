package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

public class ChatMessageContent implements ObjectSample {

    private String content;
    private ChatComponent component$content;

    public ChatMessageContent(Object chatMessageContent) {
        AccessedObject object = new AccessedObject(chatMessageContent);
        this.content = object.read(0, String.class);
        this.component$content = new ChatComponent(ChatSerializer.fromComponent(object.read(0, ClassAccessor.get().getIChatBaseComponentClass())));
    }

    public ChatMessageContent(String content, ChatComponent component$content) {
        this.content = content;
        this.component$content = component$content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatComponent getComponentContent() {
        return component$content;
    }

    public void setComponentContent(ChatComponent component$content) {
        this.component$content = component$content;
    }


    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getChatMessageContentClass(), String.class, ClassAccessor.get().getIChatBaseComponentClass()),
                this.content, this.component$content.asIChatBaseComponent()
        );
    }
}
