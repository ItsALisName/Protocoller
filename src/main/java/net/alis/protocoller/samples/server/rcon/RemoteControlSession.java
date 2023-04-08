package net.alis.protocoller.samples.server.rcon;

import java.net.Socket;

public interface RemoteControlSession {

    boolean isAuthed();

    void setAuthed(boolean authed);

    Socket getClient();

}
