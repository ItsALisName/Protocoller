package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.GlobalProvider;
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
        AccessedObject object = new AccessedObject(chatBoundNetwork);
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_1n2)){
            this.chatType = object.read(0, Integer.TYPE);
            this.name = new ChatComponent(ChatSerializer.fromComponent(object.read(0, ClassAccessor.get().getIChatBaseComponentClass())));
            Object tName = object.read(1, ClassAccessor.get().getIChatBaseComponentClass());
            if(tName != null) this.targetName = new ChatComponent(ChatSerializer.fromComponent(tName));
        } else {
            this.chatDecoration = Optional.of(new ChatModifier(((Optional<Object>)object.read(0, Optional.class)).get()));
        }
    }

    //For 1.19.1 and higher
    public ChatBoundNetwork(int chatType, ChatComponent name, @Nullable ChatComponent targetName) {
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
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_1n2)){
            return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getChatBoundNetworkClass(), int.class, ClassAccessor.get().getIChatBaseComponentClass(), ClassAccessor.get().getIChatBaseComponentClass()),
                this.chatType, this.name.asIChatBaseComponent(), this.targetName.asIChatBaseComponent()
            );
        } else {
            return Reflect.callConstructor(
                    Reflect.getConstructor(ClassAccessor.get().getChatBoundNetworkClass(), Optional.class),
                    Optional.of(this.chatDecoration.get().createOriginal())
            );
        }
    }
}
