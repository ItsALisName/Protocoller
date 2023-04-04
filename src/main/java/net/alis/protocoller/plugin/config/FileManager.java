package net.alis.protocoller.plugin.config;

import org.bukkit.plugin.Plugin;

public class FileManager {

    public static void createFiles(Plugin plugin) {
        if(!ConfigUtils.getFile("general.yml").exists())
            plugin.saveResource("general.yml", false);
    }

}
