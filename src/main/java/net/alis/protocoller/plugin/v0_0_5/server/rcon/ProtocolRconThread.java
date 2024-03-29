package net.alis.protocoller.plugin.v0_0_5.server.rcon;

import net.alis.protocoller.NetworkServer;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.server.rcon.RconThread;
import net.alis.protocoller.samples.server.rcon.RemoteControlSession;
import net.alis.protocoller.util.AccessedObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtocolRconThread implements RconThread {

    private final AccessedObject handle;
    private final NetworkServer server;

    public ProtocolRconThread(Object remoteControlListener, NetworkServer server) {
        this.handle = new AccessedObject(remoteControlListener, true);
        this.server = server;
        this.closeSocketMethod = Reflect.getMethod(ClassAccessor.get().getRemoteControlListenerClass(), Void.TYPE, true, ServerSocket.class);
    }

    public List<RemoteControlSession> getClients() {
        List<RemoteControlSession> cl = new ArrayList<>();
        if(this.server.getVersion().lessThanOrEqualTo(Version.v1_16)) {
            for(Map.Entry<?,?> e : ((Map<?,?>)this.handle.readField(0, Map.class)).entrySet()) {
                cl.add(new ProtocolRemoteControlSession(e.getValue()));
            }
        } else {
            for(Object rem : ((List<?>)this.handle.readField(0, List.class))) {
                cl.add(new ProtocolRemoteControlSession(rem));
            }
        }
        return cl;
    }

    public void removeClients() {
        if(this.server.getVersion().lessThanOrEqualTo(Version.v1_16)) {
            this.handle.writeSpecify(0, Map.class, new HashMap<>());
        } else {
            this.handle.writeSpecify(0, List.class, new ArrayList<>());
        }
    }

    public void closeSocket(ServerSocket socket) {
        if(this.server.getVersion().lessThanOrEqualTo(Version.v1_16)) {
            LogsManager.get().getLogger().debug("closeSocket: {}", socket);
            try {
                socket.close();
            } catch (IOException ioexception) {
                ExceptionBuilder.throwException(ioexception, true);
            }
        } else {
            Reflect.callMethod(this.handle.getOriginal(), closeSocketMethod, false, socket);
        }
    }

    public ServerSocket getSocket() {
        return this.handle.readField(0, ServerSocket.class);
    }

    public AccessedObject getHandle() {
        return handle;
    }

    public NetworkServer getServer() {
        return server;
    }

    private Method closeSocketMethod;
}
