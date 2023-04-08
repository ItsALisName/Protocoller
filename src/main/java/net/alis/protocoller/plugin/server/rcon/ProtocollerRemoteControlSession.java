package net.alis.protocoller.plugin.server.rcon;

import net.alis.protocoller.samples.server.rcon.RemoteControlSession;
import net.alis.protocoller.util.AccessedObject;

import java.net.Socket;

public class ProtocollerRemoteControlSession implements RemoteControlSession {

    private final AccessedObject handle;

    public ProtocollerRemoteControlSession(Object remoteSession) {
        this.handle = new AccessedObject(remoteSession);
    }

    public boolean isAuthed() {
        return this.handle.read(0, Boolean.TYPE);
    }

    public void setAuthed(boolean authed) {
        this.handle.write(0, authed);
    }

    public Socket getClient() {
        return this.handle.read(0, Socket.class);
    }

}
