package net.alis.protocoller.samples.server.rcon;

import net.alis.protocoller.NetworkServer;

import java.net.ServerSocket;
import java.util.List;

public interface RconThread {

    List<RemoteControlSession> getClients();

    ServerSocket getSocket();

    void closeSocket(ServerSocket socket);

    NetworkServer getServer();

}
