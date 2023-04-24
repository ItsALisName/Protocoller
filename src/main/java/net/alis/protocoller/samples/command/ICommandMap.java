package net.alis.protocoller.samples.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public interface ICommandMap {

    void registerAll(@NotNull String fallbackPrefix, @NotNull List<Command> commands);

    boolean register(@NotNull String fallbackPrefix, @NotNull Command command);

    boolean register(@NotNull String label, @NotNull String fallbackPrefix, @NotNull Command command);

    boolean register(@NotNull String label, @NotNull Command command, boolean isAlias, @NotNull String fallbackPrefix);

    boolean dispatch(@NotNull CommandSender sender, @NotNull String commandLine) throws CommandException;

    void clearCommands();

    Command getCommand(@NotNull String name);

    List<String> tabComplete(@NotNull CommandSender sender, @NotNull String cmdLine);

    List<String> tabComplete(@NotNull CommandSender sender, @NotNull String cmdLine, @Nullable Location location);

    Collection<Command> getCommands();

    boolean unregister(Command command);

}
