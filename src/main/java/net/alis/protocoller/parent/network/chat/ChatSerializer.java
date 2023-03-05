package net.alis.protocoller.parent.network.chat;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ChatSerializer {

    private static Method methodFromStringOrNull, methodFromJSON, methodFromJSONOrString, methodFromComponent, originalMethodWrite, originalMethodRead,
    methodFromStringLegacy;

    public static void init() {
        methodFromStringOrNull = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftChatMessageClass(), "fromStringOrNull", new Class[]{String.class, boolean.class});
        methodFromJSON = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftChatMessageClass(), "fromJSON", new Class[]{String.class});
        methodFromJSONOrString = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftChatMessageClass(), "fromJSONOrString", new Class[]{String.class, boolean.class, boolean.class});
        methodFromComponent = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftChatMessageClass(), "fromComponent", new Class[]{ClassesContainer.INSTANCE.getIChatBaseComponentClass()});
        if(methodFromJSONOrString == null) {
            methodFromStringLegacy = Reflection.getMethod(ClassesContainer.INSTANCE.getChatSerializerClass(), "Loot", new Class[]{String.class});
        }
        //originalMethodWrite = Reflection.getMethod(ClassesContainer.INSTANCE.getChatSerializerClass(), "Skip$1", new Class[]{String.class});
        //originalMethodRead = Reflection.getMethod(ClassesContainer.INSTANCE.getChatSerializerClass(), "Skip$1", new Class[]{ClassesContainer.INSTANCE.getIChatBaseComponentClass()});
    }

    public static Object fromStringOrNull(String s, boolean keepNewLines) {
        if(methodFromStringOrNull == null) {
            return fromStringLegacy(s);
        }
        return Reflection.callMethod(null, methodFromStringOrNull, s, keepNewLines);
    }

    public static Object fromJSON(String json) {
        if(methodFromJSON == null) {
            return fromStringLegacy(json);
        }
        return Reflection.callMethod(null, methodFromJSON, json);
    }

    public static Object fromJSONOrString(String s, boolean nullable, boolean keepNewLines) {
        if(methodFromJSONOrString == null) {
            return fromStringLegacy(s);
        }
        return Reflection.callMethod(null, methodFromJSONOrString, s, nullable, keepNewLines);
    }

    //public static Object fromJSON$1(String json) {
    //    return Reflection.callMethod(null, originalMethodWrite, json);
    //}

    public static String fromComponent(Object component) {
        return Reflection.callMethod(null, methodFromComponent, component);
    }

    public static Object fromStringLegacy(String s) {
        return Reflection.callMethod(null, methodFromStringLegacy, s);
    }

    //public static String fromComponent$1(Object component) {
    //    return Reflection.callMethod(null, originalMethodRead, component);
    //}

}
