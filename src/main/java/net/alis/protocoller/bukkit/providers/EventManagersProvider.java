package net.alis.protocoller.bukkit.providers;

import net.alis.protocoller.bukkit.events.AsyncProtocollerEventManager;
import net.alis.protocoller.bukkit.events.SyncProtocollerEventManager;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.event.manager.AsynchronousEventManager;
import net.alis.protocoller.event.manager.SynchronousEventManager;

public class EventManagersProvider implements PacketEventsManager {

    private final SyncProtocollerEventManager sync;
    private final AsyncProtocollerEventManager async;

    protected EventManagersProvider() {
        this.sync = new SyncProtocollerEventManager();
        this.async = new AsyncProtocollerEventManager();
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
