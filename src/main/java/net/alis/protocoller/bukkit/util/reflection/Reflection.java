package net.alis.protocoller.bukkit.util.reflection;

import net.alis.protocoller.bukkit.data.InitialData;
import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.util.ObjectAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reflection {

    @Nullable
    public static Class<?> getClass(@NotNull String clazz) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Nullable
    public static Field getField(Class<?> instance, String fieldName) {
        for(Field field : instance.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getName().equalsIgnoreCase(fieldName)) return field;
        }
        return null;
    }

    public static void writeField(Object instance, @NotNull Field field, Object param) {
        field.setAccessible(true);
        try {
            field.set(instance, param);
        } catch (IllegalAccessException e) {
            LogsManager.get().getLogger().error("Failed to read field '" + field.getName() + "' in class '" + instance.getClass().getSimpleName() + "'!", e);
        }
    }

    @Nullable
    public static <PARAM> PARAM readField(Object instance, Field field) {
        try {
            return (PARAM) field.get(instance);
        } catch (IllegalAccessException accessException) {
            return null;
        }
    }

    public static Class<?> getSubClass(Class<?> clazz, String name) {
        Class<?>[] classes = clazz.getDeclaredClasses();
        for (Class<?> subClass : classes) {
            if (subClass.getSimpleName().equals(name)) {
                return subClass;
            }
        }
        return null;
    }

    @Nullable
    public static Class<?> getClassOr(@NotNull String clazz, @NotNull String clazz$1) {
        Class<?> response = getClass(clazz);
        return response == null ? getClass(clazz$1) : response;
    }

    @Nullable
    public static Class<?> getCraftBukkitClass(String clazz) {
        return getClass(InitialData.INSTANCE.getCraftBukkitPackage() + "." + clazz);
    }

    @Nullable
    public static Class<?> getNMClass(String clazz) {
        return getClass("net.minecraft." + clazz);
    }

    @Nullable
    public static Class<?> getLegacyNMSClass(String clazz) {
        return getClass("net.minecraft.server." + InitialData.INSTANCE.getPackageVersion() + "." + clazz);
    }

    public static Class<?> getNMSClass(String clazzName, @Nullable String clazzPathWithName) {
        if(InitialData.INSTANCE.isLegacyServer()) {
            return getLegacyNMSClass(clazzName);
        } else {
            return getClass(clazzPathWithName);
        }
    }

    public static Method getMethod(Class<?> instance, String name, Class<?>[] params) {
        for(Method method : instance.getDeclaredMethods()) {
            method.setAccessible(true);
            if(Arrays.equals(method.getParameterTypes(), params)) {
                if (method.getName().equalsIgnoreCase(name)) {
                    return method;
                }
            }
        }
        return null;
    }

    public static Method getMethodNullParams(Class<?> instance, Class<?> returnType) {
        for(Method method : instance.getDeclaredMethods()) {
            method.setAccessible(true);
            if(method.getReturnType() == returnType && method.getParameterTypes().length == 0) return method;
        }
        return null;
    }

    public static Method getMethod(Class<?> instance, String name, Class<?>[] params, Class<?> returnType) {
        for(Method method : instance.getDeclaredMethods()) {
            method.setAccessible(true);
            if(Arrays.equals(method.getParameterTypes(), params)) {
                if (method.getName().equalsIgnoreCase(name)) {
                    return method;
                }
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> cls, int index, Class<?>... params) {
        int start = 0;
        for (final Method m : cls.getDeclaredMethods()) {
            if ((params == null || Arrays.equals(m.getParameterTypes(), params)) && index == start++) {
                m.setAccessible(true);
                return m;
            }
        }
        if (cls.getSuperclass() != null) {
            return getMethod(cls.getSuperclass(), index, params);
        }
        return null;
    }

    public static Field getField(Class<?> instance, int index, Class<?> type) {
        int start = 0;
        for(Field field : instance.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(index == start) {
                    return field;
                }
                start += 1;
            }
        }
        throw new RuntimeException("Failed to find field in class!\n" +
                "\n[Protocoller] Details: " +
                "\n[Protocoller] From class: '" + instance.getSimpleName() + "'" +
                "\n[Protocoller] Requested field type: '" + type.getSimpleName() + "'" +
                "\n[Protocoller] Requested field index: '" + index + "'"
        );
    }

    public static <PARAM> PARAM readSuperclassField(Object instance, int index, Class<?> type) {
        int start = 0;
        for(Field field : instance.getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(index == start) {
                    try {
                        return (PARAM) field.get(instance);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(
                                "Failed to read field from object!\n" +
                                        "\n[Protocoller] Details: " +
                                        "\n[Protocoller] From object: '" + instance.toString() + "'" +
                                        "\n[Protocoller] Requested field type: '" + type.getSimpleName() + "'" +
                                        "\n[Protocoller] Requested field index: '" + index + "'",
                                e
                        );
                    }
                }
                start += 1;
            }
        }
        return null;
    }

    public static <PARAM> PARAM readField(Object instance, String fieldName) {
        try {
            return (PARAM) instance.getClass().getDeclaredField(fieldName).get(instance);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(
                    "Failed to read field from object!\n" +
                            "\n[Protocoller] Details: " +
                            "\n[Protocoller] From object: '" + instance.toString() + "'" +
                            "\n[Protocoller] Requested field type: '???'" +
                            "\n[Protocoller] Requested field index: '???'",
                    e
            );
        }
    }

    public static <PARAM> PARAM readField(Object instance, int index, Class<?> type) {
        int start = 0;
        for(Field field : instance.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(index == start) {
                    try {
                        return (PARAM) field.get(instance);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(
                                "Failed to read field from object!\n" +
                                        "\n[Protocoller] Details: " +
                                        "\n[Protocoller] From object: '" + instance.toString() + "'" +
                                        "\n[Protocoller] Requested field type: '" + type.getSimpleName() + "'" +
                                        "\n[Protocoller] Requested field index: '" + index + "'",
                                e
                        );
                    }
                }
                start += 1;
            }
        }
        throw new RuntimeException("Failed to find field in object!\n" +
                "\n[Protocoller] Details: " +
                "\n[Protocoller] From object: '" + instance.toString() + "'" +
                "\n[Protocoller] Requested field type: '" + type.getSimpleName() + "'" +
                "\n[Protocoller] Requested field index: '" + index + "'"
        );
    }

    public static void writeField(Object instance, int index, Object param) {
        int start = 0;
        for(Field field : instance.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == param.getClass()) {
                if(index == start) {
                    try {
                        field.set(instance, param); break;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(
                                "Failed to write field in object!\n" +
                                        "\n[Protocoller] Details: " +
                                        "\n[Protocoller] In object: '" + instance.toString() + "'" +
                                        "\n[Protocoller] Requested field type: '" + param.getClass().getSimpleName() + "'" +
                                        "\n[Protocoller] Requested field index: '" + index + "'",
                                e
                        );
                    }
                }
                start += 1;
            }
        }
        throw new RuntimeException("Failed to find field in object!\n" +
                "\n[Protocoller] Details: " +
                "\n[Protocoller] In object: '" + instance.toString() + "'" +
                "\n[Protocoller] Requested field type: '" + param.getClass().getSimpleName() + "'" +
                "\n[Protocoller] Requested field index: '" + index + "'"
        );
    }

    public static Method getMethod(Class<?> instance, int index, Class<?> returnType) {
        int start = 0;
        for(Method method : instance.getDeclaredMethods()) {
            method.setAccessible(true);
            if(method.getReturnType() == returnType || method.getReturnType().isAssignableFrom(returnType)) {
                if(index == start) {
                    return method;
                }
                start += 1;
            }
        }
        throw new RuntimeException("Failed to find method in class!\n" +
                "\n[Protocoller] Details: " +
                "\n[Protocoller] From class: '" + instance.getSimpleName() + "'" +
                "\n[Protocoller] Requested method return type: '" + returnType.getSimpleName() + "'" +
                "\n[Protocoller] Requested method index: '" + index + "'"
        );
    }

    public static Method getMethod(Class<?> instance, int index, String methodName) {
        int start = 0;
        for(Method method : instance.getDeclaredMethods()) {
            method.setAccessible(true);
            if(method.getName().equalsIgnoreCase(methodName)) {
                if(index == start) {
                    return method;
                }
                start += 1;
            }
        }
        throw new RuntimeException("Failed to find method in class!\n" +
                "\n[Protocoller] Details: " +
                "\n[Protocoller] From class: '" + instance.getSimpleName() + "'" +
                "\n[Protocoller] Requested method return type: '???'" +
                "\n[Protocoller] Requested method index: '" + index + "'"
        );
    }

    public static <PARAM> PARAM callMethod(Object instance, Method method, Object... parameters) {
        try {
            method.setAccessible(true);
            return (PARAM) method.invoke(instance, parameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Failed to call method from object!" +
                    "\n[Protocoller] Details: " +
                    "\n[Protocoller] From object: '" + instance.toString() + "'" +
                    "\n[Protocoller] Requested method: '" + method.getName() + "'" +
                    "\n[Protocoller] Reason: '" + e.getCause().getMessage() + "'",
                    e
            );
        }
    }

    public static <PARAM> PARAM callFirstMethod(Object instance, Class<?> returnType, Object... parameters) {
        try {
            return (PARAM) getMethod(instance.getClass(), 0, returnType).invoke(instance, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to call method from object!" +
                "\n[Protocoller] Details: " +
                "\n[Protocoller] From object: '" + instance.getClass().getSimpleName() + "'",
                e
        );
        }
    }

    public static Method getMethod(Class<?> cls, Class<?> returning, int index, Class<?>... params) {
        int start = 0;
        for (Method m : cls.getDeclaredMethods()) {
            if (Arrays.equals(m.getParameterTypes(), params) && (returning == null || m.getReturnType().equals(returning)) && index == start++) {
                m.setAccessible(true);
                return m;
            }
        }
        if (cls.getSuperclass() != null) {
            return getMethod(cls.getSuperclass(), null, index, params);
        }
        return null;
    }

    public static <PARAM> PARAM callInterfaceMethod(Class<?> instance, int index, Class<?> returnType) {
        try {
            return (PARAM) getMethod(instance, index, returnType).invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to call method from object!" +
                    "\n[Protocoller] Details: " +
                    "\n[Protocoller] From object: '" + instance.getSimpleName() + "'",
                    e
            );
        }
    }

    public static <PARAM> PARAM callInterfaceMethod(Class<?> instance, int index, Class<?> returnType, Object... objects) {
        try {
            return (PARAM) getMethod(instance, index, returnType).invoke(null, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to call method from object!" +
                    "\n[Protocoller] Details: " +
                    "\n[Protocoller] From object: '" + instance.getSimpleName() + "'",
                    e
            );
        }
    }

    public static <PARAM> PARAM callInterfaceMethod(Class<?> instance, int index, String methodName) {
        try {
            return (PARAM) getMethod(instance, index, methodName).invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to call method from object!" +
                    "\n[Protocoller] Details: " +
                    "\n[Protocoller] From object: '" + instance.getSimpleName() + "'",
                    e
            );
        }
    }

    public static <PARAM> PARAM callInterfaceMethod(Class<?> instance, int index, String methodName, Object... objects) {
        try {
            return (PARAM) getMethod(instance, index, methodName).invoke(null,objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to call method from object!" +
                    "\n[Protocoller] Details: " +
                    "\n[Protocoller] From object: '" + instance.getSimpleName() + "'",
                    e
            );
        }
    }

    public static boolean isNullConstructorsClass(Class<?> clazz) {
        if(clazz.getDeclaredConstructors().length == 0) return true;
        for(Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if(constructor.getParameterTypes().length == 0) return true;
        }
        return false;
    }

    @Nullable
    public static Constructor<?> getConstructor(Class<?> instance, Class<?>... parameters) {
        if(instance != null){
            for (Constructor<?> constructor : instance.getDeclaredConstructors()) {
                constructor.setAccessible(true);
                if (Arrays.equals(constructor.getParameterTypes(), parameters)) {
                    return constructor;
                }
            }
        }
        return null;
    }

    @Nullable
    public static Constructor<?> getConstructor(Class<?> instance, Object... parameters) {
        List<Class<?>> params = new ArrayList<>(); for(Object obj : parameters) params.add(obj.getClass());
        for(Constructor<?> constructor : instance.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            if(Arrays.asList(constructor.getParameterTypes()).equals(params)) {
                return constructor;
            }
        }
        return null;
    }

    public static <PARAM> PARAM classNewInstance(Class<?> clazz) {
        try {
            return (PARAM) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    public static <PARAM> PARAM callConstructor(Constructor<?> constructor, Object... parameters) {
        try {
            if(parameters == null) {
                return (PARAM) constructor.newInstance();
            }
            if(parameters.length == 0) {
                return (PARAM) constructor.newInstance();
            }
            return (PARAM) constructor.newInstance(parameters);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Failed to call constructor with name: '" + constructor.getName() + "'", e);
        }
    }

    public static <PARAM> PARAM callEmptyConstructor(Class<?> instance, IndexedParam<?,?>[] params) {
        Constructor<?> constructor = instance.getDeclaredConstructors()[0]; constructor.setAccessible(true);
        ObjectAccessor o = new ObjectAccessor(callConstructor(constructor));
        for(IndexedParam<?, ?> p : params) {
            o.write(p.getIndex(), p.getObject());
        }
        return (PARAM) o.getObject();
    }

    public static <PARAM> PARAM getEnumValue(Class<? extends Enum<?>> clazz, int ordinal) {
        for(Enum<?> o : clazz.getEnumConstants()) {
            if(o.ordinal() == ordinal) {
                return (PARAM) o;
            }
        }
        throw new RuntimeException("Failed to find enum constant with id: " + ordinal);
    }

    public static int getEnumId(Object enumObject) {
        for(Enum<?> e : ((Class<? extends Enum<?>>)enumObject.getClass()).getEnumConstants()) {
            if(e.equals(enumObject)) return e.ordinal();
        }
        return -404;
    }

}
