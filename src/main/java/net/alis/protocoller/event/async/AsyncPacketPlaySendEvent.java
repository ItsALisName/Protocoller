package net.alis.protocoller.event.async;

import io.netty.channel.Channel;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.event.impl.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;

public class AsyncPacketPlaySendEvent extends AsyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    private final Player player;
    private final NetworkPlayer networkPlayer;

    public AsyncPacketPlaySendEvent(PacketDataContainer data, Channel channel, InetSocketAddress address, Player player, NetworkPlayer networkPlayer) {
        super(data, channel, address);
        this.player = player;
        this.networkPlayer = networkPlayer;
    }

    public void trySendPacket(PlayOutPacket packet) throws Exception {
        super.trySendPacket(packet);
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
