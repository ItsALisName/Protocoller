package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class ChatHexColor implements ObjectSample {

    private int value;
    private @Nullable String name;
    private ChatFormat format;

    public ChatHexColor(Object chatHexColor) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(chatHexColor);
        this.value = object.readField(0, Integer.TYPE);
        this.name = object.readField(0, String.class);
        this.format = ChatFormat.getById(((Enum<?>)object.readField(0, ChatFormat.clazz())).ordinal());
    }

    public ChatHexColor(int value, @Nullable String name, ChatFormat format) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
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

    @Contract("_ -> new")
    public static @NotNull ChatHexColor fromLegacyFormat(@NotNull ChatFormat format) {
        return new ChatHexColor(Reflect.callMethod(null, fromLegacyFormatMethod, false, format.original()));
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, int.class, String.class, ChatFormat.clazz()),
                this.value, this.name, this.format.original()
        );
    }
    
    public static Class<?> clazz() {
        return ClassAccessor.get().getChatHexColorClass();
    }

    private static Method fromLegacyFormatMethod;
    public static void init() {
        if(clazz() != null){
            fromLegacyFormatMethod = Reflect.getMethod(clazz(), clazz(), true, ChatFormat.clazz());
        }
    }
    
}
