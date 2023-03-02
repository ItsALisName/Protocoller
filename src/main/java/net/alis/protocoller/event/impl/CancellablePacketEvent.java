package net.alis.protocoller.event.impl;

public interface CancellablePacketEvent {

    boolean isCancelled();

    void setCancelled(boolean cancelled);

}
