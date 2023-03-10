package net.alis.protocoller.bukkit.util;

import lombok.SneakyThrows;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
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

    @SneakyThrows
    public static void forceBStatsEnable() {
        if(GlobalProvider.instance().getConfig().isForceBStatsEnabled()) {
            File bstatsFile = new File("plugins/bStats/", "config.yml");
            if(bstatsFile.exists()) {
                FileConfiguration bstatsConfig = YamlConfiguration.loadConfiguration(bstatsFile);
                bstatsConfig.set("enabled", true);
                bstatsConfig.save(bstatsFile);
            }
        }
    }

}
