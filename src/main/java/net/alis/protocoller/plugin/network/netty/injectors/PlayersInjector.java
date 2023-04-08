package net.alis.protocoller.plugin.network.netty.injectors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import net.alis.protocoller.plugin.network.netty.ChannelInjector;
import net.alis.protocoller.plugin.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayersInjector implements ChannelInjector.PlayerInjector {

    @Override
    public void inject(NetworkPlayer player) {
        this.refreshInterceptor(player);
    }

    @Override
    public void eject(@NotNull NetworkPlayer player) {
        Channel channel = player.getConnection().getNetworkManager().getChannel();
        if(channel.pipeline().get("protocoller_handler") != null) {
            channel.pipeline().remove("protocoller_handler");
        }
    }

    @Override
    public boolean isInjected(@NotNull NetworkPlayer player) {
        return player.getConnection().getNetworkManager().getChannel().pipeline().get("protocoller_handler") != null;
    }


    private @Nullable NettyPacketInterceptor getInterceptor(@NotNull Channel channel) {
        ChannelHandler handler = channel.pipeline().get("protocoller_handler");
        if (handler instanceof NettyPacketInterceptor) {
            return (NettyPacketInterceptor)handler;
        } else {
            return null;
        }
    }

    public void refreshInterceptor(@NotNull NetworkPlayer player) {
        NettyPacketInterceptor handler = this.getInterceptor(player.getConnection().getNetworkManager().getChannel());
        if (handler != null) handler.setPlayer(player);
    }
}
