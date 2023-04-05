package net.alis.protocoller.samples.network.chat;

import com.google.common.collect.Lists;
import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum ChatFormat {

    BLACK("BLACK", '0', 0, 0),
    DARK_BLUE("DARK_BLUE", '1', 1, 170),
    DARK_GREEN("DARK_GREEN", '2', 2, 43520),
    DARK_AQUA("DARK_AQUA", '3', 3, 43690),
    DARK_RED("DARK_RED", '4', 4, 11141120),
    DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
    GOLD("GOLD", '6', 6, 16755200),
    GRAY("GRAY", '7', 7, 11184810),
    DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
    BLUE("BLUE", '9', 9, 5592575),
    GREEN("GREEN", 'a', 10, 5635925),
    AQUA("AQUA", 'b', 11, 5636095),
    RED("RED", 'c', 12, 16733525),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
    YELLOW("YELLOW", 'e', 14, 16777045),
    WHITE("WHITE", 'f', 15, 16777215),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1, (Integer)null);
    private static final Map<String, ChatFormat> y = Arrays.stream(values()).collect(Collectors.toMap((f) -> c(f.name), (f) -> f));
    private static final Pattern COMPILE = Pattern.compile("(?i)§[0-9A-FK-OR]");
    private final String name;
    public final char code;
    private final boolean modifier;
    private final String colorFormat;
    private final int colorIndex;
    @Nullable
    private final Integer colorValue;

    private static String c(String name) {
        return name.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    ChatFormat(String name, char code, int colorIndex, @Nullable Integer colorValue) {
        this(name, code, false, colorIndex, colorValue);
    }

    ChatFormat(String name, char code, boolean modifier) {
        this(name, code, modifier, -1, (Integer)null);
    }

    ChatFormat(String name, char code, boolean modifier, int colorIndex, @Nullable Integer colorValue) {
        this.name = name;
        this.code = code;
        this.modifier = modifier;
        this.colorIndex = colorIndex;
        this.colorValue = colorValue;
        this.colorFormat = "§" + code;
    }

    public char getCode() {
        return this.code;
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public boolean isFormat() {
        return this.modifier;
    }

    public boolean isReset() {
        return !this.modifier && this != RESET;
    }

    @Nullable
    public Integer getColorValue() {
        return this.colorValue;
    }

    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.colorFormat;
    }

    @Nullable
    public static String clearCode(@Nullable String string) {
        return string == null ? null : COMPILE.matcher(string).replaceAll("");
    }

    @Nullable
    public static ChatFormat getFormat(@Nullable String name) {
        return name == null ? null : y.get(c(name));
    }

    @Nullable
    public static ChatFormat getByHexValue(int i) {
        ChatFormat[] chatFormats = values();
        int length = chatFormats.length;
        for(int i1 = 0; i1 < length; ++i1) {
            ChatFormat value = chatFormats[i1];
            if (value.colorValue != null && value.colorValue == i) {
                return value;
            }
        }

        return null;
    }

    @Nullable
    public static ChatFormat a(int colorIndex) {
        if (colorIndex < 0) {
            return RESET;
        } else {
            ChatFormat[] chatFormats = values();
            int length = chatFormats.length;

            for(int i = 0; i < length; ++i) {
                ChatFormat chatFormatting = chatFormats[i];
                if (chatFormatting.colorIndex == colorIndex) {
                    return chatFormatting;
                }
            }

            return null;
        }
    }

    @Nullable
    public static ChatFormat getChatFormat(char code) {
        char at = Character.toString(code).toLowerCase(Locale.ROOT).charAt(0);
        ChatFormat[] values = values();
        int length = values.length;

        for(int i = 0; i < length; ++i) {
            ChatFormat chatFormatting = values[i];
            if (chatFormatting.code == at) {
                return chatFormatting;
            }
        }

        return null;
    }

    public static Collection<String> getNames(boolean colors, boolean modifiers) {
        List<String> list = Lists.newArrayList();
        ChatFormat[] chatFormats = values();
        int length = chatFormats.length;
        for (ChatFormat chatFormatting : chatFormats) {
            if ((!chatFormatting.isReset() || colors) && (!chatFormatting.isFormat() || modifiers)) {
                list.add(chatFormatting.getName());
            }
        }
        return list;
    }

    public Enum<?> original() {
        return BaseReflection.readEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getChatFormatEnum(), this.ordinal());
    }

    public String asString() {
        return this.getName();
    }

}
