package net.alis.protocoller.plugin.v0_0_5.command.defaults;

import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.util.CommandUtils;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BukkitVersionCommand extends BukkitCommand {

    private List<String> pluginNames;

    public BukkitVersionCommand(@NotNull String name) {
        super(name);
        this.description = "Gets the version of this server including any plugins in use";
        this.usageMessage = "/version [plugin name]";
        this.setPermission("bukkit.command.version");
        this.setAliases(Arrays.asList("ver", "about"));

        this.pluginNames = new ArrayList<>();
        for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            this.pluginNames.add(plugin.getName());
        }
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String currentAlias, @NotNull String[] args) {
        if(ProtocollerConfig.isAsyncCommandExecution()) {
            ITaskAccess.get().doAsync(() -> run$execute(sender, currentAlias, args));
            return true;
        }
        return run$execute(sender, currentAlias, args);
    }

    private boolean run$execute(CommandSender sender, String alias, String[] args) {
        if(sender.hasPermission(this.getPermission())) {
            if(args.length == 0) {
                sender.sendMessage(getVersionMessage());
                return true;
            }
            if(args.length == 1) {
                Plugin plugin = Bukkit.getPluginManager().getPlugin(args[0]);
                if(plugin == null) {
                    sender.sendMessage("There are no plugins with the name \"" + args[0] + "\" installed on this server");
                    return true;
                }
                sender.sendMessage(getPluginInfo(plugin));
                return true;
            }
            sender.sendMessage(Utils.colors("&cUnknown subcommand. Using \"/" + alias + " <plugin_name>\""));
        } else {
            CommandUtils.noPermsMessage(sender, this.getPermission());
        }
        return true;
    }

    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if(sender.hasPermission(this.getPermission()))
            if(args.length == 1) return CommandUtils.sortCompletions(this.pluginNames, args);
        return Collections.singletonList("");
    }

    private @NotNull String getVersionMessage() {
        return Utils.colors(ProtocollerConfig.getVersionCommandMessage().replace("%name%", Bukkit.getName()).replace("%version%", Bukkit.getVersion()).replace("%api_version%", Bukkit.getBukkitVersion()));
    }

    public String getPluginInfo(Plugin plugin) {
        StringBuilder info = new StringBuilder("&3Command provided by Protocoller\n").append("&fInformation about plugin &a").append(plugin.getName());
        info.append("\n  &fStatus: ");
        if(plugin.isEnabled())
            info.append("&aEnabled").append("\n");
        else
            info.append("&cDisabled").append("\n");
        info.append("  &fVersion: &a").append(plugin.getDescription().getVersion()).append("\n");
        if(Utils.isNotNullText(plugin.getDescription().getWebsite())) info.append("  &fSite: &a").append(plugin.getDescription().getWebsite()).append("\n");
        info.append("  &fAuthors: &a").append(String.join(", ", plugin.getDescription().getAuthors())).append("\n");
        if(Utils.isNotNullText(plugin.getDescription().getDescription())) info.append("  &fDescription: &a").append(plugin.getDescription().getDescription()).append("\n");
        if(Reflect.getMethod(PluginDescriptionFile.class, "getProvides", true) != null) {
            String provides = String.join(", ", plugin.getDescription().getProvides());
            if(Utils.isNotNullText(provides)) info.append("  &fProvides: &a").append(provides).append("\n");
        }
        info.append("  &fMain class: &a").append(plugin.getDescription().getMain()).append("\n");
        info.append("  &fLoad type: &a").append(plugin.getDescription().getLoad().name());
        return Utils.colors(info.toString());
    }

}

