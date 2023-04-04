package Example;

import net.alis.protocoller.ApiUser;
import net.alis.protocoller.Protocoller;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    protected ApiUser api;

    @Override
    public void onEnable() {
        this.api = Protocoller.registerUser((Plugin) this);
    }

    @Override
    public void onDisable() {
        api = null;
    }
}
