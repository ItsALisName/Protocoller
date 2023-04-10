package net.alis.protocoller.plugin.v0_0_4.server;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.v0_0_4.netty.injectors.PlayersInjector;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.TaskSimplifier;

public class UpdatePlayerDataRunner implements Runnable {

    private static boolean isStarted = false;

    public static void start() {
        if(!isStarted) {
            TaskSimplifier.get().preformAsyncTimerTask(new UpdatePlayerDataRunner(), 0, 25L);
            isStarted = true;
        } else {
            LogsManager.get().getLogger().warn("Task 'UpdateServerInfo' already started!");
        }
    }

    @Override
    public void run() {
        for(NetworkPlayer player : GlobalProvider.get().getServer().getOnlinePlayers()) {
            ((PlayersInjector)GlobalProvider.get().getServer().getPlayersInjector()).refreshInterceptor(player);
        }
    }
}
