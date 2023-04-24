package net.alis.protocoller.plugin.v0_0_5.server.tasks;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.v0_0_5.netty.injectors.PlayersInjector;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.ITaskAccess;

public class dUPD_Task implements Runnable {

    public static void start() {
        ITaskAccess.get().doAsyncTimerTask(new dUPD_Task(), 0, 20L);
    }

    @Override
    public void run() {
        for(NetworkPlayer player : IProtocolAccess.get().getServer().onlinePlayers()) {
            ((PlayersInjector) IProtocolAccess.get().getServer().getPlayersInjector()).refreshInterceptor(player);
        }
    }
}
