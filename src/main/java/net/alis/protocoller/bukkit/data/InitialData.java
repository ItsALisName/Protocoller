package net.alis.protocoller.bukkit.data;

import net.alis.protocoller.bukkit.enums.Version;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class InitialData {

    public static InitialData INSTANCE;
    private final String serverPackageVersion;
    private final String packetsPackage;
    private final boolean isLegacyServer;
    private final String craftBukkitPackage;
    private final Version preVersion;

    InitialData(Server server) {
        String packageName = server.getClass().getPackage().getName();
        this.serverPackageVersion = packageName.substring(packageName.lastIndexOf(46) + 1);
        this.preVersion = Version.fromPackageName(this.serverPackageVersion);
        this.craftBukkitPackage = "org.bukkit.craftbukkit." + this.serverPackageVersion;
        if(preVersion.ordinal() < Version.v1_17.ordinal()) {
            this.packetsPackage = "net.minecraft.server." + this.serverPackageVersion;
            this.isLegacyServer = true;
        } else {
            this.packetsPackage = "net.minecraft.network.protocol";
            this.isLegacyServer = false;
        }
    }

    public String getPacketsPackage() {
        return packetsPackage;
    }

    public String getPackageVersion() {
        return serverPackageVersion;
    }

    public String getCraftBukkitPackage() {
        return craftBukkitPackage;
    }

    public boolean isLegacyServer() {
        return isLegacyServer;
    }

    public Version getPreVersion() {
        return preVersion;
    }

    public static void init(Server server) {
        INSTANCE = new InitialData(server);
    }
}
