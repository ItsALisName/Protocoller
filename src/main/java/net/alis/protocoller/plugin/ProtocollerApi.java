package net.alis.protocoller.plugin;

import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.Protocoller;
import net.alis.protocoller.plugin.exception.BannedClientException;
import net.alis.protocoller.plugin.providers.ApiProvider;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ProtocollerApi implements Protocoller {

    @Contract("_ -> new")
    @Override
    public @NotNull ProtocollerClient registerClient(Plugin plugin) throws BannedClientException {
        return new ApiProvider(plugin);
    }

    @Contract(pure = true)
    public @NotNull String getVersion() {
        return "0.0.2";
    }

    @Override
    public int registeredClientsCount() {
        return GlobalProvider.instance().getData().getClients().get().size();
    }

}
