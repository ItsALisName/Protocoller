package Example;

import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.event.synchronous.PacketPlayReceiveEvent;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.packets.game.PacketPlayInChat;

public class ExampleSyncListener implements PacketListener {

    @PacketEventHandler(eventPriority = PacketEventPriority.HIGH, ignoreCancelled = true)
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.CHAT) {
            PacketPlayInChat chat = new PacketPlayInChat(event.getData());
        }
    }
}
