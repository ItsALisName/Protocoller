package net.alis.protocoller.samples.network;

import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.exception.ProtocollerException;
import net.alis.protocoller.plugin.exception.ReflectionException;
import net.alis.protocoller.util.ParametersChangeable;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public interface PlayerConnection extends ParametersChangeable {

    NetworkManager getNetworkManager();

    void sendPacket(PlayOutPacket packet);

    void sendPackets(PlayOutPacket... packets);

    void disconnect(String reason);

    void chat(String msg, boolean async);

    Player getPlayer();

    boolean processedDisconnect();

    int getLastDropTick();

    int getLastTick();

    int getAllowedPlayerTicks();

    int getDropCount();

    void setLastTick(int lastTick);

    void setAllowedPlayerTicks(int ticks);

    void setDropCount(int count);

    void setLastDropTick(int lastDropTick);

}
