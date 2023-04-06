package net.alis.protocoller.plugin.network.netty.interceptors;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import net.alis.protocoller.plugin.events.AsyncPacketEventManager;
import net.alis.protocoller.plugin.events.SyncPacketEventManager;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.exception.PacketEventException;
import net.alis.protocoller.plugin.network.ProtocollerPlayer;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.event.synchronous.SyncPacketEvent;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

@ChannelHandler.Sharable
public class NettyPacketInterceptor extends ChannelDuplexHandler {

    private volatile Player player;
    private volatile NetworkPlayer networkPlayer;

    public NettyPacketInterceptor() {
        GlobalProvider.instance().getServer().getPacketInterceptors().add(this);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(networkPlayer != null) ((ProtocollerPlayer)networkPlayer).addSentPacket();
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
            PacketEventException.Builder exceptionBuilder = new ExceptionBuilder().getEventsExceptions().defineReason(e);
            if(event.getData() != null) {
                exceptionBuilder.readPacketError(event.getData()).showException();
            } else {
                exceptionBuilder.readPacketError(msg).showException();
            }
            return;
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, @NotNull Object msg, ChannelPromise promise) throws Exception {
        if(networkPlayer != null) ((ProtocollerPlayer)networkPlayer).addReceivedPacket();
        if(msg.getClass().getSimpleName().length() < 5) {
            super.write(ctx, msg, promise);
            return;
        }
        PacketDataContainer data = null;
        try {
            data = new PacketDataSerializer(msg, player);
        } catch (Exception e) {
            super.write(ctx, msg, promise);
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
            PacketEventException.Builder exceptionBuilder = new ExceptionBuilder().getEventsExceptions().defineReason(e);
            if(event.getData() != null) {
                exceptionBuilder.readPacketError(event.getData()).showException();
            } else {
                exceptionBuilder.readPacketError(msg).showException();
            }
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
