package net.alis.protocoller.event;

import net.alis.protocoller.entity.ApiUser;
import net.alis.protocoller.event.impl.PacketEventPriority;

public interface RegisteredPacketListener {

    ApiUser getUser();

    boolean isIgnoreCancelled();

    PacketEventPriority getPriority();

}
