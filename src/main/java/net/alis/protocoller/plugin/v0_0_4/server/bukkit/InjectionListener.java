package net.alis.protocoller.plugin.v0_0_4.server.bukkit;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.updater.ProtocollerUpdater;
import net.alis.protocoller.plugin.v0_0_4.entity.ProtocollerPlayer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InjectionListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        NetworkPlayer p = new ProtocollerPlayer(player);
        GlobalProvider.get().getServer().getPlayersInjector().inject(p);
        TaskSimplifier.get().preformAsync(() -> GlobalProvider.get().getServer().getEntityList().addEntity(player.getEntityId(), player));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TaskSimplifier.get().preformAsync(() -> {
            GlobalProvider.get().getServer().getPlayersInjector().eject(GlobalProvider.get().getServer().getPlayer(player.getUniqueId()));
            GlobalProvider.get().getServer().getPlayersList().removePlayer(player.getUniqueId());
            GlobalProvider.get().getServer().getEntityList().removeEntity(player.getEntityId());
        });
        ProtocollerUpdater.notifyOperator(player);
    }

}
