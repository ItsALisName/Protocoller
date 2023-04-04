package net.alis.protocoller.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class ProtocollerPlugin extends JavaPlugin {

    private ProtocollerInitializer initializer;

    @Override
    public void onLoad() {
        initializer = new ProtocollerInitializer(this);
        this.initializer.asyncPreLoad();
    }

    @Override
    public void onEnable() {
        this.initializer.syncLoad();
    }

    @Override
    public void onDisable() {
        this.initializer.unload();
    }
}
