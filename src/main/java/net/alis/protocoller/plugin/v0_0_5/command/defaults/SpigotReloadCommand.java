package net.alis.protocoller.plugin.v0_0_5.command.defaults;

import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpigotReloadCommand extends BukkitCommand {

    public SpigotReloadCommand(@NotNull String name) {
        super(name);
        this.description = "Reloads the server configuration and plugins";
        this.usageMessage = "/reload";
        this.setPermission("bukkit.command.reload");
        this.setAliases(Arrays.asList("rl"));
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String currentAlias, @NotNull String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        } else {
            if(!ProtocollerConfig.isDisableReloadCommand()){
                Command.broadcastCommandMessage(sender, ChatColor.RED + "Please note that this command is not supported and may cause issues when using some plugins.");
                Command.broadcastCommandMessage(sender, ChatColor.RED + "If you encounter any issues please use the /stop command to restart your server.");
                Bukkit.reload();
                Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Reload complete.");
                return true;
            }
            sender.sendMessage(Utils.colors("&cThis commands disabled by Protocoller, you can enable it in \"general.yml\""));
            return true;
        }
    }

    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }
}

