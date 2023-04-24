package net.alis.protocoller.plugin.v0_0_5.server.bukkit;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.updater.ProtocollerUpdater;
import net.alis.protocoller.plugin.v0_0_5.entity.ProtocolPlayer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.ITaskAccess;
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
        NetworkPlayer p = new ProtocolPlayer(player, IProtocolAccess.get().getServer());
        IProtocolAccess.get().getServer().getPlayersInjector().inject(p);
        ITaskAccess.get().doAsync(() -> IProtocolAccess.get().getServer().getEntityList().addEntity(player.getEntityId(), player));
        ProtocollerUpdater.notifyOperator(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ITaskAccess.get().doAsync(() -> {
            IProtocolAccess.get().getServer().getPlayersInjector().eject(IProtocolAccess.get().getServer().getPlayer(player.getUniqueId()));
            IProtocolAccess.get().getServer().getPlayersList().removePlayer(player.getUniqueId());
            IProtocolAccess.get().getServer().getEntityList().removeEntity(player.getEntityId());
        });
    }

}
