package net.alis.protocoller.plugin.events;

import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.RegisteredPacketListener;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.plugin.providers.ApiProvider;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class RegisteredProtocollerListener implements RegisteredPacketListener {

    private final ProtocollerClient client;
    private final PacketListener listener;
    private final boolean ignoreCancelled;
    private final PacketEventPriority priority;
    private final Method method;

    public RegisteredProtocollerListener(ProtocollerClient client, PacketListener listener, boolean ignoreCancelled, PacketEventPriority priority, Method method) {
        this.client = client;
        this.listener = listener;
        this.ignoreCancelled = ignoreCancelled;
        this.priority = priority;
        this.method = method;
        ((ApiProvider)client).addListener(this);
    }

    @Override
    public ProtocollerClient getClient() {
        return client;
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

    public boolean equals(@NotNull RegisteredProtocollerListener listener) {
        return listener.method.getName().equalsIgnoreCase(getMethod().getName());
    }
}
