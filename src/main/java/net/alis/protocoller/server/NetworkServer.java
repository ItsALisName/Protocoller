package net.alis.protocoller.server;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.entity.NetworkPlayer;

import java.util.Collection;

public interface NetworkServer {

    Version getVersion();

    boolean isLegacy();

    boolean isVeryLegacy();

    Collection<NetworkPlayer> getOnlinePlayers();

}
