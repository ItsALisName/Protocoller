package net.alis.protocoller.plugin.v0_0_5.server;

import io.netty.channel.ChannelFuture;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.v0_0_5.network.ProtocolNetworkManager;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.NetworkManager;
import net.alis.protocoller.samples.server.ServerConnection;
import net.alis.protocoller.util.AccessedObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ProtocolServerConnection implements ServerConnection {

    private final AccessedObject handle;

    public ProtocolServerConnection(Object serverConnection) {
        this.handle = new AccessedObject(serverConnection, true);
        this.startTcpServerListenerMethod = Reflect.getMethod(ClassAccessor.get().getServerConnectionClass(), Void.TYPE, true, InetAddress.class, Integer.TYPE);
        this.stopMethod = Reflect.getMethod(ClassAccessor.get().getServerConnectionClass(), "b", Void.TYPE, true);
        this.tickMethod = Reflect.getMethod(ClassAccessor.get().getServerConnectionClass(), "c", Void.TYPE, true);
    }

    @Override
    public <P> P getParameter(int index, Class<?> type) throws Exception {
        return this.handle.readField(index, type);
    }

    @Override
    public void setParameter(int index, Class<?> type, Object parameter) throws Exception {
        this.handle.writeSpecify(index, type, parameter);
    }

    @Override
    public <P> P getParameter(Predicate<Version> versionPredicate, int index1, int index2, Class<?> type) throws Exception {
        return this.handle.readField(versionPredicate, index1, index2, type);
    }

    @Override
    public void setParameter(Predicate<Version> versionPredicate, int index1, int index2, Object parameter) throws Exception {
        this.handle.write(versionPredicate, index1, index2, parameter);
    }

    public List<ChannelFuture> getChannels() {
        return this.handle.readField(0, List.class);
    }

    public List<NetworkManager> getConnections() {
        List<NetworkManager> conn = new ArrayList<>();
        for(Object connHandle : (List<Object>)this.handle.readField(1, List.class)) {
            conn.add(new ProtocolNetworkManager(connHandle));
        }
        return conn;
    }

    public void tick() {
        Reflect.callMethod(this.handle.getOriginal(), tickMethod, false);
    }

    public void stop() {
        Reflect.callMethod(this.handle.getOriginal(), stopMethod, false);
    }

    public void startTcpServerListener(InetAddress address, int port) throws IOException {
        Reflect.callMethod(this.handle.getOriginal(), startTcpServerListenerMethod, false, address, port);
    }

    public boolean isRunning() {
        return this.handle.readField(0, Boolean.TYPE);
    }

    public AccessedObject getHandle() {
        return handle;
    }
    private final Method startTcpServerListenerMethod, stopMethod, tickMethod;

}
