package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;
import java.util.Optional;

public class ChatBoundNetwork implements ObjectSample {

    private Optional<ChatModifier> chatDecoration; //On 1.19
    private int chatType; //On 1.19.1 and higher
    private ChatComponent name; //On 1.19.1 and higher
    private @Nullable ChatComponent targetName; //On 1.19.1 and higher

    public ChatBoundNetwork(Object chatBoundNetwork) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(chatBoundNetwork);
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_1n2)){
            this.chatType = object.readField(0, Integer.TYPE);
            this.name = new ChatComponent(ChatSerializer.fromComponent(object.readField(0, ChatComponent.clazz())));
            Object tName = object.readField(1, ChatComponent.clazz());
            if(tName != null) this.targetName = new ChatComponent(ChatSerializer.fromComponent(tName));
        } else {
            this.chatDecoration = Optional.of(new ChatModifier(((Optional<Object>)object.readField(0, Optional.class)).get()));
        }
    }

    //For 1.19.1 and higher
    public ChatBoundNetwork(int chatType, ChatComponent name, @Nullable ChatComponent targetName) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.chatType = chatType;
        this.name = name;
        this.targetName = targetName;
    }

    // For 1.19
    public ChatBoundNetwork(Optional<ChatModifier> chatDecoration) {
        this.chatDecoration = chatDecoration;
    }

    public Optional<ChatModifier> getChatDecoration() {
        return chatDecoration;
    }

    public void setChatDecoration(Optional<ChatModifier> chatDecoration) {
        this.chatDecoration = chatDecoration;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
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
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_1n2)){
            return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, int.class, ChatComponent.clazz(), ChatComponent.clazz()),
                this.chatType, this.name.asIChatBaseComponent(), this.targetName != null ? this.targetName.asIChatBaseComponent() : null
            );
        } else {
            return Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, Optional.class),
                    Optional.of(this.chatDecoration.get().createOriginal())
            );
        }
    }
    
    public static Class<?> clazz() {
        return ClassAccessor.get().getChatBoundNetworkClass();
    }
}
