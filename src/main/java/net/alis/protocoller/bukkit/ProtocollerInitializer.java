package net.alis.protocoller.bukkit;

import net.alis.EXAMPLES.TEST;
import net.alis.protocoller.bukkit.config.FileManager;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.data.InitialData;
import net.alis.protocoller.bukkit.data.PacketCreators;
import net.alis.protocoller.bukkit.network.packet.PacketTypesInitializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.server.UpdatePlayerDataRunner;
import net.alis.protocoller.bukkit.server.UpdateServerDataRunner;
import net.alis.protocoller.bukkit.server.listeners.InjectionListener;
import net.alis.protocoller.bukkit.util.TaskSimplifier;
import net.alis.protocoller.parent.network.chat.ChatSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ProtocollerInitializer {

    private final Plugin plugin;

    protected ProtocollerInitializer(Plugin plugin) {
        this.plugin = plugin;
    }

    protected void syncPreLoad() {
        TaskSimplifier.init(this.plugin);
        InitialData.init(Bukkit.getServer());
        ClassesContainer.init();
        ChatSerializer.init();
        FileManager.createFiles(this.plugin);
        PacketTypesInitializer.init();
        PacketCreators.init();
        GlobalProvider.init();
    }

    protected void asyncPreLoad() {
        new Thread(this::syncPreLoad).start();
    }

    protected void syncLoad() {
        UpdateServerDataRunner.start();
        UpdatePlayerDataRunner.start();
        Bukkit.getPluginManager().registerEvents(new InjectionListener(), this.plugin);

        new TEST(this.plugin);
    }

    protected void unload() {
        Bukkit.getServer().getScheduler().cancelTasks(this.plugin);
    }
}
