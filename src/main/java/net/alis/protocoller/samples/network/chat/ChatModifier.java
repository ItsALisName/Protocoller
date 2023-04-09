package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;

public class ChatModifier implements ObjectSample {

    public static final MinecraftKey DEFAULT_FONT = new MinecraftKey("minecraft", "default");
    @Nullable
    private ChatHexColor color;
    @Nullable
    private Boolean bold;
    @Nullable
    private Boolean italic;
    @Nullable
    private Boolean underlined;
    @Nullable
    private Boolean strikethrough;
    @Nullable
    private Boolean obfuscated;
    @Nullable
    private ChatClickable clickEvent;
    @Nullable
    private ChatHoverable hoverEvent;
    @Nullable
    private String insertion;
    @Nullable
    private MinecraftKey font;

    public ChatModifier(Object chatModifier) {
        AccessedObject object = new AccessedObject(chatModifier, true);
        this.color = new ChatHexColor(object.read(0, ClassAccessor.get().getChatHexColorClass()));
        this.bold = object.read(0, Boolean.TYPE);
        this.italic = object.read(1, Boolean.TYPE);
        this.underlined = object.read(2, Boolean.TYPE);
        this.strikethrough = object.read(3, Boolean.TYPE);
        this.obfuscated = object.read(4, Boolean.TYPE);
        this.clickEvent = new ChatClickable(object.read(0, ClassAccessor.get().getChatClickableClass()));
        this.hoverEvent = new ChatHoverable(object.read(0, ClassAccessor.get().getChatHoverableClass()));
        this.insertion = object.read(0, String.class);
        this.font = new MinecraftKey((Object) object.read(0, ClassAccessor.get().getMinecraftKeyClass()));
    }

    public ChatModifier(@Nullable ChatHexColor color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ChatClickable clickEvent, @Nullable ChatHoverable hoverEvent, @Nullable String insertion, @Nullable MinecraftKey font) {
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.insertion = insertion;
        this.font = font;
    }

    @Nullable
    public ChatHexColor getColor() {
        return color;
    }

    public void setColor(@Nullable ChatHexColor color) {
        this.color = color;
    }

    @Nullable
    public Boolean getBold() {
        return bold;
    }

    public void setBold(@Nullable Boolean bold) {
        this.bold = bold;
    }

    @Nullable
    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(@Nullable Boolean italic) {
        this.italic = italic;
    }

    @Nullable
    public Boolean getUnderlined() {
        return underlined;
    }

    public void setUnderlined(@Nullable Boolean underlined) {
        this.underlined = underlined;
    }

    @Nullable
    public Boolean getStrikethrough() {
        return strikethrough;
    }

    public void setStrikethrough(@Nullable Boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    @Nullable
    public Boolean getObfuscated() {
        return obfuscated;
    }

    public void setObfuscated(@Nullable Boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    @Nullable
    public ChatClickable getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(@Nullable ChatClickable clickEvent) {
        this.clickEvent = clickEvent;
    }

    @Nullable
    public ChatHoverable getHoverEvent() {
        return hoverEvent;
    }

    public void setHoverEvent(@Nullable ChatHoverable hoverEvent) {
        this.hoverEvent = hoverEvent;
    }

    @Nullable
    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(@Nullable String insertion) {
        this.insertion = insertion;
    }

    @Nullable
    public MinecraftKey getFont() {
        return font;
    }

    public void setFont(@Nullable MinecraftKey font) {
        this.font = font;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getChatModifierClass(), ClassAccessor.get().getChatHexColorClass(), Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, ClassAccessor.get().getChatClickableClass(), ClassAccessor.get().getChatHoverableClass(), String.class, ClassAccessor.get().getMinecraftKeyClass()),
                this.color.createOriginal(), this.bold, this.italic,this.underlined,this.strikethrough, this.obfuscated, this.clickEvent.createOriginal(), this.hoverEvent.createOriginal(), this.insertion, this.font.createOriginal()
        );
    }
}
