package net.alis.protocoller.samples.server.rcon;

import net.alis.protocoller.samples.server.rcon.RemoteStatusReply;

import java.net.DatagramSocket;

public interface QueryThreadGC4 {

    int getQueryPort();

    void setQueryPort(int port);

    int getServerPort();

    void setServerPort(int port);

    int getMaxPlayers();

    void setMaxPlayers(int max);

    String getServerName();

    String getWorldName();

    void setServerName(String name);

    void setWorldName(String name);

    DatagramSocket getSocket();

    String getHostIp();

    String getServerIp();

    void setHostIp(String ip);

    void setServerIp(String ip);

    RemoteStatusReply getRulesResponse();

}
