package net.alis.protocoller;

import net.alis.protocoller.plugin.providers.ApiProvider;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Protocoller {

    @Contract("_ -> new")
    public static @NotNull ApiUser registerUser(Plugin plugin) {
        return new ApiProvider(plugin);
    }

    @Contract(pure = true)
    public static @NotNull String getVersion() {
        return "0.0.1";
    }
    
}
