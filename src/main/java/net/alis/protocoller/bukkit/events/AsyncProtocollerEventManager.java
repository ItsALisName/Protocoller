package net.alis.protocoller.bukkit.events;

import io.netty.channel.Channel;
import net.alis.protocoller.bukkit.exceptions.PacketEventException;
import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.bukkit.util.TaskSimplifier;
import net.alis.protocoller.bukkit.util.Utils;
import net.alis.protocoller.ApiUser;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.RegisteredPacketListener;
import net.alis.protocoller.event.asynchronous.*;
import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.event.manager.AsynchronousEventManager;
import net.alis.protocoller.packet.PacketDataSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class AsyncProtocollerEventManager implements AsynchronousEventManager {

    @Override
    public void registerListener(ApiUser user, PacketListener listener) {
        TaskSimplifier.INSTANCE.preformAsync(() -> {
            Thread.currentThread().setName("RegistrationThread-" + Utils.generateRandomInt(15));
            int methodsCount = 0;
            for(Method method : listener.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                Class<?> parameterType = method.getParameterTypes()[0];
                if(parameterType == AsyncPacketPlayReceiveEvent.class || parameterType == AsyncPacketPlaySendEvent.class || parameterType == AsyncPacketLoginReceiveEvent.class || parameterType == AsyncPacketLoginSendEvent.class || parameterType == AsyncPacketHandshakeReceiveEvent.class || parameterType == AsyncPacketStatusReceiveEvent.class || parameterType == AsyncPacketStatusSendEvent.class) {
                    PacketEventHandler annotation = method.getAnnotation(PacketEventHandler.class);
                    if(annotation == null) {
                        new PacketEventException("An unannotated method '" + method.getName() + "' was found while registering packet event listener '" + listener.getClass().getSimpleName() + "'").printStackTrace();
                        continue;
                    }
                    ListenerType lt = ListenerType.fromClass(parameterType);
                    RegisteredProtocollerListener protocollerListener = new RegisteredProtocollerListener(user, listener, annotation.ignoreCancelled(), annotation.eventPriority(), method);
                    switch (lt) {
                        case PPRE: AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case PPSE: AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case PLRE: AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case PLSE: AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case PSRE: AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case PSSE: AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                        case PHRE: AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners().add(protocollerListener); break;
                    }
                    LogsManager.get().sendRegisteredListenerMessage(user.getName(), user.getVersion(), lt.inName);
                    methodsCount += 1;

                }
            }
            if(methodsCount == 0) {
                LogsManager.get().getLogger(Thread.currentThread()).warn("No methods found listening for packets during registration of packet listener class '" + listener.getClass().getSimpleName() + "'");
            }
        });
    }

    @Override
    public void registerListeners(ApiUser user, PacketListener... listeners) {
        for(PacketListener listener : listeners) this.registerListener(user, listener);
    }

    protected enum ListenerType {
        PPRE("AsyncAsyncPacketPlayReceiveEvent"), PPSE("AsyncPacketPlaySendEvent"), PLSE("AsyncPacketLoginSendEvent"), PLRE("AsyncPacketLoginReceiveEvent"), PSSE("AsyncPacketStatusSendEvent"), PSRE("AsyncPacketStatusReceiveEvent"), PHRE("AsyncPacketHandshakeReceiveEvent"), UNKNOWN("Unknown");

        private final String inName;
        ListenerType(String inName) {
            this.inName = inName;
        }

        static ListenerType fromClass(Class<?> clazz) {
            if(clazz == AsyncPacketPlayReceiveEvent.class) return PPRE;
            if(clazz == AsyncPacketPlaySendEvent.class) return PPSE;
            if(clazz == AsyncPacketLoginReceiveEvent.class) return PLRE;
            if(clazz == AsyncPacketLoginSendEvent.class) return PLSE;
            if(clazz == AsyncPacketHandshakeReceiveEvent.class) return PHRE;
            if(clazz == AsyncPacketStatusReceiveEvent.class) return PSRE;
            if(clazz == AsyncPacketStatusSendEvent.class) return PSSE;
            return UNKNOWN;
        }
    }
    
    public static void callListeners(PacketDataSerializer data, Channel channel, InetSocketAddress address, @Nullable Player player, @Nullable NetworkPlayer networkPlayer) {
        TaskSimplifier.INSTANCE.preformAsync(() -> {
            switch (data.getType().getState()) {
                case PLAY_OUT_CLIENTBOUND: {
                    if(player == null) {
                        throw new PacketEventException("An error occurred while trying to initialize the event 'AsyncPacketPlaySendEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                    }
                    AsyncPacketPlaySendEvent event = new AsyncPacketPlaySendEvent(data, channel, address, player, networkPlayer);
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    return;
                }
                case PLAY_IN: {
                    if(player == null) {
                        throw new PacketEventException("An error occurred while trying to initialize the event 'AsyncPacketPlayReceiveEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                    }
                    AsyncPacketPlayReceiveEvent event = new AsyncPacketPlayReceiveEvent(data, channel, address, player, networkPlayer);
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {
                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {
                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {
                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {
                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {
                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    return;
                }
                case PLAY_OUT: {
                    if(player == null) {
                        throw new PacketEventException("An error occurred while trying to initialize the event 'AsyncPacketPlaySendEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                    }
                    AsyncPacketPlaySendEvent event = new AsyncPacketPlaySendEvent(data, channel, address, player, networkPlayer);
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    return;
                }
                case LOGIN_IN: {
                    AsyncPacketLoginReceiveEvent event = new AsyncPacketLoginReceiveEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    return;
                }
                case LOGIN_OUT: {
                    AsyncPacketLoginSendEvent event = new AsyncPacketLoginSendEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    return;
                }
                case STATUS_IN: {
                    AsyncPacketStatusReceiveEvent event = new AsyncPacketStatusReceiveEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    return;
                }
                case STATUS_OUT: {
                    AsyncPacketStatusSendEvent event = new AsyncPacketStatusSendEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    return;
                }
                case HANDSHAKE_IN: {
                    AsyncPacketHandshakeReceiveEvent event = new AsyncPacketHandshakeReceiveEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {
                            Reflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                        }
                    }
                    return;
                }
            }
            throw new PacketEventException("An error occurred while trying to initialize the event 'AsynchronousPacketEvent'! Inform the author about this: https://vk.com/alphatwo");
        });
    }
}
