package net.alis.protocoller.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public class ITaskAccess {

    private final Plugin plugin;

    private final BukkitScheduler scheduler;
    private ITaskAccess(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
        INSTANCE = this;
    }

    public void doAsync(@NotNull BukkitRunnable task) {
        task.runTaskAsynchronously(plugin);
    }

    public void doAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }
    public void doAsyncLater(@NotNull BukkitRunnable task, long delay) {
        task.runTaskLaterAsynchronously(plugin, delay);
    }

    public void doAsyncLater(Runnable task, long delay) {
        scheduler.runTaskLaterAsynchronously(plugin, task, delay);
    }

    public void doAsyncTimerTask(@NotNull BukkitRunnable task, long delay, long period) {
        task.runTaskTimerAsynchronously(plugin, delay, period);
    }

    public void doAsyncTimerTask(Runnable task, long delay, long period) {
        scheduler.runTaskTimerAsynchronously(plugin, task, delay, period);
    }

    public void doSync(@NotNull BukkitRunnable task) {
        task.runTask(plugin);
    }

    public void doSync(Runnable task) {
        scheduler.runTask(plugin, task);
    }
    public void doSyncLater(@NotNull BukkitRunnable task, long delay) {
        task.runTaskLater(plugin, delay);
    }

    public void doSyncLater(Runnable task, long delay) {
        scheduler.runTaskLater(plugin, task, delay);
    }

    public void doSyncTimerTask(@NotNull BukkitRunnable task, long delay, long period) {
        task.runTaskTimer(plugin, delay, period);
    }

    public void doSyncTimerTask(Runnable task, long delay, long period) {
        scheduler.runTaskTimer(plugin, task, delay, period);
    }


    private static ITaskAccess INSTANCE;
    public static void init(Plugin plugin) {
        new ITaskAccess(plugin);
    }
    public static ITaskAccess get() {
        return INSTANCE;
    }
}
