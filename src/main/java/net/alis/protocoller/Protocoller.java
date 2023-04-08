package net.alis.protocoller;

import lombok.Setter;
import net.alis.protocoller.plugin.exception.BannedClientException;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface Protocoller {

    static Protocoller get() {
        return protocoller.protocoller$provider;
    }

    @NotNull ProtocollerClient registerClient(Plugin plugin) throws BannedClientException;

    @NotNull String getVersion();

    int registeredClientsCount();

    class protocoller { private static @Setter Protocoller protocoller$provider; }
    
}
