package ExamplePlugin;

import io.netty.channel.ChannelFuture;
import net.alis.protocoller.NetworkServer;
import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.samples.authlib.properties.Property;
import net.alis.protocoller.samples.network.NetworkManager;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.status.ServerData;
import net.alis.protocoller.samples.network.status.ServerPing;
import net.alis.protocoller.samples.network.status.ServerPingPlayerSample;
import net.alis.protocoller.samples.server.ServerConnection;
import net.alis.protocoller.samples.server.rcon.QueryThreadGC4;
import net.alis.protocoller.samples.server.rcon.RconThread;
import net.alis.protocoller.samples.server.rcon.RemoteControlSession;
import net.alis.protocoller.samples.server.rcon.RemoteStatusReply;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NetworkServerWorkExample {

    public static void serverConnectionAccess() {
        NetworkServer server = MainClass.protocollerApi.getServer();
        ServerConnection connection = server.getConnection();
        List<NetworkManager> managers = connection.getConnections();
        List<ChannelFuture> channels = connection.getChannels();
        NetworkManager manager = managers.get(0);
        Property[] p = manager.getSpoofedProfiles();
    }

    public static void changeServerStatusInfo() {
        NetworkServer server = MainClass.protocollerApi.getServer();
        ServerPing ping = server.getServerStatus();
        ping.setVersion(new ServerData("Spigot 1.25.1", 756));
        ping.setFavicon("Some favicon");
        ping.setDescription(new ChatComponent("A best minecraft server!"));
        ServerPingPlayerSample playerSample = new ServerPingPlayerSample(10000, 999);
        List<GameProfile> profiles = new ArrayList<>();
        profiles.add(new GameProfile(UUID.randomUUID(), "ALis"));
        profiles.add(new GameProfile(UUID.randomUUID(), "Tasher228"));
        profiles.add(new GameProfile(UUID.randomUUID(), "Notch"));
        playerSample.setSample(profiles.toArray(new GameProfile[0]));
        ping.setPlayers(playerSample);
        server.setServerStatus(ping);
    }

    public static void changeGC4Thread() {
        NetworkServer server = MainClass.protocollerApi.getServer();
        QueryThreadGC4 query = server.getQueryThreadGC4();
        if(query != null) { //QueryThread can be null(Может возвращать null)
            query.setMaxPlayers(1000);
            RemoteStatusReply statusReply = query.getRulesResponse();
            byte[] replyBytes = statusReply.toByteArray();
            query.setServerIp("1.1.1.1");
            query.setServerName("Best minecraft server");
            int queryPort = query.getQueryPort();
        }
    }

    public void rconCheck() {
        NetworkServer server = MainClass.protocollerApi.getServer();
        RconThread rcon = server.getRconThread(); //RconThread can be null(Может быть null)
        if(rcon != null){
            List<RemoteControlSession> sessions = rcon.getClients();
            RemoteControlSession session = sessions.get(0);
            if(session.isAuthed()) {
                session.setAuthed(false);
            }
        }
    }

}
