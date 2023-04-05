package net.alis.protocoller;

import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.server.NetworkServer;

import java.util.List;

public interface ApiUser {

    int registeredListeners();

    NetworkServer getServer();

    PacketEventsManager getEventManager();

    String getName();

    String getVersion();

    List<String> getAuthors();

    boolean equals(Object object);

    boolean equals(ApiUser user);

}
