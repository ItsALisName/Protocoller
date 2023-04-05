package net.alis.protocoller.plugin.util;

import lombok.SneakyThrows;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Utils {

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

    public static @NotNull String getCurrentDate(boolean isDetail) {
        SimpleDateFormat format;
        if(isDetail) {
            format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        } else {
            format = new SimpleDateFormat("dd/MM/yyyy");
        }
        return format.format(new Date());
    }

    public static @NotNull String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

}
