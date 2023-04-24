package net.alis.protocoller.plugin.v0_0_5.api;

import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.Protocoller;
import net.alis.protocoller.plugin.exception.BannedClientException;
import net.alis.protocoller.plugin.providers.ApiProvider;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ProtocollerApi implements Protocoller {

    private final ClientsList clients;

    public ProtocollerApi() {
        this.clients = new ClientsList();
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ProtocollerClient registerClient(Plugin plugin) throws BannedClientException {
        return new ApiProvider(plugin);
    }

    @Contract(pure = true)
    public @NotNull String getVersion() {
        return "0.0.5";
    }

    @Override
    public int registeredClientsCount() {
        return clients.get().size();
    }

    public ClientsList getClients() {
        return this.clients;
    }
}
