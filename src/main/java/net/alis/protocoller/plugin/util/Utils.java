package net.alis.protocoller.plugin.util;

import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Utils {

    public static final UUID NIL_UUID = new UUID(0L, 0L);

    public static <T> Set<T> findSimilar(@NotNull List<T> first, @NotNull List<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }

    public static int generateRandomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max + 1);
    }

    @Contract(pure = true)
    public static @NotNull String setColors(@NotNull String input) {
        return input.replace("&", "§");
    }

    public static @NotNull String getCurrentDate(boolean inDetail) {
        SimpleDateFormat format;
        if(inDetail) {
            format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        } else {
            format = new SimpleDateFormat("dd-MM-yyyy");
        }
        return format.format(new Date());
    }

    public static @NotNull String getCurrentTime(boolean fileNameFormat) {
        if(!fileNameFormat) return new SimpleDateFormat("HH:mm:ss").format(new Date());
        return new SimpleDateFormat("HH-mm-ss").format(new Date());
    }

    public static <T> T[] joinArrays(T[] a, T[] b) {
        return (T[]) ArrayUtils.addAll(a, b);
    }

}
