package ExamplePlugin;

import net.alis.protocoller.NetworkServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandWorkingExample {

    public void registerCommands() {
        NetworkServer server = MainClass.protocollerApi.getServer();
        Command command = server.getCommandMap().getCommand("tpa");
        server.getCommandMap().unregister(command);
        server.getCommandMap().register("plugin_name", new ExampleCommand("example"));
        server.syncCommands();
    }

    public static class ExampleCommand extends Command {

        protected ExampleCommand(@NotNull String name) {
            super(name);
            this.setPermission("example.permission");
            this.setAliases(List.of("example1", "example2"));
        }

        @Override
        public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
            commandSender.sendMessage("Command successfully executed!");
            return true;
        }

        @NotNull
        @Override
        public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
            return List.of("Your", "custom", "completions");
        }
    }

}