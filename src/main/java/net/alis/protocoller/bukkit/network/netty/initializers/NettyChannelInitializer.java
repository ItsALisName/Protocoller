package net.alis.protocoller.bukkit.network.netty.initializers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import net.alis.protocoller.bukkit.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;

/* PacketEventsAPI class */
public class NettyChannelInitializer extends ChannelInitializer {

    private final ChannelInitializer<?> original;
    private Method initChannelMethod;

    public NettyChannelInitializer(ChannelInitializer<?> originalChannelInitializer) {
        this.original = originalChannelInitializer;
        load();
    }

    private void load() {
        initChannelMethod = Reflection.getMethod(original.getClass(), 0, "initChannel");
    }

    public ChannelInitializer<?> getOriginal() {
        return original;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        initChannelMethod.invoke(original, channel);
        NettyPacketInterceptor packetInterceptor = new NettyPacketInterceptor();
        if (channel.getClass().getSimpleName().equalsIgnoreCase("FakeChannel")) return;
        if (channel.pipeline().get("packet_handler") != null) {
            if (channel.pipeline().get("protocoller_handler") != null) {
                Bukkit.getConsoleSender().sendMessage("Attempted to initialize Skip$1 channel twice!");
            } else {
                channel.pipeline().addBefore("packet_handler", "protocoller_handler", packetInterceptor);
            }
        }
    }

}
