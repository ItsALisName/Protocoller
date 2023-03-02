package net.alis.protocoller.bukkit.server;

import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.PlayerReflection;
import net.alis.protocoller.bukkit.util.TaskSimplifier;
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
            GlobalProvider.instance().getServer().getPlayersInjector().updatePlayerObject(player, PlayerReflection.getPlayerChannel(player));
        }
    }
}
