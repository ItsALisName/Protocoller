package net.alis.protocoller.event;

import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.event.impl.PacketListener;

public interface PacketEventsManager {

    void registerListener(ProtocollerClient client, PacketListener listener);

    void registerListeners(ProtocollerClient client, PacketListener... listeners);

}
