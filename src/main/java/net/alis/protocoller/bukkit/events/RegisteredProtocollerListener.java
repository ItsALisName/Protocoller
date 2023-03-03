package net.alis.protocoller.bukkit.events;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.RegisteredPacketListener;
import net.alis.protocoller.event.impl.PacketListener;

import java.lang.reflect.Method;

public class RegisteredProtocollerListener implements RegisteredPacketListener {

    private final ApiUser user;
    private final PacketListener listener;
    private final boolean ignoreCancelled;
    private final PacketEventPriority priority;
    private final Method method;

    public RegisteredProtocollerListener(ApiUser user, PacketListener listener, boolean ignoreCancelled, PacketEventPriority priority, Method method) {
        this.user = user;
        this.listener = listener;
        this.ignoreCancelled = ignoreCancelled;
        this.priority = priority;
        this.method = method;
    }

    @Override
    public ApiUser getUser() {
        return user;
    }

    @Override
    public boolean isIgnoreCancelled() {
        return ignoreCancelled;
    }

    @Override
    public PacketEventPriority getPriority() {
        return priority;
    }

    public Method getMethod() {
        return method;
    }

    public PacketListener getListener() {
        return listener;
    }
}
