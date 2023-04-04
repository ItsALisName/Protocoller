package net.alis.protocoller.plugin.server;

import net.alis.protocoller.plugin.network.netty.injectors.PlayersInjector;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.PlayerReflection;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UpdatePlayerDataRunner implements Runnable {

    private static boolean isStarted = false;

    public static void start() {
        if(!isStarted) {
            TaskSimplifier.INSTANCE.preformAsyncTimerTask(new UpdatePlayerDataRunner(), 0, 25L);
            isStarted = true;
        } else {
            Bukkit.getConsoleSender().sendMessage("[Protocoller] Task 'UpdateServerInfo' already started!");
        }
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            ((PlayersInjector)GlobalProvider.instance().getServer().getPlayersInjector()).refreshInterceptor(player, PlayerReflection.getPlayerChannel(player));
        }
    }
}
