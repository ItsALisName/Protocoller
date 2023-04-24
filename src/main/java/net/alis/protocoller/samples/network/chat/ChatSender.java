package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ChatSender implements ObjectSample {

    private UUID uuid;
    private ChatComponent name;
    @Nullable private ChatComponent teamName;

    public ChatSender(Object chatSender) {
        Utils.checkClassSupportability(clazz(), "ChatSender", false);
        AccessedObject object = new AccessedObject(chatSender);
        this.uuid = object.readField(0, UUID.class);
        Object oName = object.readField(0, ChatComponent.clazz());
        Object tName = object.readField(1, ChatComponent.clazz());
        if(oName != null) this.name = new ChatComponent(ChatSerializer.fromComponent(oName));
        if(tName != null) this.teamName = new ChatComponent(ChatSerializer.fromComponent(tName));
    }

    public ChatSender(UUID uuid, ChatComponent name, @Nullable ChatComponent teamName) {
        this.uuid = uuid;
        this.name = name;
        this.teamName = teamName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ChatComponent getName() {
        return name;
    }

    public void setName(ChatComponent name) {
        this.name = name;
    }

    public @Nullable ChatComponent getTeamName() {
        return teamName;
    }

    public void setTeamName(@Nullable ChatComponent teamName) {
        this.teamName = teamName;
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getChatSenderClass();
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, UUID.class, ChatComponent.clazz(), ChatComponent.clazz()),
                this.uuid, name != null ? name.asIChatBaseComponent() : null, teamName != null ? teamName.asIChatBaseComponent() : null
        );
    }
}
