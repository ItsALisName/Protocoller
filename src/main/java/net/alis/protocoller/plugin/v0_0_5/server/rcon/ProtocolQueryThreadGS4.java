package net.alis.protocoller.plugin.v0_0_5.server.rcon;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.samples.server.rcon.QueryThreadGC4;
import net.alis.protocoller.samples.server.rcon.RemoteStatusReply;
import net.alis.protocoller.util.AccessedObject;

import java.net.DatagramSocket;

public class ProtocolQueryThreadGS4 implements QueryThreadGC4 {

    private final AccessedObject handle;

    public ProtocolQueryThreadGS4(Object remStList) {
        this.handle = new AccessedObject(remStList, true);
    }

    public int getQueryPort() {
        return this.handle.readField(0, Integer.TYPE);
    }

    public void setQueryPort(int port) {
        this.handle.write(0, port);
    }

    public int getServerPort() {
        return this.handle.readField(1, Integer.TYPE);
    }

    public void setServerPort(int port) {
        this.handle.write(1, port);
    }

    public int getMaxPlayers() {
        return this.handle.readField(2, Integer.TYPE);
    }

    public void setMaxPlayers(int max) {
        this.handle.write(2, max);
    }

    public String getServerName() {
        return this.handle.readField(0, String.class);
    }

    public void setServerName(String name) {
        this.handle.write(0, name);
    }

    public String getWorldName() {
        return this.handle.readField(1, String.class);
    }

    public void setWorldName(String name) {
        this.handle.write(1, name);
    }

    public DatagramSocket getSocket() {
        return this.handle.readField(0, DatagramSocket.class);
    }

    public String getHostIp() {
        return this.handle.readField(2, String.class);
    }

    public void setHostIp(String hostIp) {
        this.handle.write(2, hostIp);
    }

    public String getServerIp() {
        return this.handle.readField(3, String.class);
    }

    public void setServerIp(String ip) {
        this.handle.write(3, ip);
    }

    public RemoteStatusReply getRulesResponse() {
        return new RemoteStatusReply(this.handle.readField(0, ClassAccessor.get().getRemoteStatusReplyClass()));
    }

    public AccessedObject getHandle() {
        return handle;
    }
}
