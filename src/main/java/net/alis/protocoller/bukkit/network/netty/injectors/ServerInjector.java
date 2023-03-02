package net.alis.protocoller.bukkit.network.netty.injectors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;

import net.alis.protocoller.bukkit.network.netty.ChannelInjector;
import net.alis.protocoller.bukkit.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.bukkit.network.netty.initializers.NettyChannelInitializer;

import net.alis.protocoller.bukkit.providers.GlobalProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ServerInjector implements ChannelInjector.ServerInjector {

    private final List<ChannelFuture> injectedFutures = new ArrayList<>();

    @Override
    public void inject() {
        List<ChannelFuture> futures = GlobalProvider.instance().getServer().getChannelFutures();
        synchronized (futures) {
            for(ChannelFuture channelFuture : futures) {
                injectChannelFuture(channelFuture);
            }
        }
        for (Channel channel : GlobalProvider.instance().getServer().getChannels()) {
            if (channel == null || channel.getClass().getSimpleName().equalsIgnoreCase("FakeChannel")) {
                continue;
            }
            if (channel.pipeline().get("protocoller_handler") != null) {
                channel.pipeline().remove("protocoller_handler");
            }

            if (channel.pipeline().get("packet_handler") != null) {
                channel.pipeline().addBefore("packet_handler", "protocoller_handler", new NettyPacketInterceptor());
            }
        }
    }


    @Override
    public void eject() {
        Field childHandlerField = null;
        for (ChannelFuture future : injectedFutures) {
            List<String> names = future.channel().pipeline().names();
            ChannelHandler bootstrapAcceptor = null;
            for (String name : names) {
                try {
                    ChannelHandler handler = future.channel().pipeline().get(name);
                    if (childHandlerField == null) {
                        childHandlerField = handler.getClass().getDeclaredField("childHandler");
                        childHandlerField.setAccessible(true);
                    }
                    ChannelInitializer<Channel> oldInit = (ChannelInitializer<Channel>) childHandlerField.get(handler);
                    if (oldInit instanceof NettyChannelInitializer) bootstrapAcceptor = handler;
                } catch (Exception e) {}
            }
            if (bootstrapAcceptor == null) bootstrapAcceptor = future.channel().pipeline().first();
            try {
                ChannelInitializer<Channel> oldInit = (ChannelInitializer<Channel>) childHandlerField.get(bootstrapAcceptor);
                if (oldInit instanceof NettyChannelInitializer) {
                    childHandlerField.setAccessible(true);
                    childHandlerField.set(bootstrapAcceptor, ((NettyChannelInitializer) oldInit).getOriginal());
                }
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("Failed to eject the injection handler! Please reboot plugin!");
            }
        }
        injectedFutures.clear();
    }

    private void injectChannelFuture(ChannelFuture channelFuture) {
        List<String> channelHandlerNames = channelFuture.channel().pipeline().names();
        ChannelHandler bootstrapAcceptor = null;
        Field bootstrapAcceptorField = null;
        for (String handlerName : channelHandlerNames) {
            ChannelHandler handler = channelFuture.channel().pipeline().get(handlerName);
            try {
                bootstrapAcceptorField = handler.getClass().getDeclaredField("childHandler");
                bootstrapAcceptorField.setAccessible(true);
                bootstrapAcceptorField.get(handler);
                bootstrapAcceptor = handler;
            } catch (Exception ignored) {

            }
        }
        if (bootstrapAcceptor == null) bootstrapAcceptor = channelFuture.channel().pipeline().first();
        ChannelInitializer<?> originalChannelInitializer = null;
        try {
            originalChannelInitializer = (ChannelInitializer<?>) bootstrapAcceptorField.get(bootstrapAcceptor);
            ChannelInitializer<?> channelInitializer = new NettyChannelInitializer(originalChannelInitializer);
            bootstrapAcceptorField.setAccessible(true);
            bootstrapAcceptorField.set(bootstrapAcceptor, channelInitializer);
            injectedFutures.add(channelFuture);
        } catch (IllegalAccessException e) {
            ClassLoader cl = bootstrapAcceptor.getClass().getClassLoader();
            if (cl.getClass().getName().equals("org.bukkit.plugin.java.PluginClassLoader")) {
                PluginDescriptionFile yaml = null;
                try {
                    yaml = (PluginDescriptionFile) PluginDescriptionFile.class.getDeclaredField("description").get(cl);
                } catch (IllegalAccessException | NoSuchFieldException e2) {
                    e2.printStackTrace();
                }
                throw new IllegalStateException("Failed to inject, because of " + bootstrapAcceptor.getClass().getName() + ", you might want to try running without " + yaml.getName() + "?");
            } else {
                throw new IllegalStateException("Failed to find core component 'childHandler', please check your plugins. issue: " + bootstrapAcceptor.getClass().getName());
            }
        }

    }
}
