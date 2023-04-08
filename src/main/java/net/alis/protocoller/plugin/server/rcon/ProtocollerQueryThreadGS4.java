package net.alis.protocoller.plugin.server.rcon;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.samples.server.rcon.QueryThreadGC4;
import net.alis.protocoller.samples.server.rcon.RemoteStatusReply;
import net.alis.protocoller.util.AccessedObject;

import java.net.DatagramSocket;

public class ProtocollerQueryThreadGS4 implements QueryThreadGC4 {

    private final AccessedObject handle;

    public ProtocollerQueryThreadGS4(Object remStList) {
        this.handle = new AccessedObject(remStList, true);
    }

    public int getQueryPort() {
        return this.handle.read(0, Integer.TYPE);
    }

    public void setQueryPort(int port) {
        this.handle.write(0, port);
    }

    public int getServerPort() {
        return this.handle.read(1, Integer.TYPE);
    }

    public void setServerPort(int port) {
        this.handle.write(1, port);
    }

    public int getMaxPlayers() {
        return this.handle.read(2, Integer.TYPE);
    }

    public void setMaxPlayers(int max) {
        this.handle.write(2, max);
    }

    public String getServerName() {
        return this.handle.read(0, String.class);
    }

    public void setServerName(String name) {
        this.handle.write(0, name);
    }

    public String getWorldName() {
        return this.handle.read(1, String.class);
    }

    public void setWorldName(String name) {
        this.handle.write(1, name);
    }

    public DatagramSocket getSocket() {
        return this.handle.read(0, DatagramSocket.class);
    }

    public String getHostIp() {
        return this.handle.read(2, String.class);
    }

    public void setHostIp(String hostIp) {
        this.handle.write(2, hostIp);
    }

    public String getServerIp() {
        return this.handle.read(3, String.class);
    }

    public void setServerIp(String ip) {
        this.handle.write(3, ip);
    }

    public RemoteStatusReply getRulesResponse() {
        return new RemoteStatusReply(this.handle.read(0, ClassesContainer.get().getRemoteStatusReplyClass()));
    }

    public AccessedObject getHandle() {
        return handle;
    }
}
