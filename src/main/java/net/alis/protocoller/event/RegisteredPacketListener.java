package net.alis.protocoller.event;

import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.event.impl.PacketEventPriority;

public interface RegisteredPacketListener {

    ProtocollerClient getClient();

    boolean isIgnoreCancelled();

    PacketEventPriority getPriority();

}
