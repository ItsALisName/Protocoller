package net.alis.protocoller.plugin;

import lombok.SneakyThrows;
import net.alis.protocoller.Protocoller;
import net.alis.protocoller.plugin.config.ConfigUtils;
import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.memory.ApproximateData;
import net.alis.protocoller.plugin.memory.PacketBuilders;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.updater.ProtocollerUpdater;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.api.ProtocollerApi;
import net.alis.protocoller.plugin.v0_0_5.command.ProtocolBukkitCommandMap;
import net.alis.protocoller.plugin.v0_0_5.command.ProtocolCommandListener;
import net.alis.protocoller.plugin.v0_0_5.entity.ProtocolPlayer;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketTypesInitializer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.v0_0_5.server.ProtocolServer;
import net.alis.protocoller.plugin.v0_0_5.server.tasks.dUCC_Task;
import net.alis.protocoller.plugin.v0_0_5.server.tasks.dUPD_Task;
import net.alis.protocoller.plugin.v0_0_5.server.tasks.dUSD_Task;
import net.alis.protocoller.plugin.util.FastUtilLegacyAdapter;
import net.alis.protocoller.plugin.util.Metrics;
import net.alis.protocoller.plugin.util.ProtocolUtil;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolChunkProviderServer;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolWorldServer;
import net.alis.protocoller.plugin.v0_0_5.server.level.border.ProtocolWorldBorder;
import net.alis.protocoller.plugin.v0_0_5.server.level.chunk.ProtocolChunk;
import net.alis.protocoller.plugin.v0_0_5.server.level.lighting.ProtocolLightEngine;
import net.alis.protocoller.samples.attributes.GenericAttributes;
import net.alis.protocoller.samples.craftbukkit.MagicNumbersSample;
import net.alis.protocoller.samples.crafting.CRecipe;
import net.alis.protocoller.samples.effect.MobEffects;
import net.alis.protocoller.samples.network.chat.ChatHexColor;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.plugin.v0_0_5.server.bukkit.InjectionListener;
import net.alis.protocoller.samples.network.chat.MessageTypeModern;
import net.alis.protocoller.samples.network.chat.OutgoingChatMessage;
import libraries.net.md_5.bungee.api.chat.Ext;
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
        ApproximateData.init();
        ClassAccessor.init();
        MobEffects.init();
        ProtocolWorldServer.init();
        ProtocolChunk.init();
        ProtocolServer.init();
        ProtocolPlayer.init();
        ProtocolChunkProviderServer.init();
        ProtocolBukkitCommandMap.init();
        ProtocolWorldBorder.init();
        ProtocolLightEngine.init();
        ProtocolCommandListener.init();
        GenericAttributes.init();
        MagicNumbersSample.init();
        ChatSerializer.init();
        MessageTypeModern.init();
        OutgoingChatMessage.init();
        ChatHexColor.init();
        ConfigUtils.createFiles(this.plugin, false);
        ProtocollerConfig.load((ProtocollerPlugin) this.plugin);
        /* ! */ Ext.Hover.getOriginalByName = Reflect.getMethod(ClassAccessor.get().getChatHoverActionEnumClass(), "a", ClassAccessor.get().getChatHoverActionEnumClass(), true, String.class);
        PacketTypesInitializer.init();
        CRecipe.RecipeSerializer.init();
        IProtocolAccess.init();
        if(IProtocolAccess.get() != null){
            Protocoller.protocoller.setProtocoller$provider(new ProtocollerApi());
            PacketBuilders.init();
            Metrics.forceBStatsEnable();
            LogsManager.get().sendPreloadingFinishedMessage(this.plugin.getDescription().getVersion());
        }
    }

    protected void asyncPreLoad() {
        new Thread(this::syncPreLoad).start();
    }

    @SneakyThrows
    protected void syncLoad() {
        if(IProtocolAccess.get() == null) {
            try {
                Thread.sleep(5000L);
                if(IProtocolAccess.get() == null) {
                    Bukkit.getLogger().severe("[Protocoller] The plugin still failed to load, probably the above error?");
                    Bukkit.getLogger().severe("[Protocoller] Please, report about that!");
                    Bukkit.getPluginManager().disablePlugin(this.plugin);
                    return;
                }
            } catch (InterruptedException e) {
                Bukkit.getLogger().severe("[Protocoller] Failed to pause server thread for Protocoller loading! Disabling...");
                Bukkit.getLogger().severe("[Protocoller] Please, report about that!");
                Bukkit.getPluginManager().disablePlugin(this.plugin);
                return;
            }
        }
        LogsManager.get().info("Protocoller enabled and ready for work!");
        ITaskAccess.init(this.plugin);
        dUSD_Task.start();
        dUCC_Task.start();
        dUPD_Task.start();
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
