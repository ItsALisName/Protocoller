package net.alis.protocoller.event.manager;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.event.impl.PacketListener;

public interface AsynchronousEventManager {

    void registerListener(ApiUser user, PacketListener listener);

    void registerListeners(ApiUser user, PacketListener... listeners);

}
