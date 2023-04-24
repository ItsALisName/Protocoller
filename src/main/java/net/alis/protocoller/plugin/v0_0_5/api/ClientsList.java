package net.alis.protocoller.plugin.v0_0_5.api;

import net.alis.protocoller.ProtocollerClient;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class ClientsList {

    private final Set<ProtocollerClient> clients;

    protected ClientsList() {
        this.clients = new HashSet<>();
    }

    public Set<ProtocollerClient> get() {
        return this.clients;
    }

    public boolean unregister(ProtocollerClient client) {
        return this.clients.removeIf(c -> c.equals(client));
    }

    public boolean unregister(String clientName) {
        return this.clients.removeIf(c -> c.getName().equalsIgnoreCase(clientName));
    }

    @Nullable
    public ProtocollerClient get(String name) {
        for(ProtocollerClient client : this.clients) if(client.getName().equalsIgnoreCase(name)) return client;
        return null;
    }

}
