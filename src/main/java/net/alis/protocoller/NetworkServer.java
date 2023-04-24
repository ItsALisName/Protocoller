package net.alis.protocoller;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.samples.command.CommandListenerWrapper;
import net.alis.protocoller.samples.command.ICommandMap;
import net.alis.protocoller.samples.server.rcon.QueryThreadGC4;
import net.alis.protocoller.samples.server.ServerConnection;
import net.alis.protocoller.samples.network.status.ServerPing;
import net.alis.protocoller.samples.server.rcon.RconThread;
import net.alis.protocoller.samples.server.world.level.WorldServer;
import net.alis.protocoller.util.ParametersChangeable;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NetworkServer extends ParametersChangeable {

    Version getVersion();

    String getCoreName();

    void syncCommands();

    ICommandMap getCommandMap();

    @Nullable QueryThreadGC4 getQueryThreadGC4();

    ServerPing getServerStatus();

    void setServerStatus(@NotNull ServerPing status);

    Server getBukkitServer();

    @Nullable CommandListenerWrapper getCommandListenerWrapper();

    @Nullable RconThread getRconThread();

    ServerConnection getConnection();

    Collection<NetworkPlayer> onlinePlayers();

    NetworkPlayer getPlayer(Player player);

    NetworkPlayer getPlayer(UUID uuid);

    NetworkPlayer getPlayer(String nickname);

    int getReceivedPacketsCount();

    int getSentPacketsCount();

    List<WorldServer> worlds();

    WorldServer getWorldServer(String name);

}
