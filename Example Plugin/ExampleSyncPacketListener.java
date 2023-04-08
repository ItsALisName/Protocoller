package ExamplePlugin;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.event.sync.PacketPlaySendEvent;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.packets.game.PacketPlayOutAbilities;
import org.bukkit.entity.Player;

public class ExampleSyncPacketListener implements PacketListener {

    public void yourCustomMethodName(PacketPlaySendEvent event) {
        if(event.getPacketType() == PacketType.Play.Server.ABILITIES) {
            PacketPlayOutAbilities abilities = new PacketPlayOutAbilities(event.getData());
            NetworkPlayer networkPlayer = event.getNetworkPlayer();
            Player player = event.getNetworkPlayer().getBukkitPlayer();
            if(player.getName().equalsIgnoreCase("ALis")) {
                if(!abilities.isCanFly()) abilities.setCanFly(true);
                event.setPacket(abilities);
            }
            return;
        }
    }

}
