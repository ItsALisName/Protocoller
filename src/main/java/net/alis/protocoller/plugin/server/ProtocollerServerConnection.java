package net.alis.protocoller.plugin.server;

import io.netty.channel.ChannelFuture;
import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.network.ProtocollerNetworkManager;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.NetworkManager;
import net.alis.protocoller.samples.server.ServerConnection;
import net.alis.protocoller.util.AccessedObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ProtocollerServerConnection implements ServerConnection {

    private final AccessedObject handle;

    public ProtocollerServerConnection(Object serverConnection) {
        this.handle = new AccessedObject(serverConnection, true);
        this.startTcpServerListenerMethod = Reflect.getMethod(ClassesContainer.get().getServerConnectionClass(), Void.TYPE, true, InetAddress.class, Integer.TYPE);
        this.stopMethod = Reflect.getMethod(ClassesContainer.get().getServerConnectionClass(), "b", Void.TYPE, true);
        this.tickMethod = Reflect.getMethod(ClassesContainer.get().getServerConnectionClass(), "c", Void.TYPE, true);
    }

    public List<ChannelFuture> getChannels() {
        return this.handle.read(0, List.class);
    }

    public List<NetworkManager> getConnections() {
        List<NetworkManager> conn = new ArrayList<>();
        for(Object connHandle : (List<Object>)this.handle.read(1, List.class)) {
            conn.add(new ProtocollerNetworkManager(connHandle));
        }
        return conn;
    }

    public void tick() {
        Reflect.callMethod(this.handle.getObject(), tickMethod, false);
    }

    public void stop() {
        Reflect.callMethod(this.handle.getObject(), stopMethod, false);
    }

    public void startTcpServerListener(InetAddress address, int port) throws IOException {
        Reflect.callMethod(this.handle.getObject(), startTcpServerListenerMethod, false, address, port);
    }

    public boolean isRunning() {
        return this.handle.read(0, Boolean.TYPE);
    }

    public AccessedObject getHandle() {
        return handle;
    }
    private final Method startTcpServerListenerMethod, stopMethod, tickMethod;

}
