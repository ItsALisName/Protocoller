package net.alis.protocoller;

import net.alis.protocoller.plugin.api.ProtocollerApiUser;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Protocoller {

    @Contract("_ -> new")
    public static @NotNull ApiUser registerUser(Plugin plugin) {
        return new ProtocollerApiUser(plugin);
    }
    
}
