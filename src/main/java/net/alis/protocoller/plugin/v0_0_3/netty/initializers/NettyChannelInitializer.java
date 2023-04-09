package net.alis.protocoller.plugin.v0_0_3.netty.initializers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import net.alis.protocoller.plugin.v0_0_3.netty.NettyHelper;
import net.alis.protocoller.plugin.util.reflection.Reflect;

import java.lang.reflect.Method;

@ChannelHandler.Sharable
public class NettyChannelInitializer extends ChannelInitializer<Channel> {

    private final ChannelInitializer<?> original;
    private final Method sourceMethod;

    public NettyChannelInitializer(ChannelInitializer<?> originalChannelInitializer) {
        this.original = originalChannelInitializer;
        sourceMethod = Reflect.getMethod(original.getClass(), 0, "initChannel");
    }

    public ChannelInitializer<?> getOriginal() {
        return original;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        NettyHelper.tryInitChannel(original, sourceMethod, channel);
    }

}
