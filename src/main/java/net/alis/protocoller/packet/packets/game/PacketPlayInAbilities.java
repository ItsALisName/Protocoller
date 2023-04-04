package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.entity.player.PlayerAbilities;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInAbilities implements PlayInPacket {

    private final PacketDataContainer packetData;
    private boolean isInvulnerable;
    private boolean isFlying;
    private boolean canFly;
    private boolean canInstantlyBuild;
    private float flySpeed;
    private float walkSpeed;

    public PacketPlayInAbilities(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(!GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16)) {
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

    public boolean isInvulnerable() {
        return isInvulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16)) this.packetData.writeBoolean(0, invulnerable);
        isInvulnerable = invulnerable;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16)) {
            this.packetData.writeBoolean(1, flying);
        } else {
            this.packetData.writeBoolean(0, flying);
        }
        isFlying = flying;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16)) this.packetData.writeBoolean(2, canFly);
        this.canFly = canFly;
    }

    public boolean isCanInstantlyBuild() {
        return canInstantlyBuild;
    }

    public void setCanInstantlyBuild(boolean canInstantlyBuild) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16)) this.packetData.writeBoolean(3, canInstantlyBuild);
        this.canInstantlyBuild = canInstantlyBuild;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16)) this.packetData.writeFloat(0, flySpeed);
        this.flySpeed = flySpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16)) this.packetData.writeFloat(1, walkSpeed);
        this.walkSpeed = walkSpeed;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ABILITIES;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
