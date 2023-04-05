package net.alis.protocoller.plugin.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.plugin.util.TaskSimplifier;

public class UpdateServerDataRunner implements Runnable {

    public static void start() {
        TaskSimplifier.get().preformAsyncTimerTask(new UpdateServerDataRunner(), 0L, 20L);
    }

    @Override
    public void run() {
        GlobalProvider.instance().getServer().channels.clear();
        try {
            for (Object networkManager : MinecraftReflection.getServerNetworkManagers()) {
                GlobalProvider.instance().getServer().channels.add(BaseReflection.readField(networkManager, 0, Channel.class));
            }
        } catch (Exception ex) {
            new ExceptionBuilder().getServerExceptions().defineReason(ex).networkManagersError().throwException();
        }
        GlobalProvider.instance().getServer().channelFutures.clear();
        try {
            for (Object future : MinecraftReflection.getServerChannelFutures()) {
                GlobalProvider.instance().getServer().channelFutures.add((ChannelFuture) future);
            }
        } catch (Exception ex) {
            new ExceptionBuilder().getServerExceptions().defineReason(ex).channelFuturesError().throwException();
        }
    }
}
