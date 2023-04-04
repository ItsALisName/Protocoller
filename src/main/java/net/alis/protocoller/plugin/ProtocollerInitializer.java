package net.alis.protocoller.plugin;

import Example.TEST;
import net.alis.protocoller.plugin.config.FileManager;
import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.data.InitialData;
import net.alis.protocoller.plugin.data.PacketBuilders;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.network.packet.PacketTypesInitializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.server.UpdatePlayerDataRunner;
import net.alis.protocoller.plugin.server.UpdateServerDataRunner;
import net.alis.protocoller.plugin.server.listeners.InjectionListener;
import net.alis.protocoller.plugin.util.FastUtilLegacyAdapter;
import net.alis.protocoller.plugin.util.Metrics;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.samples.attributes.GenericAttributes;
import net.alis.protocoller.samples.craftbukkit.MagicNumbersSample;
import net.alis.protocoller.samples.crafting.CRecipe;
import net.alis.protocoller.samples.effect.MobEffects;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtocollerInitializer {

    private final Plugin plugin;

    protected ProtocollerInitializer(Plugin plugin) {
        this.plugin = plugin;
    }

    protected void syncPreLoad() {
        LogsManager.init();
        LogsManager.get().sendPreloadingStartMessage();
        FastUtilLegacyAdapter.Classes.init();
        GenericAttributes.init();
        MobEffects.init();
        InitialData.init(Bukkit.getServer());
        ClassesContainer.init();
        MagicNumbersSample.init();
        ChatSerializer.init();
        FileManager.createFiles(this.plugin);
        PacketTypesInitializer.init();
        CRecipe.RecipeSerializer.init();
        GlobalProvider.init();
        PacketBuilders.init();
        Utils.forceBStatsEnable();
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
                Bukkit.getLogger().info("[Protocoller/" + Thread.currentThread().getName() + "] Failed to pause server thread for Protocoller loading! Disabling...");
                Bukkit.getLogger().info("[Protocoller] Please, report about that!");
                Bukkit.getPluginManager().disablePlugin(this.plugin);
                return;
            }
        }
        TaskSimplifier.init(this.plugin);
        UpdateServerDataRunner.start();
        UpdatePlayerDataRunner.start();
        Bukkit.getPluginManager().registerEvents(new InjectionListener(), this.plugin);
        if(GlobalProvider.instance().getConfig().isForceBStatsEnabled()) {
            new Metrics((JavaPlugin) this.plugin, 17877);
        } else {
            if(GlobalProvider.instance().getConfig().isBStatsEnabled()) {
                new Metrics((JavaPlugin) this.plugin, 17877);
            }
        }
        new TEST(this.plugin);
    }

    protected void unload() {
        Bukkit.getServer().getScheduler().cancelTasks(this.plugin);
    }
}
