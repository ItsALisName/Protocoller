package net.alis.protocoller.plugin.memory;

import net.alis.protocoller.plugin.enums.Version;
import org.bukkit.Bukkit;

public class ApproximateData {

    private final String serverPackageVersion;
    private final String packetsPackage;
    private final boolean isLegacyServer;
    private final String craftBukkitPackage;
    private final Version preVersion;

    private final String javaVersion;
    private final String jdkVersion;
    private final String osName;
    private final String osVersion;
    private final String osArchitecture;
    ApproximateData() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
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
        this.javaVersion = System.getProperty("java.version");
        this.jdkVersion = System.getProperty("java.runtime.version");
        this.osName = System.getProperty("os.name");
        this.osVersion = System.getProperty("os.version");
        this.osArchitecture = System.getProperty("os.arch");
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getJdkVersion() {
        return jdkVersion;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getOsArchitecture() {
        return osArchitecture;
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

    public static void init() {
        INSTANCE = new ApproximateData();
    }

    public static ApproximateData get() {
        return INSTANCE;
    }

    private static ApproximateData INSTANCE;
}
