package net.md_5.bungee.api.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class Ext {

    public static class Hover {

        public static @Nullable HoverEvent.Action getByName(String name) {
            for(HoverEvent.Action action : HoverEvent.Action.values()) if(action.name().toLowerCase().equalsIgnoreCase(name)) return action;
            return null;
        }

        public static HoverEvent.Action fromOriginal(Object original) {
            AccessedObject object = new AccessedObject(original, true);
            return getByName(object.read(0, String.class));
        }

        public static Object original(HoverEvent.@NotNull Action action) {
            return Reflect.callMethod(null, getOriginalByName, false, action.name().toLowerCase());
        }

        public static Method getOriginalByName;

    }

    public static class Click {

        public static @Nullable ClickEvent.Action getById(int id) {
            for(ClickEvent.Action action : ClickEvent.Action.values()) if(action.ordinal() == id) return action;
            return null;
        }

        public static Enum<?> original(ClickEvent.Action action) {
            return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getClickActionEnum(), action.ordinal());
        }

    }

}
