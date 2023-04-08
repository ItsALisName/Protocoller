package ExamplePlugin;

import net.alis.protocoller.event.async.AsyncPacketLoginReceiveEvent;
import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketEventPriority;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.packets.login.PacketLoginInStart;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.samples.authlib.GameProfile;

public class ExampleAsyncPacketListener implements PacketListener {

    @PacketEventHandler(eventPriority = PacketEventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerLoginStart(AsyncPacketLoginReceiveEvent event) {
        if(event.getPacketType() == PacketType.Login.Client.START) {
            PacketLoginInStart loginPack = new PacketLoginInStart(event.getData());
            GameProfile prePlayerData = loginPack.getGameProfile();
            LogsManager.get().getLogger().info("Player \"" + prePlayerData.getName() + "\" will join the server soon");
        }
    }

}
