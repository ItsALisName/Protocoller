package net.alis.protocoller.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public class TaskSimplifier {

    private final Plugin plugin;

    private final BukkitScheduler scheduler;
    private TaskSimplifier(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
        INSTANCE = this;
    }

    public void preformAsync(@NotNull BukkitRunnable task) {
        task.runTaskAsynchronously(plugin);
    }

    public void preformAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }
    public void preformAsyncLater(@NotNull BukkitRunnable task, long delay) {
        task.runTaskLaterAsynchronously(plugin, delay);
    }

    public void preformAsyncLater(Runnable task, long delay) {
        scheduler.runTaskLaterAsynchronously(plugin, task, delay);
    }

    public void preformAsyncTimerTask(@NotNull BukkitRunnable task, long delay, long period) {
        task.runTaskTimerAsynchronously(plugin, delay, period);
    }

    public void preformAsyncTimerTask(Runnable task, long delay, long period) {
        scheduler.runTaskTimerAsynchronously(plugin, task, delay, period);
    }

    public void preformSync(@NotNull BukkitRunnable task) {
        task.runTask(plugin);
    }

    public void preformSync(Runnable task) {
        scheduler.runTask(plugin, task);
    }
    public void preformSyncLater(@NotNull BukkitRunnable task, long delay) {
        task.runTaskLater(plugin, delay);
    }

    public void preformSyncLater(Runnable task, long delay) {
        scheduler.runTaskLater(plugin, task, delay);
    }

    public void preformSyncTimerTask(@NotNull BukkitRunnable task, long delay, long period) {
        task.runTaskTimer(plugin, delay, period);
    }

    public void preformSyncTimerTask(Runnable task, long delay, long period) {
        scheduler.runTaskTimer(plugin, task, delay, period);
    }


    private static TaskSimplifier INSTANCE;
    public static void init(Plugin plugin) {
        new TaskSimplifier(plugin);
    }
    public static TaskSimplifier get() {
        return INSTANCE;
    }
}
