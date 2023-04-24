package net.alis.protocoller.plugin.util;

import libraries.net.md_5.bungee.api.chat.HoverEvent;
import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandUtils {

    public static @NotNull List<String> sortCompletions(@NotNull List<String> list, String @NotNull [] args){
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for(String s : list) if(s.startsWith(last)) result.add(s);
        return result;
    }

    public static void noPermsMessage(CommandSender sender, String permission) {
        if(sender instanceof Player) {
            ChatComponent noPerms = new ChatComponent(ProtocollerConfig.getCommandsDenyMessage());
            noPerms.setHoverEvent(HoverEvent.Action.SHOW_TEXT, "&cMissing permission: &c&o" + permission);
            IProtocolAccess.get().getPlayer(sender.getName()).sendChatMessage(noPerms);
        } else {
            sender.sendMessage(Utils.colors(ProtocollerConfig.getCommandsDenyMessage()));
        }
    }

}
