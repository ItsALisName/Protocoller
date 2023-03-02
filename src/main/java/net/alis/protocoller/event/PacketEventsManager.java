package net.alis.protocoller.event;

import net.alis.protocoller.event.manager.AsynchronousEventManager;
import net.alis.protocoller.event.manager.SynchronousEventManager;

public interface PacketEventsManager {

    SynchronousEventManager getSynchronousManager();

    AsynchronousEventManager getAsynchronousManager();

}
