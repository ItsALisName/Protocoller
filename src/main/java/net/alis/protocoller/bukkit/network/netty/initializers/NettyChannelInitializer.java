package net.alis.protocoller.bukkit.network.netty.initializers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import net.alis.protocoller.bukkit.network.netty.NettyHelper;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

import java.lang.reflect.Method;

@ChannelHandler.Sharable
public class NettyChannelInitializer extends ChannelInitializer<Channel> {

    private final ChannelInitializer<?> original;
    private final Method sourceMethod;

    public NettyChannelInitializer(ChannelInitializer<?> originalChannelInitializer) {
        this.original = originalChannelInitializer;
        sourceMethod = Reflection.getMethod(original.getClass(), 0, "initChannel");
    }

    public ChannelInitializer<?> getOriginal() {
        return original;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        NettyHelper.tryInitChannel(original, sourceMethod, channel);
    }

}
