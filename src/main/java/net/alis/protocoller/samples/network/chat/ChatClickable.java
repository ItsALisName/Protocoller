package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import libraries.net.md_5.bungee.api.chat.ClickEvent;
import libraries.net.md_5.bungee.api.chat.Ext;

public class ChatClickable implements ObjectSample {

    private ClickEvent.Action action;
    private String value;

    public ChatClickable(Object chatClickable) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(chatClickable);
        this.action = Ext.Click.getById(((Enum<?>)object.readField(0, ClassAccessor.get().getClickActionEnum())).ordinal());
        this.value = object.readField(0, String.class);
    }

    public ChatClickable(ClickEvent.Action action, String value) {
        this.action = action;
        this.value = value;
    }

    public ClickEvent.Action getAction() {
        return action;
    }

    public void setAction(ClickEvent.Action action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, ClassAccessor.get().getClickActionEnum(), String.class),
                Ext.Click.original(this.action), this.value
        );
    }
    
    public static Class<?> clazz() {
        return ClassAccessor.get().getChatClickableClass();
    }
}
