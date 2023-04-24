package ExamplePlugin;

import libraries.net.md_5.bungee.api.MessageType;
import libraries.net.md_5.bungee.api.chat.HoverEvent;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.NetworkServer;
import net.alis.protocoller.packet.packets.game.PacketPlayOutChat;
import net.alis.protocoller.samples.network.NetworkManager;
import net.alis.protocoller.samples.network.PlayerConnection;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import org.bukkit.Bukkit;

import java.util.UUID;

public class NetworkPlayerWorkExample {

    public void networkPlayerWorking() {
        NetworkServer server = MainClass.protocollerApi.getServer();
        NetworkPlayer player = server.getPlayer(UUID.fromString("ALis"));
        NetworkPlayer player1 = server.getPlayer("ALis");
        NetworkPlayer player2 = server.getPlayer(Bukkit.getPlayer("ALis"));

        PlayerConnection connection = player.getConnection();
        int ping = player.getPing();
        int protocol = player.getVersion().getProtocol();
        String versionName = player.getVersion().asString();

        ChatComponent component = new ChatComponent("Welcome to the ");
        component.append("server " + player.getName()).setHoverEvent(HoverEvent.Action.SHOW_TEXT, "You amazing!");
        connection.sendPacket(new PacketPlayOutChat(component, MessageType.CHAT, player.getUniqueId()));

        NetworkManager manager = connection.getNetworkManager();
    }

}
