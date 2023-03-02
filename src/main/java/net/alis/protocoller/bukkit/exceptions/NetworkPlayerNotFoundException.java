package net.alis.protocoller.bukkit.exceptions;

import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.UUID;

public class NetworkPlayerNotFoundException extends RuntimeException {

    public NetworkPlayerNotFoundException(@NotNull String playerName) {
        super("Couldn't find a1 player with the nickname '" + playerName + "' among online players!");
    }

    public NetworkPlayerNotFoundException(@NotNull UUID uuid) {
        super("Couldn't find Skip$1 player with unique id '" + uuid.toString() + "' among online players!");
    }

    public NetworkPlayerNotFoundException(@NotNull InetSocketAddress address) {
        super("Couldn't find Skip$1 player with the address '" + address.getAddress().getHostAddress() + "' among online players");
    }

}
