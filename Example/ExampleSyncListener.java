package Example;

import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.event.synchronous.PacketPlayReceiveEvent;
import net.alis.protocoller.event.synchronous.PacketStatusSendEvent;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.packets.game.PacketPlayInChat;
import net.alis.protocoller.packet.packets.status.PacketStatusOutServerInfo;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.status.ServerData;
import net.alis.protocoller.samples.network.status.ServerPing;
import net.alis.protocoller.samples.network.status.ServerPingPlayerSample;

public class ExampleSyncListener implements PacketListener {

    @PacketEventHandler(eventPriority = PacketEventPriority.HIGH, ignoreCancelled = true)
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.CHAT) {
            PacketPlayInChat chat = new PacketPlayInChat(event.getData());
            chat.setMessage("Hi");
            event.setPacket(chat);
        }
    }

    @PacketEventHandler
    public void onPacketStatusSend(PacketStatusSendEvent event) {
        if(event.getPacketType() == PacketType.Status.Server.SERVER_INFO) {
            PacketStatusOutServerInfo serverInfo = new PacketStatusOutServerInfo(event.getData());
            ServerPing ping = new ServerPing();
            ping.setFavicon(serverInfo.getMetadata().getFavicon());
            ping.setDescription(new ChatComponent("Best server"));
            ServerPingPlayerSample playerSample = new ServerPingPlayerSample(1000, 999);
            ping.setPlayers(playerSample);
            ServerData data = new ServerData("Your core 1.20.1", 762);
            ping.setVersion(data);
            PacketStatusOutServerInfo newServerInfo = new PacketStatusOutServerInfo(ping);
            event.setPacket(newServerInfo);
        }
    }
}
