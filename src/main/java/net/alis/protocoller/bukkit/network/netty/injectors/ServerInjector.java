package net.alis.protocoller.bukkit.network.netty.injectors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;

import net.alis.protocoller.bukkit.network.netty.ChannelInjector;
import net.alis.protocoller.bukkit.network.netty.NettyHelper;
import net.alis.protocoller.bukkit.network.netty.initializers.NettyChannelInitializer;

import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.ArrayList;
import java.util.List;

public class ServerInjector implements ChannelInjector.ServerInjector {

    private final List<ChannelFuture> injectedFutures = new ArrayList<>();

    @Override
    public void inject() {
        List<ChannelFuture> futures = GlobalProvider.instance().getServer().getChannelFutures();
        synchronized (futures) {
            for(ChannelFuture channelFuture : futures) {
                if(!injectedFutures.contains(channelFuture)) {
                    injectChannelFuture(channelFuture);
                }
            }
        }
        for (Channel channel : GlobalProvider.instance().getServer().getChannels()) {
            NettyHelper.ejectChannel(channel);
            NettyHelper.injectChannel(channel);
        }
    }


    @Override
    public void eject() {
        for (ChannelFuture future : injectedFutures) {
            NettyHelper.ejectChannelFuture(future);
        }
        injectedFutures.clear();
    }

    private void injectChannelFuture(ChannelFuture channelFuture) {
        ChannelHandler bootstrapAcceptor = NettyHelper.getBootstrapAcceptor(channelFuture);
        try {
            ChannelInitializer<?> originalChannelInitializer = NettyHelper.getInitializerFromBootstrap(bootstrapAcceptor);
            ChannelInitializer<?> channelInitializer = new NettyChannelInitializer(originalChannelInitializer);
            NettyHelper.replaceInitializers(bootstrapAcceptor, channelInitializer);
            injectedFutures.add(channelFuture);
        } catch (Exception e) {
            ClassLoader loader = bootstrapAcceptor.getClass().getClassLoader();
            if (loader.getClass().getName().equalsIgnoreCase("org.bukkit.plugin.java.PluginClassLoader")) {
                PluginDescriptionFile errorSourceDescription = Reflection.readField(loader, "description");
                throw new RuntimeException("Failed to inject to ChannelFuture. Source of the error: \"" + bootstrapAcceptor.getClass().getSimpleName() + "\". Try to start the server without using: \"" + errorSourceDescription.getName() + "\"");
            } else {
                throw new IllegalStateException("Failed to inject to ChannelFuture. Source of the error: \"" + bootstrapAcceptor.getClass().getSimpleName() + "\"");
            }
        }

    }
}