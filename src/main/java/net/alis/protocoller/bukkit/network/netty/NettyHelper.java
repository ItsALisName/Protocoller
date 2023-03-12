package net.alis.protocoller.bukkit.network.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import net.alis.protocoller.bukkit.network.netty.initializers.NettyChannelInitializer;
import net.alis.protocoller.bukkit.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class NettyHelper {

    private static Field localField$0;

    public static void tryInitChannel(ChannelInitializer<?> source, Method sourceMethod, Channel newChannel) {
        Reflection.callMethod(source, sourceMethod, newChannel);
        injectChannel(newChannel);
    }

    public static void injectChannel(Channel channel) {
        if (!channel.getClass().getSimpleName().equalsIgnoreCase("FakeChannel") && channel.pipeline().get("packet_handler") != null) {
            if (channel.pipeline().get("protocoller_handler") == null) {
                channel.pipeline().addBefore("packet_handler", "protocoller_handler", new NettyPacketInterceptor());
            }
        }
    }

    public static void ejectChannel(Channel channel) {
        if (channel.pipeline().get("protocoller_handler") != null) {
            channel.pipeline().remove("protocoller_handler");
        }
    }

    public static ChannelHandler getBootstrapAcceptor(ChannelFuture future) {
        List<String> channelHandlerNames = future.channel().pipeline().names();
        ChannelHandler bootstrapAcceptor = null;
        for (String handlerName : channelHandlerNames) {
            ChannelHandler handler = future.channel().pipeline().get(handlerName);
            if(handler != null) {
                localField$0 = Reflection.getField(handler.getClass(), "childHandler");
                Reflection.readField(handler, localField$0);
                bootstrapAcceptor = handler;
            }
        }
        return bootstrapAcceptor == null ? future.channel().pipeline().first() : bootstrapAcceptor;
    }

    public static ChannelInitializer<?> getInitializerFromBootstrap(ChannelHandler bootstrapAcceptor) {
        return Reflection.readField(bootstrapAcceptor, localField$0);
    }

    public static void replaceInitializers(ChannelHandler bootstrapAcceptor, ChannelInitializer<?> newChannelInitializer) {
        Reflection.writeField(bootstrapAcceptor, localField$0, newChannelInitializer);
    }

    public static void ejectChannelFuture(ChannelFuture channelFuture) {
        ChannelHandler bootstrapAcceptor = getBootstrapAcceptor(channelFuture);
        List<String> handlersNames = channelFuture.channel().pipeline().names();
        for (String name : handlersNames) {
            try {
                ChannelHandler handler = channelFuture.channel().pipeline().get(name);
                ChannelInitializer<Channel> current = (ChannelInitializer<Channel>) localField$0.get(handler);
                if (current instanceof NettyChannelInitializer) bootstrapAcceptor = handler;
            } catch (Exception ignored) {}
        }
        try {
            ChannelInitializer<Channel> original = (ChannelInitializer<Channel>) localField$0.get(bootstrapAcceptor);
            if (original instanceof NettyChannelInitializer) {
                Reflection.writeField(bootstrapAcceptor, localField$0, ((NettyChannelInitializer) original).getOriginal());
            }
        } catch (Exception ignored) { }
    }

}
