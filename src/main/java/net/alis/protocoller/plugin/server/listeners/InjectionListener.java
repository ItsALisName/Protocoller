package net.alis.protocoller.plugin.server.listeners;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.packet.packets.game.PacketPlayOutChat;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.network.ProtocollerPlayer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.server.rcon.QueryThreadGC4;
import net.md_5.bungee.api.MessageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;

public class InjectionListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        NetworkPlayer p = new ProtocollerPlayer(player);
        GlobalProvider.instance().getServer().getPlayersInjector().inject(p);
        TaskSimplifier.get().preformAsync(() -> {
            GlobalProvider.instance().getData().getEntitiesContainer().addEntity(player.getEntityId(), player);
        });
        p.getConnection().sendPacket(new PacketPlayOutChat(new ChatComponent("Lol"), MessageType.ACTION_BAR, p.getUniqueId()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TaskSimplifier.get().preformAsync(() -> {
            GlobalProvider.instance().getServer().getPlayersInjector().eject(GlobalProvider.instance().getServer().getPlayer(player.getUniqueId()));
            GlobalProvider.instance().getData().getPlayersContainer().removePlayer(player.getUniqueId());
            GlobalProvider.instance().getData().getEntitiesContainer().removeEntity(player.getEntityId());
        });
    }

}
