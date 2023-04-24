package net.alis.protocoller.plugin.util;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Utils {

    public static final UUID NIL_UUID = new UUID(0L, 0L);

    public static <T> Set<T> findSimilar(@NotNull List<T> first, @NotNull List<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }

    public static int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max + 1);
    }

    @Contract(pure = true)
    public static @NotNull String colors(@NotNull String input) {
        return input.replace("&", "§");
    }

    public static @NotNull String currentDate(boolean inDetail) {
        SimpleDateFormat format;
        if(inDetail) {
            format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        } else {
            format = new SimpleDateFormat("dd-MM-yyyy");
        }
        return format.format(new Date());
    }

    public static boolean isNotNullText(String text) {
        return text != null && text.length() > 0;
    }

    public static @NotNull String currentTime(boolean fileNameFormat) {
        if(!fileNameFormat) return new SimpleDateFormat("HH:mm:ss").format(new Date());
        return new SimpleDateFormat("HH-mm-ss").format(new Date());
    }

    public static @NotNull String timeOf(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    public static @NotNull String dateOf(Date date, boolean inDetail) {
        if(inDetail) {
            return new SimpleDateFormat("EEEE, dd MMMM yyyy").format(date);
        } else {
            return new SimpleDateFormat("dd-MM-yyyy").format(date);
        }
    }

    public static <T> T @Nullable [] joinArrays(T[] a, T[] b) {
        if (a == null) {
            return (T[]) clone(b);
        } else if (b == null) {
            return (T[]) clone(a);
        } else {
            Object[] joinedArray = (Object[])(Array.newInstance(a.getClass().getComponentType(), a.length + b.length));
            System.arraycopy(a, 0, joinedArray, 0, a.length);
            try {
                System.arraycopy(b, 0, joinedArray, a.length, b.length);
                return (T[]) joinedArray;
            } catch (ArrayStoreException e) {
                Class<?> type1 = a.getClass().getComponentType();
                Class<?> type2 = b.getClass().getComponentType();
                if (!type1.isAssignableFrom(type2)) {
                    ExceptionBuilder.throwException(new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName()), true);
                } else {
                    ExceptionBuilder.throwException(e, true);
                }
            }
        }
        return null;
    }

    public static void checkClassSupportability(Class<?> obj, String objName, boolean isPacket) {
        if(obj == null) {
            ExceptionBuilder builder = new ExceptionBuilder();
            if(isPacket) {
                builder.getUnsupportedObjectsExceptions().unsupportedPacketError(objName).throwException();
            }
            builder.getUnsupportedObjectsExceptions().unsupportedObjectError(objName).throwException();
        }
    }

    public static Object[] clone(Object[] array) {
        return array == null ? null : array.clone();
    }

    public static <T> T make(@NotNull Supplier<T> supplier) {
        return supplier.get();
    }

    @Contract("_, _ -> param1")
    public static <T> T make(T t, @NotNull Consumer<T> consumer) {
        consumer.accept(t);
        return t;
    }

}
