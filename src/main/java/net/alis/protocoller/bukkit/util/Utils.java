package net.alis.protocoller.bukkit.util;

import net.alis.protocoller.packet.MinecraftPacketType;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Utils {

    public static <T> Set<T> findSimilar(List<T> first, List<T> second) {
        return first.stream().filter(second::contains).collect(Collectors.toSet());
    }

    public static String setColors(String input) {
        return input.replace("&", "§");
    }

}
