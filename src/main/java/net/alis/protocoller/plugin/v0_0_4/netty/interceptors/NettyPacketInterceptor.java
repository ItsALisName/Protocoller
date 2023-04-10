package net.alis.protocoller.plugin.v0_0_4.netty.interceptors;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import net.alis.protocoller.packet.packets.handshake.PacketHandshakingInSetProtocol;
import net.alis.protocoller.plugin.events.AsyncPacketEventManager;
import net.alis.protocoller.plugin.events.SyncPacketEventManager;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.exception.PacketEventException;
import net.alis.protocoller.plugin.v0_0_4.entity.ProtocollerPlayer;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.event.sync.SyncPacketEvent;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.plugin.util.ProtocolUtil;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

@ChannelHandler.Sharable
public class NettyPacketInterceptor extends ChannelDuplexHandler {

    private volatile Player player;
    private volatile NetworkPlayer networkPlayer;

    public NettyPacketInterceptor() {
        GlobalProvider.get().getServer().getPacketInterceptors().add(this);
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
        if(!ProtocolUtil.viaVersionInstalled){
            if (data.getType() == PacketType.Handshake.Client.SET_PROTOCOL) {
                PacketHandshakingInSetProtocol protocol = new PacketHandshakingInSetProtocol(data);
                if (!ProtocolUtil.TEMP_PROTOCOL_MAP.containsKey(protocol.getAddress())) {
                    ProtocolUtil.TEMP_PROTOCOL_MAP.put(protocol.getAddress(), protocol.getProtocolVersion());
                    TaskSimplifier.get().preformAsyncLater(() -> ProtocolUtil.TEMP_PROTOCOL_MAP.remove(protocol.getAddress()), 300L);
                }
            }
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



    public void setPlayer(@NotNull NetworkPlayer player) {
        this.networkPlayer = player;
        this.player = player.getBukkitPlayer();
    }

    public Player getPlayer() {
        return player;
    }

    public NetworkPlayer getNetworkPlayer() {
        return networkPlayer;
    }
}
