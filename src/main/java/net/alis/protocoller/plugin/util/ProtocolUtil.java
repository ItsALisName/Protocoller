package net.alis.protocoller.plugin.util;

import com.viaversion.viaversion.api.Via;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ProtocolUtil {

    public static boolean viaVersionInstalled = false;
    public static Map<String, Integer> TEMP_PROTOCOL_MAP = new HashMap<>();

    public static int getPlayerProtocol(Player player) {
        if(viaVersionInstalled) {
            return Via.getAPI().getPlayerVersion(player.getUniqueId());
        } else {
            if(!TEMP_PROTOCOL_MAP.containsKey(player.getAddress().getAddress().getHostAddress())) {
                return GlobalProvider.get().getServer().getVersion().getProtocol();
            }
            return TEMP_PROTOCOL_MAP.get(player.getAddress().getAddress().getHostAddress());
        }
    }

}
