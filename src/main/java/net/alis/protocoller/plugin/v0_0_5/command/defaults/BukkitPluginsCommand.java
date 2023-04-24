package net.alis.protocoller.plugin.v0_0_5.command.defaults;

import libraries.net.md_5.bungee.api.chat.HoverEvent;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class BukkitPluginsCommand extends BukkitCommand {

    public BukkitPluginsCommand(@NotNull String name) {
        super(name);
        this.description = "Gets a list of plugins running on the server";
        this.usageMessage = "/plugins";
        this.setPermission("bukkit.command.plugins");
        this.setAliases(List.of("pl"));
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String currentAlias, @NotNull String[] args) {
        if(ProtocollerConfig.isAsyncCommandExecution()) {
            ITaskAccess.get().doAsync(() -> run$execute(sender, currentAlias, args));
        } else {
            return run$execute(sender, currentAlias, args);
        }
        return true;
    }

    public boolean run$execute(@NotNull CommandSender sender, String currentAlias, String[] args) {
        if (!testPermissionSilent(sender)) {
            if(sender instanceof Player) {
                ChatComponent denyMessage = new ChatComponent(ProtocollerConfig.getCommandsDenyMessage());
                denyMessage.setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&cMissing permission: " + this.getPermission());
                ((NetworkPlayer)sender).sendChatMessage(denyMessage);
            } else {
                sender.sendMessage(Utils.colors(ProtocollerConfig.getCommandsDenyMessage()));
            }
        } else {
            if(!(sender instanceof Player)){
                sender.sendMessage(sendResult$1());
            } else {
                IProtocolAccess.get().getPlayer(sender.getName()).sendChatMessage(sendResult$0());

            }
        }
        return true;
    }

    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }

    public ChatComponent sendResult$0() {
        ChatComponent result = new ChatComponent();
        Plugin[] all = Bukkit.getPluginManager().getPlugins();
        int disabledPlugins = 0;
        for(Plugin plugin : all) {
            ChatComponent plug = new ChatComponent();
            if(plugin.isEnabled()) {
                plug.append("&3&l*  " + plugin.getName() + " &f[Status: &a&oEnabled&f, Version: &a&o" + plugin.getDescription().getVersion() + "&f, Authors: &a&o" + String.join(", ", plugin.getDescription().getAuthors()));
            } else {
                plug.append("&3&l*  " + plugin.getName() + " &f[Status: &c&oDisabled&f, Version: &a&o" + plugin.getDescription().getVersion() + "&f, Authors: &a&o" + String.join(", ", plugin.getDescription().getAuthors()));
                disabledPlugins += 1;
            }
            if(Reflect.getMethod(PluginDescriptionFile.class, "getProvides", true) != null && plugin.getDescription().getProvides().size() > 0) {
                plug.append("&f, Provides: &a&l" + String.join(", ", plugin.getDescription().getProvides()) + "&f]\n");
            } else {
                plug.append("&f]\n");
            }
            StringBuilder more = new StringBuilder();
            if(Utils.isNotNullText(plugin.getDescription().getFullName())) {
                more.append("&fFull name: &a&o").append(plugin.getDescription().getFullName()).append("\n");
            }
            more.append("&fMain class: &a&o").append(plugin.getDescription().getMain()).append("\n");
            if(Utils.isNotNullText(plugin.getDescription().getDescription())) {
                more.append("&fDescription: &a&o").append(plugin.getDescription().getDescription()).append("\n");
            }
            if(plugin.getDescription().getDepend().size() > 0) {
                more.append("&fDepend: &a&o").append(String.join(", ", plugin.getDescription().getDepend())).append("\n");
            }
            if(plugin.getDescription().getSoftDepend().size() > 0) {
                more.append("&fSoft depend: &a&o").append(String.join(", ", plugin.getDescription().getSoftDepend())).append("\n");
            }
            more.append("&fLoad: &a&o").append(plugin.getDescription().getLoad()).append("\n");
            if(plugin.getDescription().getLoadBefore().size() > 0) {
                more.append("&fLoad before: &a&o").append(String.join(", ", plugin.getDescription().getLoadBefore())).append("\n");
            }
            plug.setHoverEvent(HoverEvent.Action.SHOW_TEXT, Utils.colors(more.toString()));
            result.append(plug);
        }
        result.appendOnStart("&f&oTotal plugins - &e&o" + all.length + "&f&o; Disabled - &c&o" + disabledPlugins + "\n");
        result.appendOnStart("&3Command provided by Protocoller\n");
        return result;
    }

    public String sendResult$1() {
        StringBuilder result = new StringBuilder();
        Plugin[] all = Bukkit.getPluginManager().getPlugins();
        int disabledPlugins = 0;
        for(Plugin plugin : all) {
            if(plugin.isEnabled()) {
                result.append("&3&l*  " + plugin.getName() + " &f[Status: &a&oEnabled&f, Version: &a&o" + plugin.getDescription().getVersion() + "&f, Authors: &a&o" + String.join(", ", plugin.getDescription().getAuthors()));
            } else {
                result.append("&3&l*  " + plugin.getName() + " &f[Status: &c&oDisabled&f, Version: &a&o" + plugin.getDescription().getVersion() + "&f, Authors: &a&o" + String.join(", ", plugin.getDescription().getAuthors()));
                disabledPlugins += 1;
            }
            if(Reflect.getMethod(PluginDescriptionFile.class, "getProvides", true) != null && plugin.getDescription().getProvides().size() > 0) {
                result.append("&f, Provides: &a&l" + String.join(", ", plugin.getDescription().getProvides()) + "&f]\n");
            } else {
                result.append("&f]\n");
            }
        }
        return Utils.colors("&3&lCommand provided by Protocoller\n&f&oTotal plugins - &e&o" + all.length + "&f&o; Disabled - &c&o" + disabledPlugins + "\n" + result);
    }
}

