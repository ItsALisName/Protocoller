package net.alis.protocoller.event;

import com.google.common.collect.Sets;
import net.alis.protocoller.ApiUser;

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

    public Set<RegisteredPacketListener> getRegisteredListeners(ApiUser user) {
        Set<RegisteredPacketListener> response = new HashSet<>();
        synchronized (registeredPacketListeners){
            for (RegisteredPacketListener listener : this.registeredPacketListeners) {
                if (listener.getUser().equals(user)) {
                    response.add(listener);
                }
            }
        }
        return response;
    }

    public void unregister(ApiUser user) {
        synchronized (registeredPacketListeners){
            this.registeredPacketListeners.removeIf(listener -> listener.getUser().equals(user));
        }
    }

    public void unregisterAll() {
        this.registeredPacketListeners.clear();
    }
}
