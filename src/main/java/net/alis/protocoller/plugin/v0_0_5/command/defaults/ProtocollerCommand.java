package net.alis.protocoller.plugin.v0_0_5.command.defaults;

import libraries.net.md_5.bungee.api.chat.ClickEvent;
import libraries.net.md_5.bungee.api.chat.HoverEvent;
import net.alis.protocoller.Protocoller;
import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.events.RegisteredProtocollerListener;
import net.alis.protocoller.plugin.providers.ApiProvider;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.updater.ProtocollerUpdater;
import net.alis.protocoller.plugin.util.CommandUtils;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.api.ClientsList;
import net.alis.protocoller.plugin.v0_0_5.api.ProtocollerApi;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProtocollerCommand extends BukkitCommand {

    public ProtocollerCommand(@NotNull String name) {
        super(name);
        this.description = "Main plugin command";
        this.setPermission("protocoller.command");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if(ProtocollerConfig.isAsyncCommandExecution()) {
            ITaskAccess.get().doAsync(() -> run$execute(commandSender, s, strings));
            return true;
        }
        return run$execute(commandSender, s, strings);
    }

    public boolean run$execute(CommandSender commandSender, String label, String[] args) {
        if(commandSender.hasPermission("protocoller.command")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                if(commandSender instanceof Player) {
                    IProtocolAccess.get().getPlayer(commandSender.getName()).sendChatMessage(helpMessage(commandSender));
                    return true;
                }
                commandSender.sendMessage(helpMessage());
                return true;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if(commandSender.hasPermission("protocoller.command.reload")) {
                    ProtocollerConfig.reload();
                    commandSender.sendMessage(Utils.colors("&a[Protocoller] Plugin configuration reloaded."));
                } else {
                    CommandUtils.noPermsMessage(commandSender, "protocoller.command.reload");
                }
                return true;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("checkupdates")) {
                if(commandSender.hasPermission("protocoller.command.checkupdates")) {
                    commandSender.sendMessage(Utils.colors("&e[Protocoller] Checking for newer version..."));
                    ProtocollerUpdater.checkUpdate();
                } else {
                    CommandUtils.noPermsMessage(commandSender, "protocoller.command.checkupdates");
                }
                return true;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("version")) {
                if(commandSender.hasPermission("protocoller.command.version")){
                    if (commandSender instanceof Player && commandSender.hasPermission("protocoller.command.checkupdates")) {
                        IProtocolAccess.get().getPlayer(commandSender.getName()).sendChatMessage(sendVersionInformation0());
                    } else {
                        commandSender.sendMessage(sendVersionInformation());
                    }
                } else {
                    CommandUtils.noPermsMessage(commandSender, "protocoller.command.version");
                }
                return true;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("users")) {
                if(commandSender.hasPermission("protocoller.command.users")) {
                    if(commandSender instanceof Player) {
                        IProtocolAccess.get().getPlayer(commandSender.getName()).sendChatMessage(sendUsersList(commandSender));
                    } else {
                        commandSender.sendMessage(sendUsersList());
                    }
                } else {
                    CommandUtils.noPermsMessage(commandSender, "protocoller.command.users");
                }
                return true;
            }
            if(args.length == 3 && args[0].equalsIgnoreCase("users") && args[1].equalsIgnoreCase("unregister")) {
                if(!commandSender.hasPermission("protocoller.command.users")) {
                    CommandUtils.noPermsMessage(commandSender, "protocoller.command.users");
                    return true;
                }
                if(!commandSender.hasPermission("protocoller.command.users.unregister")) {
                    CommandUtils.noPermsMessage(commandSender, "protocoller.command.users.unregister");
                    return true;
                }
                String userName = args[2];
                ClientsList clientsList = ((ProtocollerApi)Protocoller.get()).getClients();
                ProtocollerClient client = clientsList.get(userName);
                if(client == null) {
                    commandSender.sendMessage(Utils.colors("&c[Protocoller] No user found with the name \"" + userName + "\"!"));
                    return true;
                }
                ((ApiProvider)client).removeAllListeners();
                clientsList.unregister(client);
                commandSender.sendMessage(Utils.colors("&a[Protocoller] Client \"" + userName + "\" unregistered."));
                return true;
            }
            commandSender.sendMessage("&c[Protocoller] Unknown subcommand, use '/protocoller ?' for help");
            return true;
        } else {
            CommandUtils.noPermsMessage(commandSender,"protocoller.command");
        }
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();
        if(sender.hasPermission("protocoller.command")) {
            if(args.length == 1) {
                completions.add("?");
                completions.add("help");
                if (sender.hasPermission("protocoller.command.version")) completions.add("version");
                if (sender.hasPermission("protocoller.command.reload")) completions.add("reload");
                if (sender.hasPermission("protocoller.command.checkupdates")) completions.add("checkupdates");
                if (sender.hasPermission("protocoller.command.users")) completions.add("users");
                return CommandUtils.sortCompletions(completions, args);
            }
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("users") && sender.hasPermission("protocoller.command.users.unregister")) {
                    completions.add("unregister");
                    return completions;
                }
            }
            if(args.length == 3) {
                if(args[1].equalsIgnoreCase("unregister") && sender.hasPermission("protocoller.command.users.unregister")) {
                    ClientsList users = ((ProtocollerApi) Protocoller.get()).getClients();
                    for(ProtocollerClient client : users.get()) completions.add(client.getName());
                    return CommandUtils.sortCompletions(completions, args);
                }
            }
        }
        return completions;
    }

    private @NotNull ChatComponent helpMessage(CommandSender sender) {
        ChatComponent help = new ChatComponent("");
        help.append("&a/protocoller version &f- shows information about the current version");
        if(sender.hasPermission("protocoller.command.version"))
            help.append(new ChatComponent(" &2[RUN]").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&aRun command").setClickEvent(ClickEvent.Action.RUN_COMMAND, "/protocoller version")).newLine();
        help.append("&a/protocoller reload &f- reloads the configuration");
        if(sender.hasPermission("protocoller.command.reload"))
            help.append(new ChatComponent(" &2[RUN]").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&aRun command").setClickEvent(ClickEvent.Action.RUN_COMMAND, "/protocoller reload")).newLine();
        help.append("&a/protocoller checkupdates &f- starts checking for a new version");
        if(sender.hasPermission("protocoller.command.checkupdates"))
            help.append(new ChatComponent(" &2[RUN]").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&aRun command").setClickEvent(ClickEvent.Action.RUN_COMMAND, "/protocoller checkupdates")).newLine();
        help.append("&a/protocoller users &f- shows a list of plugin users");
        if(sender.hasPermission("protocoller.command.users"))
            help.append(new ChatComponent(" &2[RUN]").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&aRun command").setClickEvent(ClickEvent.Action.RUN_COMMAND, "/protocoller users")).newLine();
        help.append("&a/protocoller users unregister <user> &f- deletes the plugin user");
        if(sender.hasPermission("protocoller.commands.users.unregister"))
            help.append(new ChatComponent(" &e[SUGGEST]").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&aSuggest command").setClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/protocoller users unregister "));
        return help;
    }

    private @NotNull String helpMessage() {
        StringBuilder message = new StringBuilder();
        message.append("&a/protocoller version &f- shows information about the current version").append("\n");
        message.append("&a/protocoller reload &f- reloads the configuration").append("\n");
        message.append("&a/protocoller checkupdates &f- starts checking for a new version").append("\n");
        message.append("&a/protocoller users &f- shows a list of plugin users");
        return Utils.colors(message.toString());
    }

    private String sendVersionInformation() {
        StringBuilder info = new StringBuilder();
        Date lastCheck = ProtocollerUpdater.getLastCheck();
        info.append("&a[Protocoller] Current version: &2&o").append(Protocoller.get().getVersion()).append("\n");
        info.append("&a[Protocoller] Latest version: &2&o").append(ProtocollerUpdater.getLatest()).append("\n");
        info.append("&a[Protocoller] Last update check: &2&o").append(Utils.timeOf(lastCheck)).append(", ").append(Utils.dateOf(lastCheck, true));
        return Utils.colors(info.toString());
    }

    private ChatComponent sendVersionInformation0() {
        ChatComponent info = new ChatComponent(sendVersionInformation());
        return info.append(new ChatComponent("&a[CHECK FOR UPDATES]").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&aClick to start the update check")).setClickEvent(ClickEvent.Action.RUN_COMMAND, "/protocoller checkupdates");
    }

    private String sendUsersList() {
        ClientsList clientsList = ((ProtocollerApi)Protocoller.get()).getClients();
        if(clientsList.get().size() == 0) {
            return Utils.colors("&e[Protocoller] No registered users found.");
        }
        StringBuilder users = new StringBuilder("&aRegistered plugin users: ").append("\n");
        for(ProtocollerClient client : clientsList.get()) {
            ApiProvider api = (ApiProvider) client;
            users.append("&a&l* &a").append(api.getFullName()).append("\n");
        }
        return Utils.colors(users.toString());
    }

    private ChatComponent sendUsersList(CommandSender sender) {
        ClientsList clientsList = ((ProtocollerApi)Protocoller.get()).getClients();
        if(clientsList.get().size() == 0) {
            return new ChatComponent("&e[Protocoller] No registered users found.");
        }
        ChatComponent users = new ChatComponent("&aRegistered plugin users: ").newLine();
        boolean hasPermissionToUnreg = sender.hasPermission("protocoller.command.users.unregister");
        for(ProtocollerClient client : clientsList.get()) {
            ApiProvider api = (ApiProvider) client;
            StringBuilder deep = new StringBuilder("&fTotal registered listeners: \n");
            for(RegisteredProtocollerListener listener : api.getListeners()) {
                String[] methodName = listener.getMethod().getName().split("\\.");
                deep.append("&a* ").append(listener.getListener().getClass().getName()).append(" &fin method &a").append(methodName[methodName.length - 1]).append("\n");
            }
            ChatComponent info = new ChatComponent("&a&l* " + api.getFullName()).setHoverEvent(HoverEvent.Action.SHOW_TEXT, Utils.colors(deep.toString()));
            if(hasPermissionToUnreg)
                info.append(new ChatComponent(" &c[UNREGISTER]").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&cClick for unregister user")).setClickEvent(ClickEvent.Action.RUN_COMMAND, "/protocoller users unregister " + api.getName());
            users.append(info.newLine());
        }
        return users;
    }

}
