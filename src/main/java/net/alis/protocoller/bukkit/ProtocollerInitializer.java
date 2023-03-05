package net.alis.protocoller.bukkit;

import net.alis.EXAMPLES.TEST;
import net.alis.protocoller.bukkit.config.FileManager;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.data.InitialData;
import net.alis.protocoller.bukkit.data.PacketBuilders;
import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.network.packet.PacketTypesInitializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.server.UpdatePlayerDataRunner;
import net.alis.protocoller.bukkit.server.UpdateServerDataRunner;
import net.alis.protocoller.bukkit.server.listeners.InjectionListener;
import net.alis.protocoller.bukkit.util.FastUtilLegacyAdapter;
import net.alis.protocoller.bukkit.util.TaskSimplifier;
import net.alis.protocoller.bukkit.util.Utils;
import net.alis.protocoller.parent.crafting.CRecipe;
import net.alis.protocoller.parent.network.chat.ChatSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ProtocollerInitializer {

    private final Plugin plugin;

    protected ProtocollerInitializer(Plugin plugin) {
        this.plugin = plugin;
    }

    protected void syncPreLoad() {
        LogsManager.init();
        LogsManager.get().sendPreloadingStartMessage();
        FastUtilLegacyAdapter.Classes.init();
        InitialData.init(Bukkit.getServer());
        ClassesContainer.init();
        ChatSerializer.init();
        FileManager.createFiles(this.plugin);
        PacketTypesInitializer.init();
        CRecipe.RecipeSerializer.init();
        PacketBuilders.init();
        GlobalProvider.init();
        LogsManager.get().sendPreloadingFinishedMessage(this.plugin.getDescription().getVersion());
    }

    protected void asyncPreLoad() {
        new Thread(this::syncPreLoad, "BootThread-" + Utils.generateRandomInt(5)).start();
    }

    protected void syncLoad() {
        if(GlobalProvider.instance() == null) {
            try {
                Bukkit.getLogger().info("[Protocoller/" + Thread.currentThread().getName() + "] For some unknown reason Protocoller hasn't loaded yet... Please wait!");
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        TaskSimplifier.init(this.plugin);
        UpdateServerDataRunner.start();
        UpdatePlayerDataRunner.start();
        Bukkit.getPluginManager().registerEvents(new InjectionListener(), this.plugin);

        new TEST(this.plugin);
    }

    protected void unload() {
        Bukkit.getServer().getScheduler().cancelTasks(this.plugin);
    }
}
