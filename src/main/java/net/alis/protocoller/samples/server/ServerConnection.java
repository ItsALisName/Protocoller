package net.alis.protocoller.samples.server;

import io.netty.channel.ChannelFuture;
import net.alis.protocoller.samples.network.NetworkManager;
import net.alis.protocoller.util.ParametersChangeable;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public interface ServerConnection extends ParametersChangeable {

    List<ChannelFuture> getChannels();

    List<NetworkManager> getConnections();

    void tick();

    void stop();

    void startTcpServerListener(InetAddress address, int port) throws IOException;

    boolean isRunning();

}
