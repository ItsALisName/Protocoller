package net.alis.protocoller.event.synchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.entity.NetworkPlayer;
import net.alis.protocoller.event.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataSerializer;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;

public class PacketPlaySendEvent extends SyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    private final Player player;
    private final NetworkPlayer networkPlayer;

    public PacketPlaySendEvent(PacketDataSerializer data, Channel channel, InetSocketAddress address, Player player, NetworkPlayer networkPlayer) {
        super(data, channel, address);
        this.player = player;
        this.networkPlayer = networkPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public NetworkPlayer getNetworkPlayer() {
        return networkPlayer;
    }

    public static PacketHandlerList getHandlerList() {
        return handlerList;
    }
}
