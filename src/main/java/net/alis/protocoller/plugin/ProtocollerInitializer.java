package net.alis.protocoller.plugin;

import net.alis.protocoller.Protocoller;
import net.alis.protocoller.plugin.config.ConfigUtils;
import net.alis.protocoller.plugin.config.ProtocollerConfig;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.memory.InitialData;
import net.alis.protocoller.plugin.memory.PacketBuilders;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.updater.ProtocollerUpdater;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_4.api.ProtocollerApi;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketTypesInitializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.v0_0_4.server.UpdatePlayerDataRunner;
import net.alis.protocoller.plugin.v0_0_4.server.UpdateServerRunner;
import net.alis.protocoller.plugin.util.FastUtilLegacyAdapter;
import net.alis.protocoller.plugin.util.Metrics;
import net.alis.protocoller.plugin.util.ProtocolUtil;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import net.alis.protocoller.samples.attributes.GenericAttributes;
import net.alis.protocoller.samples.craftbukkit.MagicNumbersSample;
import net.alis.protocoller.samples.crafting.CRecipe;
import net.alis.protocoller.samples.effect.MobEffects;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.plugin.v0_0_4.server.bukkit.InjectionListener;
import net.md_5.bungee.api.chat.Ext;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

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
        ClassAccessor.init();
        MagicNumbersSample.init();
        ChatSerializer.init();
        ConfigUtils.createFiles(this.plugin, false);
        ProtocollerConfig.load((ProtocollerPlugin) this.plugin);
        /* ! */ Ext.Hover.getOriginalByName = Reflect.getMethod(ClassAccessor.get().getChatHoverActionEnumClass(), "a", ClassAccessor.get().getChatHoverActionEnumClass(), false, String.class);
        PacketTypesInitializer.init();
        CRecipe.RecipeSerializer.init();
        GlobalProvider.init();
        if(GlobalProvider.get() != null){
            Protocoller.protocoller.setProtocoller$provider(new ProtocollerApi());
            PacketBuilders.init();
            Metrics.forceBStatsEnable();
            LogsManager.get().sendPreloadingFinishedMessage(this.plugin.getDescription().getVersion());
        }
    }

    protected void asyncPreLoad() {
        new Thread(this::syncPreLoad).start();
    }

    protected void syncLoad() {
        if(GlobalProvider.get() == null) {
            try {
                Bukkit.getLogger().info("[Protocoller] For some unknown reason Protocoller hasn't loaded yet... Please wait!");
                Thread.sleep(5000L);
                if(GlobalProvider.get() == null) {
                    Bukkit.getPluginManager().disablePlugin(this.plugin);
                    return;
                }
            } catch (InterruptedException e) {
                Bukkit.getLogger().info("[Protocoller] Failed to pause server thread for Protocoller loading! Disabling...");
                Bukkit.getLogger().info("[Protocoller] Please, report about that!");
                Bukkit.getPluginManager().disablePlugin(this.plugin);
                return;
            }
        }
        LogsManager.get().info("Protocoller enabled and ready for work!");
        TaskSimplifier.init(this.plugin);
        UpdateServerRunner.start();
        UpdatePlayerDataRunner.start();
        Bukkit.getPluginManager().registerEvents(new InjectionListener(), this.plugin);
        if(ProtocollerConfig.isForceBStatsEnabled()) {
            new Metrics((JavaPlugin) this.plugin, 17877);
        } else {
            if(ProtocollerConfig.isBStatsEnabled()) {
                new Metrics((JavaPlugin) this.plugin, 17877);
            }
        }
        /* ! */ ProtocolUtil.viaVersionInstalled = this.plugin.getServer().getPluginManager().isPluginEnabled("ViaVersion");
        ProtocollerUpdater.init();
    }

    protected void unload() {
        Bukkit.getServer().getScheduler().cancelTasks(this.plugin);
    }
}
