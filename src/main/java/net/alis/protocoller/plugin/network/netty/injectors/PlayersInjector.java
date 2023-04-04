package net.alis.protocoller.plugin.network.netty.injectors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import net.alis.protocoller.plugin.network.netty.ChannelInjector;
import net.alis.protocoller.plugin.network.netty.interceptors.NettyPacketInterceptor;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.PlayerReflection;
import net.alis.protocoller.entity.NetworkPlayer;
import org.bukkit.entity.Player;

public class PlayersInjector implements ChannelInjector.PlayerInjector {

    @Override
    public void inject(Player player) {
        this.refreshInterceptor(player, PlayerReflection.getPlayerChannel(player));
    }

    @Override
    public void eject(Player player) {
        Channel channel = PlayerReflection.getPlayerChannel(player);
        if (channel != null) {
            try {
                channel.pipeline().remove("protocoller_handler");
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void eject(NetworkPlayer player) {
        player.getChannel().pipeline().remove("protocoller_handler");
    }

    @Override
    public boolean isInjected(Player player) {
        NetworkPlayer networkPlayer = GlobalProvider.instance().getData().getPlayersContainer().get(player.getUniqueId());
        Channel channel;
        if(networkPlayer != null) {
            channel = networkPlayer.getChannel();
        } else {
            channel = PlayerReflection.getPlayerChannel(player);
        }
        return channel.pipeline().get("protocoller_handler") != null;
    }


    private NettyPacketInterceptor getInterceptor(Channel channel) {
        ChannelHandler handler = channel.pipeline().get("protocoller_handler");
        if (handler instanceof NettyPacketInterceptor) {
            return (NettyPacketInterceptor)handler;
        } else {
            return null;
        }
    }

    public void refreshInterceptor(Player player, Channel channel) {
        NettyPacketInterceptor handler = this.getInterceptor(channel);
        if (handler != null) {
            handler.setPlayer(player);
            handler.setNetworkPlayer(GlobalProvider.instance().getData().getPlayersContainer().get(player.getUniqueId()));
        }
    }
}
