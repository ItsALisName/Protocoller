package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.entity.player.PlayerAbilities;
import net.alis.protocoller.util.annotations.NotOnAllVersions;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInAbilities implements Packet {

    private final PacketDataContainer packetData;
    private @NotOnAllVersions boolean isInvulnerable;
    private boolean isFlying;
    private @NotOnAllVersions boolean canFly;
    private @NotOnAllVersions boolean canInstantlyBuild;
    private @NotOnAllVersions float flySpeed;
    private @NotOnAllVersions float walkSpeed;

    private final boolean legacy = GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16);

    public PacketPlayInAbilities(PacketDataContainer packetData) {
        this.packetData = packetData;
        if(!legacy) {
            this.isFlying = packetData.readBoolean(0);
        } else {
            this.isInvulnerable = packetData.readBoolean(0);
            this.isFlying = packetData.readBoolean(1);
            this.canFly = packetData.readBoolean(2);
            this.canInstantlyBuild = packetData.readBoolean(3);
            this.flySpeed = packetData.readFloat(0);
            this.walkSpeed = packetData.readFloat(1);
        }
    }

    public PacketPlayInAbilities(@NotNull PlayerAbilities abilities) {
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, abilities.createOriginal()));
        this.isInvulnerable = abilities.isInvulnerable();
        this.isFlying = abilities.isFlying();
        this.canFly = abilities.isCanFly();
        this.canInstantlyBuild = abilities.isCanInstantlyBuild();
        this.flySpeed = abilities.getFlySpeed();
        this.walkSpeed = abilities.getWalkSpeed();
    }

    @NotOnAllVersions
    public boolean isInvulnerable() {
        return isInvulnerable;
    }

    @NotOnAllVersions
    public void setInvulnerable(boolean invulnerable) {
        if(legacy) this.packetData.writeBoolean(0, invulnerable);
        isInvulnerable = invulnerable;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        if(legacy) {
            this.packetData.writeBoolean(1, flying);
        } else {
            this.packetData.writeBoolean(0, flying);
        }
        isFlying = flying;
    }

    @NotOnAllVersions
    public boolean isCanFly() {
        return canFly;
    }

    @NotOnAllVersions
    public void setCanFly(boolean canFly) {
        if(legacy) this.packetData.writeBoolean(2, canFly);
        this.canFly = canFly;
    }

    @NotOnAllVersions
    public boolean isCanInstantlyBuild() {
        return canInstantlyBuild;
    }

    @NotOnAllVersions
    public void setCanInstantlyBuild(boolean canInstantlyBuild) {
        if(legacy) this.packetData.writeBoolean(3, canInstantlyBuild);
        this.canInstantlyBuild = canInstantlyBuild;
    }

    @NotOnAllVersions
    public float getFlySpeed() {
        return flySpeed;
    }

    @NotOnAllVersions
    public void setFlySpeed(float flySpeed) {
        if(legacy) this.packetData.writeFloat(0, flySpeed);
        this.flySpeed = flySpeed;
    }

    @NotOnAllVersions
    public float getWalkSpeed() {
        return walkSpeed;
    }

    @NotOnAllVersions
    public void setWalkSpeed(float walkSpeed) {
        if(legacy) this.packetData.writeFloat(1, walkSpeed);
        this.walkSpeed = walkSpeed;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ABILITIES;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
