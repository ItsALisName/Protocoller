package net.alis.protocoller;

import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.event.impl.ManagerType;

public interface ProtocollerClient {

    int registeredListeners();

    NetworkServer getServer();

    PacketEventsManager getEventManager(ManagerType type);

    String getName();

    boolean equals(Object object);

    boolean equals(ProtocollerClient client);

}
