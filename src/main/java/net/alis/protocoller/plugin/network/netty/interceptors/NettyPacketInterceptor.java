package net.alis.protocoller.plugin.network.netty.interceptors;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import net.alis.protocoller.plugin.events.AsyncPacketEventManager;
import net.alis.protocoller.plugin.events.SyncPacketEventManager;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.synchronous.SyncPacketEvent;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Arrays;

@ChannelHandler.Sharable
public class NettyPacketInterceptor extends ChannelDuplexHandler {

    private volatile Player player;
    private volatile NetworkPlayer networkPlayer;

    public NettyPacketInterceptor() {
        GlobalProvider.instance().getServer().getPacketInterceptors().add(this);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg.getClass().getSimpleName().length() < 5) {
            super.channelRead(ctx, msg);
            return;
        }
        if(msg instanceof ByteBuf) {
            super.channelRead(ctx, msg);
            return;
        }
        PacketDataContainer data = null;
        try {
            data = new PacketDataSerializer(msg, player);
        } catch (Exception e) {
            super.channelRead(ctx, msg);
            TaskSimplifier.INSTANCE.preformAsync(() -> {
                String[] dataReport = new String[(msg.getClass().getDeclaredFields().length)];
                for(int i = 0; i < msg.getClass().getDeclaredFields().length; i++) {
                    Field field = msg.getClass().getDeclaredFields()[i]; field.setAccessible(true);
                    dataReport[i] = "'" + field.getName() + "/" + field.getType().getSimpleName() + "/" + BaseReflection.readField(msg, field);
                }
                LogsManager.get().warning(
                        "Failed to recognize the \"" + msg.getClass().getSimpleName() + "\" packet! Skipping it...",
                        "Full class: " + msg.getClass().getName(),
                        "Packet data[Name > Type > Value]: " + String.join(", ", dataReport),
                        "(Please report about this)"
                );
            });
            return;
        }
        if(data.getType().getState() == PacketType.State.PLAY_CLIENTBOUND && player == null) {
            super.channelRead(ctx, msg);
            return;
        }
        AsyncPacketEventManager.callListeners(data, ctx.channel(), (InetSocketAddress) ctx.channel().remoteAddress(), player, networkPlayer);
        SyncPacketEvent event = SyncPacketEventManager.callListeners(data, ctx.channel(), (InetSocketAddress) ctx.channel().remoteAddress(), player, networkPlayer);
        if(event == null) {
            super.channelRead(ctx, msg);
            return;
        }
        try {
            super.channelRead(ctx, event.getData().getRawPacket());
            return;
        } catch (Exception e) {
            super.channelRead(ctx, msg);
            LogsManager.get().fatal(
                    "An error occurred during the registration of the packet (ON_PACKET_RECEIVE)! Report this error to the author: https://vk.com/alphatwo",
                    "Reason: \"" + e.getCause().getMessage() + "\""
            );
            LogsManager.get().fatal(Arrays.toString(e.getStackTrace()));
            LogsManager.get().fatal(
                    "An error occurred during the registration of the packet (ON_PACKET_RECEIVE)! Report this error to the author: https://vk.com/alphatwo",
                    "Reason: \"" + e.getCause().getMessage() + "\""
            );
            return;
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, @NotNull Object msg, ChannelPromise promise) throws Exception {
        if(msg.getClass().getSimpleName().length() < 5) {
            super.write(ctx, msg, promise);
            return;
        }
        PacketDataContainer data = null;
        try {
            data = new PacketDataSerializer(msg, player);
        } catch (Exception e) {
            super.write(ctx, msg, promise);
            TaskSimplifier.INSTANCE.preformAsync(() -> {
                String[] dataReport = new String[(msg.getClass().getDeclaredFields().length)];
                for(int i = 0; i < msg.getClass().getDeclaredFields().length; i++) {
                    Field field = msg.getClass().getDeclaredFields()[i]; field.setAccessible(true);
                    dataReport[i] = "'" + field.getName() + "/" + field.getType().getSimpleName() + "/" + BaseReflection.readField(msg, field);
                }
                LogsManager.get().warning(
                        "Failed to recognize the \"" + msg.getClass().getSimpleName() + "\" packet! Skipping it...",
                        "Full class: " + msg.getClass().getName(),
                        "Packet data[Name > Type > Value]: " + String.join(", ", dataReport),
                        "(Please report about this)"
                );
            });
            return;
        }
        if((data.getType().getState() == PacketType.State.PLAY_SERVERBOUND || data.getType().getState() == PacketType.State.CLIENTBOUND) && this.player == null) {
            super.write(ctx, msg, promise);
            return;
        }
        AsyncPacketEventManager.callListeners(data, ctx.channel(), (InetSocketAddress) ctx.channel().remoteAddress(), player, networkPlayer);
        SyncPacketEvent event = SyncPacketEventManager.callListeners(data, ctx.channel(), (InetSocketAddress) ctx.channel().remoteAddress(), player, networkPlayer);
        if(event == null) {
            super.write(ctx, msg, promise);
            return;
        }
        try {
            super.write(ctx, event.getData().getRawPacket(), promise);
            return;
        } catch (Exception e) {
            super.write(ctx, msg, promise);
            LogsManager.get().fatal(
                    "An error occurred during the registration of the packet (ON_PACKET_RECEIVE)! Report this error to the author: https://vk.com/alphatwo",
                    "Reason: \"" + e.getCause().getMessage() + "\""
            );
            LogsManager.get().fatal(Arrays.toString(e.getStackTrace()));
            LogsManager.get().fatal(
                    "An error occurred during the registration of the packet (ON_PACKET_RECEIVE)! Report this error to the author: https://vk.com/alphatwo",
                    "Reason: \"" + e.getCause().getMessage() + "\""
            );
            return;
        }
    }



    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setNetworkPlayer(NetworkPlayer networkPlayer) {
        this.networkPlayer = networkPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public NetworkPlayer getNetworkPlayer() {
        return networkPlayer;
    }
}
