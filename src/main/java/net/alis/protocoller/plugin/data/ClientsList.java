package net.alis.protocoller.plugin.data;

import net.alis.protocoller.ProtocollerClient;

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


}
