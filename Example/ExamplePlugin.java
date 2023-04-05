package Example;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.Protocoller;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin implements Listener {

    public static ApiUser apiUser;

    @Override
    public void onEnable() {
        apiUser = Protocoller.registerUser((Plugin) this);
        apiUser.getEventManager().getSynchronousManager().registerListener(apiUser, new ExampleSyncListener());
        apiUser.getEventManager().getAsynchronousManager().registerListener(apiUser, new ExampleAsyncListener());
        Bukkit.getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        PacketPlayOutChat chat = new PacketPlayOutChat(new ChatComponent("Hello"), ChatMessageType.SYSTEM, p.getUniqueId());
        NetworkPlayer networkPlayer = apiUser.getServer().getPlayer(p);
        networkPlayer.sendPacket(chat);
    }

}
