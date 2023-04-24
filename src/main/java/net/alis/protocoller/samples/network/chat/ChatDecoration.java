package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatDecoration implements ObjectSample {

    private String translationKey;
    private List<Parameter> parameters;
    private ChatModifier style;

    public ChatDecoration(Object chatDecoration) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(chatDecoration);
        this.translationKey = object.readField(0, String.class);
        this.parameters = new ArrayList<>();
        List<Enum<?>> params = object.readField(0, List.class);
        if(params != null && params.size() > 0) {
            for(Enum<?> e : params) this.parameters.add(Parameter.getById(e.ordinal()));
        }
        this.style = new ChatModifier(object.readField(0, ChatModifier.clazz()));
    }

    public ChatDecoration(String translationKey, List<Parameter> parameters, ChatModifier style) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.translationKey = translationKey;
        this.parameters = parameters;
        this.style = style;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public ChatModifier getStyle() {
        return style;
    }

    public void setStyle(ChatModifier style) {
        this.style = style;
    }

    @Override
    public Object createOriginal() {
        List<Enum<?>> orig = new ArrayList<>();
        for(Parameter p : this.parameters) orig.add(p.original());
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, String.class, List.class, ChatModifier.clazz()),
                this.translationKey, orig, this.style.createOriginal()
        );
    }

    public static ChatDecoration withSender(String key) {
        return new ChatDecoration(key, List.of(Parameter.SENDER, Parameter.CONTENT), ChatModifier.EMPTY);
    }

    public static ChatDecoration incomingDirectMessage(String key) {
        ChatModifier style = ChatModifier.EMPTY.setColor(ChatHexColor.fromLegacyFormat(ChatFormat.GRAY)).setItalic(true);
        return new ChatDecoration(key, List.of(Parameter.SENDER, Parameter.CONTENT), style);
    }

    public static ChatDecoration outgoingDirectMessage(String p_240772_) {
        ChatModifier style = ChatModifier.EMPTY.setColor(ChatHexColor.fromLegacyFormat(ChatFormat.GRAY)).setItalic(true);
        return new ChatDecoration(p_240772_, List.of(Parameter.TARGET, Parameter.CONTENT), style);
    }

    public static ChatDecoration teamMessage(String key) {
        return new ChatDecoration(key, List.of(Parameter.TARGET, Parameter.SENDER, Parameter.CONTENT), ChatModifier.EMPTY);
    }

    public enum Parameter {

        SENDER, TARGET, CONTENT;

        @Nullable
        public static Parameter getById(int id) {
            for(Parameter p : values()) if(p.ordinal() == id) return p;
            return null;
        }

        public Enum<?> original() {
            Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
            return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.ordinal());
        }

        public static Class<?> clazz() {
            return ClassAccessor.get().getChatDecorationParameterEnum();
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getChatDecorationClass();
    }

}
