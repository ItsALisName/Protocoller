package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.plugin.events.AsyncPacketEventManager;
import net.alis.protocoller.plugin.events.SyncPacketEventManager;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.event.manager.AsynchronousEventManager;
import net.alis.protocoller.event.manager.SynchronousEventManager;

public class EventManagersProvider implements PacketEventsManager {

    private final SyncPacketEventManager sync;
    private final AsyncPacketEventManager async;

    protected EventManagersProvider() {
        this.sync = new SyncPacketEventManager();
        this.async = new AsyncPacketEventManager();
    }

    @Override
    public SynchronousEventManager getSynchronousManager() {
        return this.sync;
    }

    @Override
    public AsynchronousEventManager getAsynchronousManager() {
        return this.async;
    }
}
