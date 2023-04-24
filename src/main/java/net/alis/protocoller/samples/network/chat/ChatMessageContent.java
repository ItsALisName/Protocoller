package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

public class ChatMessageContent implements ObjectSample {

    private String content;
    private ChatComponent component$content;

    public ChatMessageContent(Object chatMessageContent) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(chatMessageContent);
        this.content = object.readField(0, String.class);
        this.component$content = new ChatComponent(ChatSerializer.fromComponent(object.readField(0, ChatComponent.clazz())));
    }

    public ChatMessageContent(String content, ChatComponent component$content) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
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
                Reflect.getConstructor(clazz(), false, String.class, ChatComponent.clazz()),
                this.content, this.component$content.asIChatBaseComponent()
        );
    }
    
    public static Class<?> clazz() {
        return ClassAccessor.get().getChatMessageContentClass();
    }
}
