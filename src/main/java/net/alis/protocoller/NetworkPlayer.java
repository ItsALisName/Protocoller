package net.alis.protocoller;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.samples.entity.player.PlayerAbilities;
import net.alis.protocoller.samples.network.PlayerConnection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.UUID;

public interface NetworkPlayer {

    String getName();

    UUID getUniqueId();

    Version getVersion();

    GameProfile getGameProfile();

    PlayerAbilities getPlayerAbilities();

    void setPlayerAbilities(@NotNull PlayerAbilities abilities);

    InetSocketAddress getInetSocketAddress();

    PlayerConnection getConnection();

    int getPacketsSentCount();

    int getPacketsReceivedCount();

    Player getBukkitPlayer();

    int getPing();

}
