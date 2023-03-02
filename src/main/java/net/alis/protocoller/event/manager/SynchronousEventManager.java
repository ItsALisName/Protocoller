package net.alis.protocoller.event.manager;

import net.alis.protocoller.entity.ApiUser;
import net.alis.protocoller.event.impl.PacketListener;

public interface SynchronousEventManager {

    void registerListener(ApiUser user, PacketListener listener);

    void registerListeners(ApiUser user, PacketListener... listeners);

}
