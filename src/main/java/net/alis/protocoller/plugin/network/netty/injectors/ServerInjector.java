package net.alis.protocoller.plugin.network.netty.injectors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.network.netty.ChannelInjector;
import net.alis.protocoller.plugin.network.netty.NettyHelper;
import net.alis.protocoller.plugin.network.netty.initializers.NettyChannelInitializer;

import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.NetworkManager;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.ArrayList;
import java.util.List;

public class ServerInjector implements ChannelInjector.ServerInjector {

    private final List<ChannelFuture> injectedFutures = new ArrayList<>();

    @Override
    public void inject() {
        List<ChannelFuture> futures = GlobalProvider.instance().getServer().getConnection().getChannels();
        synchronized (futures) {
            for(ChannelFuture channelFuture : futures) {
                if(!injectedFutures.contains(channelFuture)) {
                    injectChannelFuture(channelFuture);
                }
            }
        }
        for(NetworkManager manager : GlobalProvider.instance().getServer().getConnection().getConnections()){
            NettyHelper.ejectChannel(manager.getChannel());
            NettyHelper.injectChannel(manager.getChannel());
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
                PluginDescriptionFile errorSourceDescription = Reflect.readField(loader, "description", false);
                new ExceptionBuilder().getInjectExceptions().defineReason(e).failedChannelFutureInject(errorSourceDescription).throwException();
            } else {
                new ExceptionBuilder().getInjectExceptions().failedChannelFutureInject().throwException();
            }
        }

    }
}