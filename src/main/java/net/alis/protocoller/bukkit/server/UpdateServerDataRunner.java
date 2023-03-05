package net.alis.protocoller.bukkit.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.bukkit.util.reflection.ServerReflection;
import net.alis.protocoller.bukkit.util.TaskSimplifier;
import org.bukkit.Bukkit;

public class UpdateServerDataRunner implements Runnable {

    public static void start() {
        TaskSimplifier.INSTANCE.preformAsyncTimerTask(new UpdateServerDataRunner(), 0L, 20L);
    }

    @Override
    public void run() {
        try {
            GlobalProvider.instance().getServer().channels.clear();
            for(Object networkManager : ServerReflection.getServerNetworkManagers()) {
                GlobalProvider.instance().getServer().channels.add(Reflection.readField(networkManager, 0, Channel.class));
            }
            GlobalProvider.instance().getServer().channelFutures.clear();
            for(Object future : ServerReflection.getServerChannelFutures()) {
                GlobalProvider.instance().getServer().channelFutures.add((ChannelFuture) future);
            }
            GlobalProvider.instance().getServer().getServerInjector().inject();
        } catch (Exception e)  {
            throw new RuntimeException("Failed to update server channels!", e);
        }
    }
}
