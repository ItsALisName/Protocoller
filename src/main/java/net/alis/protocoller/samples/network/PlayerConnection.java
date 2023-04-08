package net.alis.protocoller.samples.network;

import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.exception.ProtocollerException;
import net.alis.protocoller.plugin.exception.ReflectionException;
import org.bukkit.entity.Player;

public interface PlayerConnection {

    <P> P getParameter(int index, Class<?> type) throws ProtocollerException;

    void setParameter(int index, Class<?> type, Object parameter) throws ProtocollerException;

    NetworkManager getNetworkManager();

    void sendPacket(PlayOutPacket packet);

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
