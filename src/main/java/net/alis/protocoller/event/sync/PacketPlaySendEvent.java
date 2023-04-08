package net.alis.protocoller.event.sync;

import io.netty.channel.Channel;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.event.impl.PacketHandlerList;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;

public class PacketPlaySendEvent extends SyncPacketEvent {

    private static final PacketHandlerList handlerList = new PacketHandlerList();

    private final Player player;
    private final NetworkPlayer networkPlayer;

    public PacketPlaySendEvent(PacketDataContainer data, Channel channel, InetSocketAddress address, Player player, NetworkPlayer networkPlayer) {
        super(data, channel, address);
        this.player = player;
        this.networkPlayer = networkPlayer;
    }

    public void setPacket(PlayOutPacket packet) {
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
