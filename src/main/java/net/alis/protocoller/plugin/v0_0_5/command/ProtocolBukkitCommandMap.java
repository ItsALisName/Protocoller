package net.alis.protocoller.plugin.v0_0_5.command;

import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.command.defaults.BukkitPluginsCommand;
import net.alis.protocoller.plugin.v0_0_5.command.defaults.BukkitVersionCommand;
import net.alis.protocoller.plugin.v0_0_5.command.defaults.ProtocollerCommand;
import net.alis.protocoller.plugin.v0_0_5.command.defaults.SpigotReloadCommand;
import net.alis.protocoller.plugin.v0_0_5.server.ProtocolServer;
import net.alis.protocoller.samples.command.ICommandMap;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProtocolBukkitCommandMap implements ICommandMap {

    private final SimpleCommandMap handle;
    private final ProtocolServer server;

    public ProtocolBukkitCommandMap(SimpleCommandMap handle, ProtocolServer server) {
        this.handle = handle;
        this.server = server;
    }

    public void registerProtocollerDefaults() {
        if(ProtocollerConfig.isReplacePluginsCommand()) plugins$command(new BukkitPluginsCommand("plugins"));
        reload$command(new SpigotReloadCommand("reload"));
        protocoller$command(new ProtocollerCommand("protocoller"));
        if(ProtocollerConfig.isReplaceVersionCommand()) version$command(new BukkitVersionCommand("version"));
        this.server.syncCommands();
    }

    public Map<String, Command> getKnownCommands() {
        return Reflect.readField(this.handle, commandsMapField, false);
    }

    public void registerAll(@NotNull String fallbackPrefix, @NotNull List<Command> commands) {
        this.handle.registerAll(fallbackPrefix, commands);
    }

    public boolean register(@NotNull String fallbackPrefix, @NotNull Command command) {
        return this.handle.register(fallbackPrefix, command);
    }

    public boolean register(@NotNull String label, @NotNull String fallbackPrefix, @NotNull Command command) {
        return this.handle.register(label, fallbackPrefix, command);
    }

    public synchronized boolean register(@NotNull String label, @NotNull Command command, boolean isAlias, @NotNull String fallbackPrefix) {
        return Reflect.callMethod(this.handle, registerMethod, false, label, command, isAlias, fallbackPrefix);
    }

    @Override
    public boolean unregister(@NotNull Command command) {
        return command.unregister(this.handle);
    }

    public boolean dispatch(@NotNull CommandSender sender, @NotNull String commandLine) throws CommandException {
        return this.handle.dispatch(sender, commandLine);
    }

    public synchronized void clearCommands() {
        this.handle.clearCommands();
        registerProtocollerDefaults();
        this.server.syncCommands();
    }

    public Command getCommand(@NotNull String name) {
        return this.handle.getCommand(name);
    }

    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String cmdLine) {
        return this.handle.tabComplete(sender, cmdLine);
    }

    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String cmdLine, @Nullable Location location) {
        return this.handle.tabComplete(sender, cmdLine, location);
    }

    public Collection<Command> getCommands() {
        return this.handle.getCommands();
    }

    public ProtocolServer getServer() {
        return server;
    }

    public SimpleCommandMap getHandle() {
        return handle;
    }

    private static Field commandsMapField;

    private static Method registerMethod;

    public static void init() {
        commandsMapField = Reflect.getField(SimpleCommandMap.class, "knownCommands", false);
        registerMethod = Reflect.getMethod(SimpleCommandMap.class, "register", boolean.class, false, String.class, Command.class, boolean.class, String.class);
    }

    private void plugins$command(BukkitPluginsCommand command) {
        Map<String, Command> currentCommands = getKnownCommands();
        currentCommands.put("bukkit:" + command.getName(), command);
        currentCommands.put(command.getName(), command);
        for(String alias : command.getAliases()) {
            currentCommands.put("bukkit:" + alias, command);
            currentCommands.put(alias, command);
        }
    }

    private void reload$command(SpigotReloadCommand command) {
        Map<String, Command> currentCommands = getKnownCommands();
        currentCommands.put("bukkit:" + command.getName(), command);
        currentCommands.put(command.getName(), command);
        for(String alias : command.getAliases()) {
            currentCommands.put("bukkit:" + alias, command);
            currentCommands.put(alias, command);
        }
    }

    private void protocoller$command(ProtocollerCommand command) {
        Map<String, Command> currentCommands = getKnownCommands();
        currentCommands.put("protocoller:" + command.getName(), command);
        currentCommands.put(command.getName(), command);
        for(String alias : command.getAliases()) {
            currentCommands.put("protocoller:" + alias, command);
            currentCommands.put(alias, command);
        }
    }

    private void version$command(BukkitVersionCommand command) {
        Map<String, Command> currentCommands = getKnownCommands();
        currentCommands.put("bukkit:" + command.getName(), command);
        currentCommands.put(command.getName(), command);
        for(String alias : command.getAliases()) {
            currentCommands.put("bukkit:" + alias, command);
            currentCommands.put(alias, command);
        }
    }
}
