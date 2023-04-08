package net.alis.protocoller;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.samples.server.rcon.QueryThreadGC4;
import net.alis.protocoller.samples.server.ServerConnection;
import net.alis.protocoller.samples.network.status.ServerPing;
import net.alis.protocoller.samples.server.rcon.RconThread;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface NetworkServer {

    Version getVersion();

    String getCoreName();

    @Nullable QueryThreadGC4 getQueryThreadGC4();

    ServerPing getServerStatus();

    void setServerStatus(@NotNull ServerPing status);

    Server getBukkitServer();

    @Nullable RconThread getRconThread();

    ServerConnection getConnection();

    Collection<NetworkPlayer> getOnlinePlayers();

    NetworkPlayer getPlayer(Player player);

    NetworkPlayer getPlayer(UUID uuid);

    NetworkPlayer getPlayer(String nickname);

    int getReceivedPacketsCount();

    int getSentPacketsCount();

}
