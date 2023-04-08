package net.alis.protocoller.plugin.events;

import io.netty.channel.Channel;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.providers.ApiProvider;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.event.RegisteredPacketListener;
import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.sync.*;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.packet.PacketDataContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class SyncPacketEventManager implements PacketEventsManager {

    @Override
    public void registerListener(ProtocollerClient client, PacketListener listener) {
        TaskSimplifier.get().preformAsync(() -> {
            int methodsCount = 0;
            for(Method method : listener.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                Class<?> parameterType = method.getParameterTypes()[0];
                if(parameterType == PacketPlayReceiveEvent.class || parameterType == PacketPlaySendEvent.class || parameterType == PacketLoginReceiveEvent.class || parameterType == PacketLoginSendEvent.class || parameterType == PacketHandshakeReceiveEvent.class || parameterType == PacketStatusReceiveEvent.class || parameterType == PacketStatusSendEvent.class) {
                    PacketEventHandler annotation = method.getAnnotation(PacketEventHandler.class);
                    if(annotation == null) {
                        LogsManager.get().getLogger().warn("An unannotated method '" + method.getName() + "' was found while registering packet listener '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getSimpleName() + "'");
                        continue;
                    }
                    ListenerType lt = ListenerType.fromClass(parameterType);
                    RegisteredProtocollerListener protocollerListener = new RegisteredProtocollerListener(client, listener, annotation.ignoreCancelled(), annotation.eventPriority(), method);
                    switch (lt) {
                        case PRE: PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case PSE: PacketPlaySendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case LRE: PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case LSE: PacketLoginSendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case SRE: PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case SSE: PacketStatusSendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case HRE: PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                    }
                    LogsManager.get().sendRegisteredListenerMessage(client.getName(), ((ApiProvider)client).getVersion(), lt.inName);
                    ((ApiProvider)client).addListenerCount();
                    methodsCount += 1;

                }
            }
            if(methodsCount == 0) {
                LogsManager.get().getLogger().warn("No methods found listening for packets during registration of packet listener class '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getSimpleName() + "'");
            }
        });
    }

    @Override
    public void registerListeners(ProtocollerClient client, PacketListener @NotNull ... listeners) {
        for(PacketListener listener : listeners) this.registerListener(client, listener);
    }

    protected enum ListenerType {
        PRE("PacketPlayReceiveEvent"), PSE("PacketPlaySendEvent"), LSE("PacketLoginSendEvent"), LRE("PacketLoginReceiveEvent"), SSE("PacketStatusSendEvent"), SRE("PacketStatusReceiveEvent"), HRE("PacketHandshakeReceiveEvent"), UNKNOWN("Unknown");

        private final String inName;

        ListenerType(String inName) {
            this.inName = inName;
        }
        static ListenerType fromClass(Class<?> clazz) {
            if(clazz == PacketPlayReceiveEvent.class) return PRE;
            if(clazz == PacketPlaySendEvent.class) return PSE;
            if(clazz == PacketLoginReceiveEvent.class) return LRE;
            if(clazz == PacketLoginSendEvent.class) return LSE;
            if(clazz == PacketHandshakeReceiveEvent.class) return HRE;
            if(clazz == PacketStatusReceiveEvent.class) return SRE;
            if(clazz == PacketStatusSendEvent.class) return SSE;
            return UNKNOWN;
        }

    }

    public static @Nullable SyncPacketEvent callListeners(@NotNull PacketDataContainer data, Channel channel, InetSocketAddress address, @Nullable Player player, @Nullable NetworkPlayer networkPlayer) {
        switch (data.getType().getState()) {
            case CLIENTBOUND: {
                if(player == null) {
                    new ExceptionBuilder().getEventsExceptions().processedNullPlayer("PacketPlaySendEvent", data).showException();
                }
                PacketPlaySendEvent event = new PacketPlaySendEvent(data, channel, address, player, networkPlayer);
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        }catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                return event;
            }
            case PLAY_CLIENTBOUND: {
                if(player == null) {
                    new ExceptionBuilder().getEventsExceptions().processedNullPlayer("PacketPlayReceiveEvent", data).showException();
                }
                PacketPlayReceiveEvent event = new PacketPlayReceiveEvent(data, channel, address, player, networkPlayer);
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlayReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlayReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlayReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlayReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlayReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlayReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                return event;
            }
            case PLAY_SERVERBOUND: {
                if(player == null) {
                    new ExceptionBuilder().getEventsExceptions().processedNullPlayer("PacketPlaySendEvent", data).showException();
                }
                PacketPlaySendEvent event = new PacketPlaySendEvent(data, channel, address, player, networkPlayer);
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketPlaySendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                return event;
            }
            case LOGIN_CLIENTBOUND: {
                PacketLoginReceiveEvent event = new PacketLoginReceiveEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try{
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                return event;
            }
            case LOGIN_SERVERBOUND: {
                PacketLoginSendEvent event = new PacketLoginSendEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketLoginSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                return event;
            }
            case STATUS_CLIENTBOUND: {
                PacketStatusReceiveEvent event = new PacketStatusReceiveEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                return event;
            }
            case STATUS_SERVERBOUND: {
                PacketStatusSendEvent event = new PacketStatusSendEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketStatusSendEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                return event;
            }
            case HANDSHAKE_CLIENTBOUND: {
                PacketHandshakeReceiveEvent event = new PacketHandshakeReceiveEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketHandshakeReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketHandshakeReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketHandshakeReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketHandshakeReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketHandshakeReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        try {
                            callMethod(((RegisteredProtocollerListener) listener).getListener(), ((RegisteredProtocollerListener) listener).getMethod(), event);
                        } catch (Exception e) {
                            new ExceptionBuilder().getEventsExceptions()
                                    .defineReason(e.getCause())
                                    .changeStackTrace(e.getCause().getStackTrace())
                                    .callEventError("PacketHandshakeReceiveEvent", ((RegisteredProtocollerListener)listener).getListener().getClass(), listener.getClient(), event.getData())
                                    .showException();
                        }
                    }
                }
                return event;
            }
        }
        new ExceptionBuilder().getEventsExceptions().callEventUnknownError("SynchronousPacketEvent").showException();
        return null;
    }

    private static void callMethod(Object instance, @NotNull Method method, Object... parameters) throws Exception {
        method.invoke(instance, parameters);
    }
    
}
