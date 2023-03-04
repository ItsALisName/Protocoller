package net.alis.protocoller.bukkit.network.netty.interceptors;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import net.alis.protocoller.bukkit.events.AsyncProtocollerEventManager;
import net.alis.protocoller.bukkit.events.SyncProtocollerEventManager;
import net.alis.protocoller.bukkit.exceptions.PacketEventException;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.synchronous.SyncPacketEvent;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import org.bukkit.entity.Player;

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
        if(msg.getClass().getSimpleName().length() < 5) {
            super.channelRead(ctx, msg);
            return;
        }
        if(msg instanceof ByteBuf) {
            super.channelRead(ctx, msg);
            return;
        }
        PacketDataSerializer data = null;
        try {
            data = new PacketDataSerializer(msg);
        } catch (Exception e) {
            super.channelRead(ctx, msg);
            return;
        }
        if(data.getType().getState() == PacketType.State.PLAY_CLIENTBOUND && player == null) {
            super.channelRead(ctx, msg);
            return;
        }
        AsyncProtocollerEventManager.callListeners(data, ctx.channel(), (InetSocketAddress) ctx.channel().remoteAddress(), player, networkPlayer);
        SyncPacketEvent event = SyncProtocollerEventManager.callListeners(data, ctx.channel(), (InetSocketAddress) ctx.channel().remoteAddress(), player, networkPlayer);
        try {
            super.channelRead(ctx, event.getData().getRawPacket());
            return;
        } catch (Exception e) {
            super.channelRead(ctx, msg);
            throw new PacketEventException("An error occurred during the registration of the packet (ON_PACKET_RECEIVE)! Report this error to the author: https://vk.com/alphatwo");
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg.getClass().getSimpleName().length() < 5) {
            super.write(ctx, msg, promise);
            return;
        }
        PacketDataSerializer data = null;
        try {
            data = new PacketDataSerializer(msg);
        } catch (Exception e) {
            super.write(ctx, msg, promise);
            return;
        }
        if((data.getType().getState() == PacketType.State.PLAY_SERVERBOUND || data.getType().getState() == PacketType.State.CLIENTBOUND) && this.player == null) {
            super.write(ctx, msg, promise);
            return;
        }
        AsyncProtocollerEventManager.callListeners(data, ctx.channel(), (InetSocketAddress) ctx.channel().remoteAddress(), player, networkPlayer);
        SyncPacketEvent event = SyncProtocollerEventManager.callListeners(data, ctx.channel(), (InetSocketAddress) ctx.channel().remoteAddress(), player, networkPlayer);
        try {
            super.write(ctx, event.getData().getRawPacket(), promise);
            return;
        } catch (Exception e) {
            super.write(ctx, msg, promise);
            throw new PacketEventException("An error occurred during the registration of the packet (ON_PACKET_SEND)! Report this error to the author: https://vk.com/alphatwo");
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
