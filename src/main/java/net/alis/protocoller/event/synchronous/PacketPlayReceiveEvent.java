package net.alis.protocoller.event.synchronous;

import io.netty.channel.Channel;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.event.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.PlayInPacket;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;

public class PacketPlayReceiveEvent extends SyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    private final Player player;
    private final NetworkPlayer networkPlayer;

    public PacketPlayReceiveEvent(PacketDataContainer data, Channel channel, InetSocketAddress address, Player player, NetworkPlayer networkPlayer) {
        super(data, channel, address);
        this.player = player;
        this.networkPlayer = networkPlayer;
    }

    public void setPacket(PlayInPacket packet) {
        super.setPacket(packet);
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
