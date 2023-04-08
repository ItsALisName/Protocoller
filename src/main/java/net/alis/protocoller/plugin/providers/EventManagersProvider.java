package net.alis.protocoller.plugin.providers;

import net.alis.protocoller.plugin.events.AsyncPacketEventManager;
import net.alis.protocoller.plugin.events.SyncPacketEventManager;

public class EventManagersProvider {

    private final SyncPacketEventManager sync;
    private final AsyncPacketEventManager async;

    protected EventManagersProvider() {
        this.sync = new SyncPacketEventManager();
        this.async = new AsyncPacketEventManager();
    }

    public AsyncPacketEventManager getAsync() {
        return async;
    }

    public SyncPacketEventManager getSync() {
        return sync;
    }
}
