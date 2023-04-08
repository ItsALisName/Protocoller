package net.alis.protocoller.event.impl;

import com.google.common.collect.Sets;
import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.event.RegisteredPacketListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PacketHandlerList {

    private final Set<RegisteredPacketListener> registeredPacketListeners;

    public PacketHandlerList() {
        this.registeredPacketListeners = Collections.synchronizedSet(Sets.newConcurrentHashSet());
    }

    public Set<RegisteredPacketListener> getRegisteredListeners() {
        return registeredPacketListeners;
    }

    public Set<RegisteredPacketListener> getRegisteredListeners(ProtocollerClient client) {
        Set<RegisteredPacketListener> response = new HashSet<>();
        synchronized (registeredPacketListeners){
            for (RegisteredPacketListener listener : this.registeredPacketListeners) {
                if (listener.getClient().equals(client)) {
                    response.add(listener);
                }
            }
        }
        return response;
    }

    public void unregister(ProtocollerClient client) {
        synchronized (registeredPacketListeners){
            this.registeredPacketListeners.removeIf(listener -> listener.getClient().equals(client));
        }
    }

    public void unregisterAll() {
        this.registeredPacketListeners.clear();
    }
}
