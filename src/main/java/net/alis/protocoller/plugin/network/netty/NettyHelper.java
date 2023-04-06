package net.alis.protocoller.plugin.network.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import net.alis.protocoller.plugin.network.netty.initializers.NettyChannelInitializer;
import net.alis.protocoller.plugin.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class NettyHelper {

    private static Field localField$0;

    public static void tryInitChannel(ChannelInitializer<?> source, Method sourceMethod, Channel newChannel) {
        BaseReflection.callMethod(source, sourceMethod, false, newChannel);
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
                localField$0 = BaseReflection.getField(handler.getClass(), "childHandler", false);
                BaseReflection.readField(handler, localField$0, false);
                bootstrapAcceptor = handler;
            }
        }
        return bootstrapAcceptor == null ? future.channel().pipeline().first() : bootstrapAcceptor;
    }

    public static ChannelInitializer<?> getInitializerFromBootstrap(ChannelHandler bootstrapAcceptor) {
        return BaseReflection.readField(bootstrapAcceptor, localField$0, false);
    }

    public static void replaceInitializers(ChannelHandler bootstrapAcceptor, ChannelInitializer<?> newChannelInitializer) {
        BaseReflection.writeField(bootstrapAcceptor, localField$0, newChannelInitializer, false);
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
                BaseReflection.writeField(bootstrapAcceptor, localField$0, ((NettyChannelInitializer) original).getOriginal(), false);
            }
        } catch (Exception ignored) { }
    }

}
