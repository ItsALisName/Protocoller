package net.alis.protocoller.bukkit.server.listeners;

import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.network.NettyChannelManager;
import net.alis.protocoller.bukkit.network.ProtocollerPlayer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.Utils;
import net.alis.protocoller.bukkit.util.reflection.PlayerReflection;
import net.alis.protocoller.bukkit.util.TaskSimplifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InjectionListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        try {
            GlobalProvider.instance().getServer().getPlayersInjector().inject(player);
        } catch (Exception ignored) { }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new ProtocollerPlayer(new NettyChannelManager(PlayerReflection.getPlayerChannel(player)), player.getName(), player.getUniqueId(), player.getAddress());
        if(!GlobalProvider.instance().getServer().getPlayersInjector().isInjected(player))
            GlobalProvider.instance().getServer().getPlayersInjector().inject(player);
        TaskSimplifier.INSTANCE.preformAsync(() -> {
            Thread.currentThread().setName("ProtocollerUtilThread-" + Utils.generateRandomInt(5));
            GlobalProvider.instance().getData().getEntitiesContainer().addEntity(player.getEntityId(), player);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TaskSimplifier.INSTANCE.preformAsync(() -> {
            Thread.currentThread().setName("ProtocollerUtilThread-" + Utils.generateRandomInt(5));
            GlobalProvider.instance().getServer().getPlayersInjector().eject(player);
            GlobalProvider.instance().getData().getPlayersContainer().removePlayer(player.getUniqueId());
            GlobalProvider.instance().getData().getEntitiesContainer().removeEntity(player.getEntityId());
        });
    }

}
