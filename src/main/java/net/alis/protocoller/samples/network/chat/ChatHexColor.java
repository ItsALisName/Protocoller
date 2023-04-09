package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.Nullable;

public class ChatHexColor implements ObjectSample {

    private int value;
    private @Nullable String name;
    private ChatFormat format;

    public ChatHexColor(Object chatHexColor) {
        AccessedObject object = new AccessedObject(chatHexColor);
        this.value = object.read(0, Integer.TYPE);
        this.name = object.read(0, String.class);
        this.format = ChatFormat.getById(((Enum<?>)object.read(0, ClassAccessor.get().getChatFormatEnum())).ordinal());
    }

    public ChatHexColor(int value, @Nullable String name, ChatFormat format) {
        this.value = value;
        this.name = name;
        this.format = format;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatFormat getFormat() {
        return format;
    }

    public void setFormat(ChatFormat format) {
        this.format = format;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getChatHexColorClass(), int.class, String.class, ClassAccessor.get().getChatFormatEnum()),
                this.value, this.name, this.format.original()
        );
    }
}
