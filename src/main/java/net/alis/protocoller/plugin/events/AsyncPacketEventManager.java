package net.alis.protocoller.plugin.events;

import io.netty.channel.Channel;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.providers.ApiProvider;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import net.alis.protocoller.ApiUser;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.event.RegisteredPacketListener;
import net.alis.protocoller.event.asynchronous.*;
import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.event.manager.AsynchronousEventManager;
import net.alis.protocoller.packet.PacketDataContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class AsyncPacketEventManager implements AsynchronousEventManager {

    @Override
    public void registerListener(ApiUser user, PacketListener listener) {
        TaskSimplifier.get().preformAsync(() -> {
            int methodsCount = 0;
            for(Method method : listener.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                Class<?> parameterType = method.getParameterTypes()[0];
                if(parameterType == AsyncPacketPlayReceiveEvent.class || parameterType == AsyncPacketPlaySendEvent.class || parameterType == AsyncPacketLoginReceiveEvent.class || parameterType == AsyncPacketLoginSendEvent.class || parameterType == AsyncPacketHandshakeReceiveEvent.class || parameterType == AsyncPacketStatusReceiveEvent.class || parameterType == AsyncPacketStatusSendEvent.class) {
                    PacketEventHandler annotation = method.getAnnotation(PacketEventHandler.class);
                    if(annotation == null) {
                        LogsManager.get().getLogger().warn("An unannotated method '" + method.getName() + "' was found while registering packet listener '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getSimpleName() + "'");
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
                    ((ApiProvider)user).addListenerCount();
                    methodsCount += 1;

                }
            }
            if(methodsCount == 0) {
                LogsManager.get().getLogger().warn("No methods found listening for packets during registration of packet listener class '" + listener.getClass().getSimpleName() + "'");
            }
        });
    }

    @Override
    public void registerListeners(ApiUser user, PacketListener... listeners) {
        for(PacketListener listener : listeners) this.registerListener(user, listener);
    }

    protected enum ListenerType {
        PPRE("AsyncAsyncPacketPlayReceiveEvent"),
        PPSE("AsyncPacketPlaySendEvent"),
        PLSE("AsyncPacketLoginSendEvent"),
        PLRE("AsyncPacketLoginReceiveEvent"),
        PSSE("AsyncPacketStatusSendEvent"),
        PSRE("AsyncPacketStatusReceiveEvent"),
        PHRE("AsyncPacketHandshakeReceiveEvent"),
        UNKNOWN("Unknown");

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
    
    public static void callListeners(PacketDataContainer data, Channel channel, InetSocketAddress address, @Nullable Player player, @Nullable NetworkPlayer networkPlayer) {
        TaskSimplifier.get().preformAsync(() -> {
            switch (data.getType().getState()) {
                case CLIENTBOUND: {
                    if(player == null) {
                        LogsManager.get().getLogger().error("An error occurred while trying to initialize the event 'AsyncPacketPlaySendEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                    }
                    AsyncPacketPlaySendEvent event = new AsyncPacketPlaySendEvent(data, channel, address, player, networkPlayer);
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    return;
                }
                case PLAY_CLIENTBOUND: {
                    if(player == null) {
                        LogsManager.get().getLogger().error("An error occurred while trying to initialize the event 'AsyncPacketPlayReceiveEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                    }
                    AsyncPacketPlayReceiveEvent event = new AsyncPacketPlayReceiveEvent(data, channel, address, player, networkPlayer);
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlayReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    return;
                }
                case PLAY_SERVERBOUND: {
                    if(player == null) {
                        LogsManager.get().getLogger().error("An error occurred while trying to initialize the event 'AsyncPacketPlaySendEvent' (PLAYER_IS_NULL)! Inform the author about this: https://vk.com/alphatwo");
                    }
                    AsyncPacketPlaySendEvent event = new AsyncPacketPlaySendEvent(data, channel, address, player, networkPlayer);
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketPlaySendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    return;
                }
                case LOGIN_CLIENTBOUND: {
                    AsyncPacketLoginReceiveEvent event = new AsyncPacketLoginReceiveEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    return;
                }
                case LOGIN_SERVERBOUND: {
                    AsyncPacketLoginSendEvent event = new AsyncPacketLoginSendEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketLoginSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    return;
                }
                case STATUS_CLIENTBOUND: {
                    AsyncPacketStatusReceiveEvent event = new AsyncPacketStatusReceiveEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    return;
                }
                case STATUS_SERVERBOUND: {
                    AsyncPacketStatusSendEvent event = new AsyncPacketStatusSendEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketStatusSendEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    return;
                }
                case HANDSHAKE_CLIENTBOUND: {
                    AsyncPacketHandshakeReceiveEvent event = new AsyncPacketHandshakeReceiveEvent(data, channel, address);
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOWEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.LOW) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.NORMAL) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGH) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.HIGHEST) {

                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    for(RegisteredPacketListener listener : AsyncPacketHandshakeReceiveEvent.getHandlerList().getRegisteredListeners()) {
                        if(listener.getPriority() == PacketEventPriority.MONITOR) {
                            try {
                                BaseReflection.callMethod(((RegisteredProtocollerListener)listener).getListener(), ((RegisteredProtocollerListener)listener).getMethod(), event);
                            }catch (Exception e) {
                                LogsManager.get().getLogger().error("Failed to call packet event for listener - '" + ((RegisteredProtocollerListener)listener).getListener().getClass().getName() + "'");
                                LogsManager.get().getLogger().error("Reason: " + e.getMessage());
                            }
                        }
                    }
                    return;
                }
            }
            LogsManager.get().getLogger().error("An error occurred while trying to initialize the event 'AsynchronousPacketEvent'! Inform the author about this: https://vk.com/alphatwo");
        });
    }
}
