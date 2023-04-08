package net.alis.protocoller.plugin.network;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.ProtocolUtil;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.PlayerConnection;
import net.alis.protocoller.util.AccessedObject;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.UUID;

public class ProtocollerPlayer implements NetworkPlayer {

    private final Player player;
    private final AccessedObject handle;
    private final ProtocollerPlayerConnection connection;
    private final Version version;
    private int packetsSent;
    private int packetsReceived;

    public ProtocollerPlayer(Player player) {
        this.player = player;
        this.handle = new AccessedObject(MinecraftReflection.getEntityPlayer(player));
        this.connection = new ProtocollerPlayerConnection(this.player);
        this.packetsSent = 0;
        this.packetsReceived = 0;
        this.version = Version.fromProtocol(ProtocolUtil.getPlayerProtocol(player));
        GlobalProvider.instance().getData().getPlayersContainer().addPlayer(this);
        this.pingField = Reflect.readField(this.getHandle().getObject(), "ping", true);
    }

    @Override
    public String getName() {
        return this.player.getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    @Override
    public InetSocketAddress getInetSocketAddress() {
        return this.player.getAddress();
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public int getPacketsReceivedCount() {
        return packetsReceived;
    }

    @Override
    public int getPacketsSentCount() {
        return packetsSent;
    }

    public int getPing() {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_17)) {
            return Reflect.readField(this.handle.getObject(), pingField, false);
        } else {
            return this.player.getPing();
        }
    }

    @Override
    public PlayerConnection getConnection() {
        return connection;
    }

    @Override
    public Player getBukkitPlayer() {
        return this.player;
    }

    public AccessedObject getHandle() {
        return handle;
    }

    public void addSentPacket() {
        this.packetsSent = packetsSent + 1;
    }

    public void addReceivedPacket() {
        this.packetsReceived = this.packetsReceived + 1;
    }

    private final Field pingField;

}
