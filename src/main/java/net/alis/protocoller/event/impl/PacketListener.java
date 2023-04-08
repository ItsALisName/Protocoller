package net.alis.protocoller.event.impl;

import net.alis.protocoller.event.sync.*;
import net.alis.protocoller.event.async.*;

public interface PacketListener {

    @PacketEventHandler
    default void onPacketPlayReceive(PacketPlayReceiveEvent event) { }

    @PacketEventHandler
    default void onPacketPlaySend(PacketPlaySendEvent event) { }

    @PacketEventHandler
    default void onPacketLoginReceive(PacketLoginReceiveEvent event) { }

    @PacketEventHandler
    default void onPacketLoginSend(PacketLoginSendEvent event) { }

    @PacketEventHandler
    default void onPacketHandshakeReceive(PacketHandshakeReceiveEvent event) { }

    @PacketEventHandler
    default void onPacketStatusReceive(PacketStatusReceiveEvent event) { }

    @PacketEventHandler
    default void onPacketStatusSend(PacketStatusSendEvent event) { }

    @PacketEventHandler
    default void onAsyncPacketPlayReceive(AsyncPacketPlayReceiveEvent event) { }

    @PacketEventHandler
    default void onAsyncPacketPlaySend(AsyncPacketPlaySendEvent event) { }

    @PacketEventHandler
    default void onAsyncPacketLoginReceive(AsyncPacketLoginReceiveEvent event) { }

    @PacketEventHandler
    default void onAsyncPacketLoginSend(AsyncPacketLoginSendEvent event) { }

    @PacketEventHandler
    default void onAsyncPacketHandshakeReceive(AsyncPacketHandshakeReceiveEvent event) { }

    @PacketEventHandler
    default void onAsyncPacketStatusReceive(AsyncPacketStatusReceiveEvent event) { }

    @PacketEventHandler
    default void onAsyncPacketStatusSend(AsyncPacketStatusSendEvent event) { }
}
