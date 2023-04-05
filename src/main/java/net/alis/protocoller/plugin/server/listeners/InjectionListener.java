package net.alis.protocoller.plugin.server.listeners;

import net.alis.protocoller.plugin.network.NettyChannelManager;
import net.alis.protocoller.plugin.network.ProtocollerPlayer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.TaskSimplifier;
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
        new ProtocollerPlayer(new NettyChannelManager(MinecraftReflection.getPlayerChannel(player)), player.getName(), player.getUniqueId(), player.getAddress());
        GlobalProvider.instance().getServer().getPlayersInjector().inject(player);
        TaskSimplifier.get().preformAsync(() -> {
            Thread.currentThread().setName("ProtocollerUtilThread-" + Utils.generateRandomInt(5));
            GlobalProvider.instance().getData().getEntitiesContainer().addEntity(player.getEntityId(), player);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TaskSimplifier.get().preformAsync(() -> {
            Thread.currentThread().setName("ProtocollerUtilThread-" + Utils.generateRandomInt(5));
            GlobalProvider.instance().getServer().getPlayersInjector().eject(player);
            GlobalProvider.instance().getData().getPlayersContainer().removePlayer(player.getUniqueId());
            GlobalProvider.instance().getData().getEntitiesContainer().removeEntity(player.getEntityId());
        });
    }

}
