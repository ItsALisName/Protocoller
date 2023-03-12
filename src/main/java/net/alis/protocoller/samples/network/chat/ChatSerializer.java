package net.alis.protocoller.samples.network.chat;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.util.ChatDeserializer;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Objects;

public class ChatSerializer {

    public static Object[] fromString(String s) {
        return fromString(s, false);
    }

    public static Object[] fromString(String s, boolean keepNewLines) {
        return Reflection.callMethod(null, fromString$1, s, keepNewLines);
    }

    public static Object[] fromString(String s, boolean keepNewLines, boolean plain) {
        if(fromString$2 != null) return Reflection.callMethod(null, fromString$2, s, keepNewLines, plain);
        return fromString(s, keepNewLines);
    }

    public static Object fromStringOrNull(String message, boolean keepNewlines) {
        return message != null && !message.isEmpty() ? fromString(message, keepNewlines)[0] : null;
    }

    public static Object fromStringOrNull(String message) {
        return fromStringOrNull(message, false);
    }

    public static String toJSON(Object iChatBaseComponent) {
        return gson.toJson(iChatBaseComponent);
    }

    public static String toJSONOrNull(Object component) {
        return component == null ? null : toJSON(component);
    }

    @Nullable
    public static Object fromJSON(String json) {
        return ChatDeserializer.objectFromJsonString(gson, json, ClassesContainer.INSTANCE.getIChatMutableComponent(), false);
    }

    @Nullable
    public static Object fromJSONOrNull(String jsonMessage) {
        if (jsonMessage == null) return null;
        try {
            return fromJSON(jsonMessage);
        } catch (JsonParseException e) {
            return null;
        }
    }

    public static Object fromJSONOrString(String message) {
        return fromJSONOrString(message, false);
    }

    public static Object fromJSONOrString(String message, boolean keepNewlines) {
        return fromJSONOrString(message, false, keepNewlines);
    }

    public static Object fromJSONOrString(String message, boolean nullable, boolean keepNewlines) {
        if (message == null) message = "";
        if (nullable && message.isEmpty()) return null;
        Object component = fromJSONOrNull(message);
        return component != null ? component : fromString(message, keepNewlines)[0];
    }

    public static String fromStringToJSON(String message) {
        return fromStringToJSON(message, false);
    }

    public static String fromStringToJSON(String message, boolean keepNewlines) {
        Object component = fromString(message, keepNewlines)[0];
        return toJSON(component);
    }

    public static String fromStringOrNullToJSON(String message) {
        Object component = fromStringOrNull(message);
        return toJSONOrNull(component);
    }

    public static String fromJSONComponent(String jsonMessage) {
        Object component = fromJSONOrNull(jsonMessage);
        return fromComponent(component);
    }

    public static String fromJSONOrStringToJSON(String message) {
        return fromJSONOrStringToJSON(message, false);
    }

    public static String fromJSONOrStringToJSON(String message, boolean keepNewlines) {
        return fromJSONOrStringToJSON(message, false, keepNewlines, Integer.MAX_VALUE, false);
    }

    public static String fromJSONOrStringOrNullToJSON(String message) {
        return fromJSONOrStringOrNullToJSON(message, false);
    }

    public static String fromJSONOrStringOrNullToJSON(String message, boolean keepNewlines) {
        return fromJSONOrStringToJSON(message, true, keepNewlines, Integer.MAX_VALUE, false);
    }

    public static String fromJSONOrStringToJSON(String message, boolean nullable, boolean keepNewlines, int maxLength, boolean checkJsonContentLength) {
        if (message == null) message = "";

        if (nullable && message.isEmpty()) return null;
        Object component = fromJSONOrNull(message);
        if (component != null) {
            if (checkJsonContentLength) {
                String content = fromComponent(component);
                String trimmedContent = trimMessage(content, maxLength);
                if (!Objects.equals(content, trimmedContent)) return fromStringToJSON(trimmedContent, keepNewlines);
            }
            return message;
        } else {
            message = trimMessage(message, maxLength);
            return fromStringToJSON(message, keepNewlines);
        }
    }

    public static String trimMessage(String message, int maxLength) {
        return message != null && message.length() > maxLength ? message.substring(0, maxLength) : message;
    }

    public static String fromComponent(Object iChatBaseComponent) {
        return Reflection.callMethod(null, fromComponent, iChatBaseComponent);
    }

    public static void init() {
        gson = new Gson();
        fromString$1 = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftChatMessageClass(), "fromString", Array.newInstance(ClassesContainer.INSTANCE.getIChatBaseComponentClass(), 1).getClass(), String.class, Boolean.TYPE);
        fromString$2 = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftChatMessageClass(), "fromString", Array.newInstance(ClassesContainer.INSTANCE.getIChatBaseComponentClass(), 1).getClass(), String.class, Boolean.TYPE, Boolean.TYPE);
        fromComponent = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftChatMessageClass(), "fromComponent", String.class, ClassesContainer.INSTANCE.getIChatBaseComponentClass());
    }

    private static Method fromString$1, fromString$2, fromComponent;
    private static Gson gson;
}
