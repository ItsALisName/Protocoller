package Example;

import net.alis.protocoller.event.asynchronous.AsyncPacketLoginSendEvent;
import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.packets.login.PacketLoginOutDisconnect;
import net.alis.protocoller.samples.network.chat.ChatComponent;

public class ExampleAsyncListener implements PacketListener {

    @PacketEventHandler(eventPriority = PacketEventPriority.HIGH, ignoreCancelled = true)
    public void onAsyncPacketLoginSend(AsyncPacketLoginSendEvent event) {
        if(event.getPacketType() == PacketType.Login.Server.DISCONNECT) {
            PacketLoginOutDisconnect disconnect = new PacketLoginOutDisconnect(event.getData());
            ChatComponent reason = disconnect.getReason();
            System.out.println("Reason: " + reason.getString());
        }
    }
}
