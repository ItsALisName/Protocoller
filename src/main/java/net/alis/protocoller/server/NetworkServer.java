package net.alis.protocoller.server;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.NetworkPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public interface NetworkServer {

    Version getVersion();

    boolean isLegacy();

    boolean isVeryLegacy();

    Collection<NetworkPlayer> getOnlinePlayers();

    NetworkPlayer getPlayer(Player player);

    NetworkPlayer getPlayer(UUID uuid);

    NetworkPlayer getPlayer(String nickname);

}
