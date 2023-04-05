package net.alis.protocoller;

import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface Protocoller {

    static Protocoller get() {
        return protocoller.protocoller$provider;
    }

    @NotNull ApiUser registerUser(Plugin plugin);

    @NotNull String getVersion();

    int registeredUsersCount();

    class protocoller { private static @Setter Protocoller protocoller$provider; }
    
}
