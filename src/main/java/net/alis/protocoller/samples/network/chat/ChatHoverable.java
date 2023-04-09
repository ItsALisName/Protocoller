package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import net.md_5.bungee.api.chat.Ext;
import net.md_5.bungee.api.chat.HoverEvent;

import java.lang.reflect.Constructor;

public class ChatHoverable implements ObjectSample {

    private HoverEvent.Action action;
    private Object value;

    public ChatHoverable(Object original) {
        AccessedObject object = new AccessedObject(original);
        this.action = Ext.Hover.fromOriginal(object.read(0, ClassAccessor.get().getChatHoverActionEnumClass()));
        this.value = object.read(1);
    }

    public ChatHoverable(HoverEvent.Action action, Object value) {
        this.action = action;
        this.value = value;
    }

    public HoverEvent.Action getAction() {
        return action;
    }

    public void setAction(HoverEvent.Action action) {
        this.action = action;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object createOriginal() {
        Constructor<?> constructor = ClassAccessor.get().getChatHoverableClass().getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        return Reflect.callConstructor(constructor, Ext.Hover.original(this.action), this.value);
    }
}

