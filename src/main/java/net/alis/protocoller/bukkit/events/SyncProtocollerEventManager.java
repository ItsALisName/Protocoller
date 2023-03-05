package net.alis.protocoller.bukkit.events;

import io.netty.channel.Channel;
import net.alis.protocoller.bukkit.exceptions.PacketEventException;
import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.bukkit.util.TaskSimplifier;
import net.alis.protocoller.bukkit.util.Utils;
import net.alis.protocoller.ApiUser;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.RegisteredPacketListener;
import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.synchronous.*;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.event.manager.SynchronousEventManager;
import net.alis.protocoller.packet.PacketDataContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class SyncProtocollerEventManager implements SynchronousEventManager {

    @Override
    public void registerListener(ApiUser user, PacketListener listener) {
        TaskSimplifier.INSTANCE.preformAsync(() -> {
            Thread.currentThread().setName("RegistrationThread-" + Utils.generateRandomInt(15));
            int methodsCount = 0;
            for(Method method : listener.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                Class<?> parameterType = method.getParameterTypes()[0];
                if(parameterType == PacketPlayReceiveEvent.class || parameterType == PacketPlaySendEvent.class || parameterType == PacketLoginReceiveEvent.class || parameterType == PacketLoginSendEvent.class || parameterType == PacketHandshakeReceiveEvent.class || parameterType == PacketStatusReceiveEvent.class || parameterType == PacketStatusSendEvent.class) {
                    PacketEventHandler annotation = method.getAnnotation(PacketEventHandler.class);
                    if(annotation == null) {
                        new PacketEventException("An unannotated method '" + method.getName() + "' was found while registering packet event listener '" + listener.getClass().getSimpleName() + "'").printStackTrace();
                        continue;
                    }
                    ListenerType lt = ListenerType.fromClass(parameterType);
                    RegisteredProtocollerListener protocollerListener = new RegisteredProtocollerListener(user, listener, annotation.ignoreCancelled(), annotation.eventPriority(), method);
                    switch (lt) {
                        case PRE: PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case PSE: PacketPlaySendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case LRE: PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case LSE: PacketLoginSendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case SRE: PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case SSE: PacketStatusSendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case HRE: PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                    }
                    LogsManager.get().sendRegisteredListenerMessage(user.getName(), user.getVersion(), lt.inName);
                    methodsCount += 1;

                }
            }
            if(methodsCount == 0) {
                LogsManager.get().getLogger().warn("No methods found listening for packets during registration of packet listener class '" + listener.getClass().getSimpleName() + "'");            } else {
            }
        });
    }

    @Override
    public void registerListeners(ApiUser user, PacketListener... listeners) {
        for(PacketListener listener : listeners) this.registerListener(user, listener);
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

    public static SyncPacketEvent callListeners(PacketDataContainer data, Channel channel, InetSocketAddress address, @Nullable Player player, @Nullable NetworkPlayer networkPlayer) {
        switch (data.getType().getState()) {
            case CLIENTBOUND: {
                if(player == null) {
                    throw new PacketEventException("An error occurred while trying to initialize the event 'PacketPlaySendEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                }
                PacketPlaySendEvent event = new PacketPlaySendEvent(data, channel, address, player, networkPlayer);
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                return event;
            }
            case PLAY_CLIENTBOUND: {
                if(player == null) {
                    throw new PacketEventException("An error occurred while trying to initialize the event 'PacketPlayReceiveEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                }
                PacketPlayReceiveEvent event = new PacketPlayReceiveEvent(data, channel, address, player, networkPlayer);
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                return event;
            }
            case PLAY_SERVERBOUND: {
                if(player == null) {
                    throw new PacketEventException("An error occurred while trying to initialize the event 'PacketPlaySendEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                }
                PacketPlaySendEvent event = new PacketPlaySendEvent(data, channel, address, player, networkPlayer);
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                return event;
            }
            case LOGIN_CLIENTBOUND: {
                PacketLoginReceiveEvent event = new PacketLoginReceiveEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                return event;
            }
            case LOGIN_SERVERBOUND: {
                PacketLoginSendEvent event = new PacketLoginSendEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                return event;
            }
            case STATUS_CLIENTBOUND: {
                PacketStatusReceiveEvent event = new PacketStatusReceiveEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                return event;
            }
            case STATUS_SERVERBOUND: {
                PacketStatusSendEvent event = new PacketStatusSendEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                return event;
            }
            case HANDSHAKE_CLIENTBOUND: {
                PacketHandshakeReceiveEvent event = new PacketHandshakeReceiveEvent(data, channel, address);
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOWEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.LOW) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.NORMAL) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGH) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                for(RegisteredPacketListener listener : PacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                    if(listener.getPriority() == PacketEventPriority.MONITOR) {
                        if(listener.isIgnoreCancelled() && event.isCancelled()) continue;
                        Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                    }
                }
                return event;
            }
        }
        throw new PacketEventException("An error occurred while trying to initialize the event 'SynchronousPacketEvent'! Inform the author about this: https://vk.com/alphatwo");
    }
}
