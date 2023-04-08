package ExamplePlugin;

import net.alis.protocoller.Protocoller;
import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.event.PacketEventsManager;
import net.alis.protocoller.event.impl.ManagerType;
import net.alis.protocoller.plugin.exception.BannedClientException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainClass extends JavaPlugin {

    public static ProtocollerClient protocollerApi;
    public static PacketEventsManager syncEventManager;
    public static PacketEventsManager asyncEventManager;

    @Override
    public void onEnable() {
        try {
            protocollerApi = Protocoller.get().registerClient((Plugin) this);
        } catch (BannedClientException e) {
            throw new RuntimeException(e);
        }

        syncEventManager = protocollerApi.getEventManager(ManagerType.SYNCHRONOUS);
        asyncEventManager = protocollerApi.getEventManager(ManagerType.ASYNCHRONOUS);

        syncEventManager.registerListener(protocollerApi, new ExampleSyncPacketListener());
        asyncEventManager.registerListener(protocollerApi, new ExampleAsyncPacketListener());

    }

    @Override
    public void onDisable() { }
}
