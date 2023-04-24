package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;

public class ChatModifier implements ObjectSample {

    public static final MinecraftKey DEFAULT_FONT = new MinecraftKey("minecraft", "default");
    public static final ChatModifier EMPTY = new ChatModifier(null, null, null, null, null, null, null, null, null, null);
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
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(chatModifier, true);
        this.color = new ChatHexColor(object.readField(0, ChatHexColor.clazz()));
        this.bold = object.readField(0, Boolean.TYPE);
        this.italic = object.readField(1, Boolean.TYPE);
        this.underlined = object.readField(2, Boolean.TYPE);
        this.strikethrough = object.readField(3, Boolean.TYPE);
        this.obfuscated = object.readField(4, Boolean.TYPE);
        this.clickEvent = new ChatClickable(object.readField(0, ChatClickable.clazz()));
        this.hoverEvent = new ChatHoverable(object.readField(0, ChatHoverable.clazz()));
        this.insertion = object.readField(0, String.class);
        this.font = new MinecraftKey((Object) object.readField(0, MinecraftKey.clazz()));
    }

    public ChatModifier(@Nullable ChatHexColor color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ChatClickable clickEvent, @Nullable ChatHoverable hoverEvent, @Nullable String insertion, @Nullable MinecraftKey font) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
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

    public ChatModifier setColor(@Nullable ChatHexColor color) {
        this.color = color;
        return this;
    }

    @Nullable
    public Boolean getBold() {
        return bold;
    }

    public ChatModifier setBold(@Nullable Boolean bold) {
        this.bold = bold;
        return this;
    }

    @Nullable
    public Boolean getItalic() {
        return italic;
    }

    public ChatModifier setItalic(@Nullable Boolean italic) {
        this.italic = italic;
        return this;
    }

    @Nullable
    public Boolean getUnderlined() {
        return underlined;
    }

    public ChatModifier setUnderlined(@Nullable Boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    @Nullable
    public Boolean getStrikethrough() {
        return strikethrough;
    }

    public ChatModifier setStrikethrough(@Nullable Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    @Nullable
    public Boolean getObfuscated() {
        return obfuscated;
    }

    public ChatModifier setObfuscated(@Nullable Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    @Nullable
    public ChatClickable getClickEvent() {
        return clickEvent;
    }

    public ChatModifier setClickEvent(@Nullable ChatClickable clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    @Nullable
    public ChatHoverable getHoverEvent() {
        return hoverEvent;
    }

    public ChatModifier setHoverEvent(@Nullable ChatHoverable hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }

    @Nullable
    public String getInsertion() {
        return insertion;
    }

    public ChatModifier setInsertion(@Nullable String insertion) {
        this.insertion = insertion;
        return this;
    }

    @Nullable
    public MinecraftKey getFont() {
        return font;
    }

    public ChatModifier setFont(@Nullable MinecraftKey font) {
        this.font = font;
        return this;
    }

    public ChatModifier applyTo(ChatModifier modifier) {
        if (this == EMPTY) {
            return modifier;
        } else {
            return modifier == EMPTY ? this : new ChatModifier(this.color != null ? this.color : modifier.color, this.bold != null ? this.bold : modifier.bold, this.italic != null ? this.italic : modifier.italic, this.underlined != null ? this.underlined : modifier.underlined, this.strikethrough != null ? this.strikethrough : modifier.strikethrough, this.obfuscated != null ? this.obfuscated : modifier.obfuscated, this.clickEvent != null ? this.clickEvent : modifier.clickEvent, this.hoverEvent != null ? this.hoverEvent : modifier.hoverEvent, this.insertion != null ? this.insertion : modifier.insertion, this.font != null ? this.font : modifier.font);
        }
    }

    public ChatModifier applyLegacyFormat(ChatFormat format) {
        ChatHexColor textcolor = this.color;
        Boolean b = this.bold;
        Boolean b1 = this.italic;
        Boolean b2 = this.strikethrough;
        Boolean b3 = this.underlined;
        Boolean b4 = this.obfuscated;
        switch (format) {
            case OBFUSCATED: {
                b4 = true;
                break;
            }
            case BOLD: {
                b = true;
                break;
            }
            case STRIKETHROUGH: {
                b2 = true;
                break;
            }
            case UNDERLINE: {
                b3 = true;
                break;
            }
            case ITALIC: {
                b1 = true;
                break;
            }
            case RESET: {
                return EMPTY;
            }
            default: {
                b4 = false;
                b = false;
                b2 = false;
                b3 = false;
                b1 = false;
                textcolor = ChatHexColor.fromLegacyFormat(format);
            }
        }
        return new ChatModifier(textcolor, b, b1, b3, b2, b4, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, ChatHexColor.clazz(), Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, ChatClickable.clazz(), ChatHoverable.clazz(), String.class, MinecraftKey.clazz()),
                this.color.createOriginal(), this.bold, this.italic,this.underlined,this.strikethrough, this.obfuscated, this.clickEvent.createOriginal(), this.hoverEvent.createOriginal(), this.insertion, this.font.createOriginal()
        );
    }
    
    public static Class<?> clazz() {
        return ClassAccessor.get().getChatModifierClass();
    }
}
