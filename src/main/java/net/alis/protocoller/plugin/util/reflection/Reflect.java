package net.alis.protocoller.plugin.util.reflection;

import net.alis.protocoller.plugin.data.InitialData;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.network.packet.IndexedParam;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Reflect {

    @Nullable
    public static Class<?> getClass(@NotNull String clazz, boolean ignoreException) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().classNotFound(clazz).throwException();
        }
    }

    @Nullable
    public static Field getField(@NotNull Class<?> instance, String fieldName, boolean ignoreException) {
        for(Field field : instance.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getName().equalsIgnoreCase(fieldName)) return field;
        }
        return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().fieldNotFound(instance, fieldName).throwException();
    }

    public static void writeField(Object instance, @NotNull Field field, Object param, boolean ignoreException) {
        field.setAccessible(true);
        try {
            field.set(instance, param);
        } catch (IllegalAccessException e) {
            new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().defineReason(e).writeFieldError(instance.getClass(), field).throwException();
        }
    }

    @Contract(pure = true)
    @Nullable
    public static <PARAM> PARAM readField(Object instance, @NotNull Field field, boolean ignoreException) {
        try {
            field.setAccessible(true);
            return (PARAM) field.get(instance);
        } catch (IllegalAccessException accessException) {
            return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().defineReason(accessException).readFieldError(instance.getClass(), field).throwException();
        }
    }

    public static @Nullable Class<?> getSubClass(@NotNull Class<?> clazz, String name, boolean ignoreException) {
        for (Class<?> subClass : clazz.getDeclaredClasses()) {
            if (subClass.getSimpleName().equalsIgnoreCase(name)) {
                return subClass;
            }
        }
        return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().subClassNotFound(name, clazz.getName()).throwException();
    }

    @Nullable
    public static Class<?> getClassOr(@NotNull String clazz, @NotNull String clazz$1, boolean ignoreException) {
        Class<?> response = getClass(clazz, ignoreException);
        return response == null ? getClass(clazz$1, ignoreException) : response;
    }

    @Nullable
    public static Class<?> getCraftBukkitClass(String clazz, boolean ignoreException) {
        return getClass(InitialData.get().getCraftBukkitPackage() + "." + clazz, ignoreException);
    }

    @Nullable
    public static Class<?> getNMClass(String clazz, boolean ignoreException) {
        return getClass("net.minecraft." + clazz, ignoreException);
    }

    @Nullable
    public static Class<?> getLegacyNMSClass(String clazz, boolean ignoreException) {
        return getClass("net.minecraft.server." + InitialData.get().getPackageVersion() + "." + clazz, ignoreException);
    }

    public static Class<?> getNMSClass(String clazzName, @Nullable String clazzPathWithName, boolean ignoreException) {
        if(InitialData.get().isLegacyServer()) {
            return getLegacyNMSClass(clazzName, ignoreException);
        } else {
            return getClass(clazzPathWithName, ignoreException);
        }
    }

    public static @Nullable Method getMethod(@NotNull Class<?> instance, String name, Class<?>[] params) {
        for(Method method : instance.getDeclaredMethods()) {
            method.setAccessible(true);
            if(Arrays.equals(method.getParameterTypes(), params)) {
                if (method.getName().equalsIgnoreCase(name)) {
                    return method;
                }
            }
        }
        return new ExceptionBuilder().getReflectionExceptions().methodNotFound(instance, name, params).throwException();
    }

    public static Method getMethod(Class<?> instance, Class<?> returnType, boolean ignoreException, Class<?>... parameters) {
        for(Method method : instance.getDeclaredMethods()) {
            method.setAccessible(true);
            if(method.getReturnType() == returnType) {
                if(Arrays.equals(method.getParameterTypes(), parameters)) return method;
            }
        }
        return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().methodNotFound(instance, "unknown", parameters).throwException();
    }

    public static @Nullable Method getMethod(@NotNull Class<?> instance, String methodName, Class<?> returnType, boolean ignoreException, Class<?>... paramTypes) {
        for(Method m : instance.getDeclaredMethods()) {
            m.setAccessible(true);
            if(m.getName().equalsIgnoreCase(methodName) && m.getReturnType() == returnType && Arrays.equals(m.getParameterTypes(), paramTypes)) {
                return m;
            }
        }
        return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().methodNotFound(instance, methodName, returnType, paramTypes).throwException();
    }

    public static @NotNull Method getMethod(@NotNull Class<?> instance, int index, Class<?> returnType) {
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
        return new ExceptionBuilder().getReflectionExceptions().methodNotFound(instance, index, returnType).throwException();
    }

    public static @NotNull Method getMethod(Class<?> instance, Class<?> returnType) {
        return getMethod(instance, 0, returnType);
    }

    public static Method getSuperclassMethod(@NotNull Class<?> instance, String methodName, Class<?> returnType, boolean ignoreException, Class<?>... paramTypes) {
        return getMethod(instance.getSuperclass(), methodName, returnType, ignoreException, paramTypes);
    }

    public static Method getSuperclassMethod(@NotNull Class<?> instance, String name, Class<?>[] params) {
        return getMethod(instance.getSuperclass(), name, params);
    }

    public static @NotNull Field getField(@NotNull Class<?> instance, int index, Class<?> type) {
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
        return new ExceptionBuilder().getReflectionExceptions().fieldNotFound(type, index, instance).throwException();
    }

    public static <PARAM> @Nullable PARAM readSuperclassField(@NotNull Object instance, int index, Class<?> type, boolean ignoreException) {
        int start = 0;
        for(Field field : instance.getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(index == start) {
                    try {
                        return (PARAM) field.get(instance);
                    } catch (IllegalAccessException e) {
                        return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().defineReason(e).readFieldError(instance.getClass(), index, field).throwException();
                    }
                }
                start += 1;
            }
        }
        return new ExceptionBuilder().getReflectionExceptions().fieldNotFound(type, index, instance.getClass()).throwException();
    }

    public static <PARAM> PARAM readField(@NotNull Object instance, String fieldName, boolean ignoreException) {
        try {
            Field f = instance.getClass().getDeclaredField(fieldName); f.setAccessible(true);
            return (PARAM) f.get(instance);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().defineReason(e).readFieldError(instance.getClass(), fieldName).throwException();
        }
    }

    public static <PARAM> PARAM readField(@NotNull Object instance, int index, Class<?> type, boolean ignoreException) {
        int start = 0;
        for(Field field : instance.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(index == start) {
                    try {
                        return (PARAM) field.get(instance);
                    } catch (IllegalAccessException e) {
                        return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().defineReason(e).readFieldError(instance.getClass(), index, field).throwException();
                    }
                }
                start += 1;
            }
        }
        return new ExceptionBuilder().getReflectionExceptions().fieldNotFound(type, index, instance.getClass()).throwException();
    }

    public static void writeField(@NotNull Object instance, int index, Object param, boolean ignoreException) {
        int start = 0;
        for(Field field : instance.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == param.getClass()) {
                if(index == start) {
                    try {
                        field.set(instance, param); break;
                    } catch (IllegalAccessException e) {
                        new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().defineReason(e).writeFieldError(instance.getClass(), index, field).throwException();
                    }
                }
                start += 1;
            }
        }
        new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().fieldNotFound(param.getClass(), index, instance.getClass()).throwException();
    }

    public static @NotNull Method getMethod(@NotNull Class<?> instance, int index, String methodName) {
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
        return new ExceptionBuilder().getReflectionExceptions().methodNotFound(instance, index, methodName).throwException();
    }

    public static <PARAM> PARAM callMethod(Object instance, @NotNull Method method, boolean ignoreException, Object... parameters) {
        try {
            method.setAccessible(true);
            return (PARAM) method.invoke(instance, parameters);
        } catch (Exception e) {
            return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().defineReason(e).callMethodError(instance.getClass(), method).throwException();
        }
    }

    public static <PARAM> PARAM callInterfaceMethod(Class<?> instance, int index, Class<?> returnType) {
        try {
            return (PARAM) getMethod(instance, index, returnType).invoke(null);
        } catch (Exception e) {
            return new ExceptionBuilder().getReflectionExceptions().defineReason(e).callInterfaceMethodError(instance, index, returnType).throwException();
        }
    }

    public static <PARAM> PARAM callInterfaceMethod(Class<?> instance, int index, Class<?> returnType, Object... objects) {
        try {
            return (PARAM) getMethod(instance, index, returnType).invoke(null, objects);
        } catch (Exception e) {
            return new ExceptionBuilder().getReflectionExceptions().defineReason(e).callInterfaceMethodError(instance, index, returnType).throwException();
        }
    }

    public static <PARAM> PARAM callInterfaceMethod(Class<?> instance, int index, String methodName) {
        try {
            return (PARAM) getMethod(instance, index, methodName).invoke(null);
        } catch (Exception e) {
            return new ExceptionBuilder().getReflectionExceptions().defineReason(e).callInterfaceMethodError(instance, index, methodName).throwException();
        }
    }

    public static <PARAM> PARAM callInterfaceMethod(Class<?> instance, int index, String methodName, Object... objects) {
        try {
            return (PARAM) getMethod(instance, index, methodName).invoke(null,objects);
        } catch (Exception e) {
            return new ExceptionBuilder().getReflectionExceptions().defineReason(e).callInterfaceMethodError(instance, index, methodName).throwException();
        }
    }

    @Nullable
    public static Constructor<?> getConstructor(Class<?> instance, boolean ignoreException, Class<?>... parameters) {
        if(instance != null){
            for (Constructor<?> constructor : instance.getDeclaredConstructors()) {
                constructor.setAccessible(true);
                if (Arrays.equals(constructor.getParameterTypes(), parameters)) {
                    return constructor;
                }
            }
            return new ExceptionBuilder().setIgnored(ignoreException).getReflectionExceptions().constructorNotFound(instance, parameters).throwException();
        }
        if(!ignoreException){
            return ExceptionBuilder.throwException(new NullPointerException("Failed to get class constructor because class is null"), true);
        }
        return null;
    }

    @Nullable
    public static Constructor<?> getConstructor(Class<?> instance, Object @NotNull ... parameters) {
        List<Class<?>> params = new ArrayList<>(); for(Object obj : parameters) params.add(obj.getClass());
        for(Constructor<?> constructor : instance.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            if(Arrays.asList(constructor.getParameterTypes()).equals(params)) {
                return constructor;
            }
        }
        return new ExceptionBuilder().getReflectionExceptions().constructorNotFound(instance, params.toArray(new Class[0])).throwException();
    }

    public static <PARAM> @Nullable PARAM classNewInstance(@NotNull Class<?> clazz) {
        try {
            return (PARAM) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return new ExceptionBuilder().getReflectionExceptions().defineReason(e).classNewInstanceError(clazz).throwException();
        }
    }

    public static <PARAM> @NotNull PARAM callConstructor(Constructor<?> constructor, Object... parameters) {
        try {
            if(parameters == null) {
                return (PARAM) constructor.newInstance();
            }
            if(parameters.length == 0) {
                return (PARAM) constructor.newInstance();
            }
            return (PARAM) constructor.newInstance(parameters);
        } catch (Exception e) {
            List<Class<?>> params = Arrays.stream(parameters).map(Object::getClass).collect(Collectors.toList());
            return new ExceptionBuilder().getReflectionExceptions().defineReason(e).callConstructorError(constructor, params.toArray(new Class[0])).throwException();
        }
    }

    public static <PARAM> PARAM callEmptyConstructor(@NotNull Class<?> instance, IndexedParam<?,?> @NotNull [] params) {
        Constructor<?> constructor = instance.getDeclaredConstructors()[0]; constructor.setAccessible(true);
        AccessedObject o = new AccessedObject(callConstructor(constructor));
        for(IndexedParam<?, ?> p : params) {
            o.write(p.getIndex(), p.getObject());
        }
        return (PARAM) o.getObject();
    }

    public static <PARAM> @NotNull PARAM readEnumValue(@NotNull Class<? extends Enum<?>> clazz, int ordinal) {
        for(Enum<?> o : clazz.getEnumConstants()) {
            if(o.ordinal() == ordinal) {
                return (PARAM) o;
            }
        }
        return new ExceptionBuilder().getReflectionExceptions().findEnumConstantError(clazz, ordinal).throwException();
    }

    public static int getEnumId(@NotNull Object enumObject) {
        for(Enum<?> e : ((Class<? extends Enum<?>>)enumObject.getClass()).getEnumConstants()) {
            if(e.equals(enumObject)) return e.ordinal();
        }
        return -404;
    }

}
