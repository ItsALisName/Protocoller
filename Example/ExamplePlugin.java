package Example;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.Protocoller;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ApiUser apiUser = Protocoller.registerUser((Plugin) this);
        apiUser.getEventManager().getSynchronousManager().registerListener(apiUser, new ExampleSyncListener());
        apiUser.getEventManager().getAsynchronousManager().registerListener(apiUser, new ExampleAsyncListener());
    }
}
