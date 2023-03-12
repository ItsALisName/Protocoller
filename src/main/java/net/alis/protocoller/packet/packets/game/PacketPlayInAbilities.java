package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.entity.player.PlayerAbilities;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInAbilities implements PlayInPacket {

    private final PacketDataContainer packetData;
    private @Nullable boolean isInvulnerable;
    private boolean isFlying;
    private @Nullable boolean canFly;
    private @Nullable boolean canInstantlyBuild;
    private @Nullable float flySpeed;
    private @Nullable float walkSpeed;

    private final boolean legacy = GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_16);

    public PacketPlayInAbilities(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
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

    @Nullable
    public boolean isInvulnerable() {
        return isInvulnerable;
    }

    @Nullable
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

    @Nullable
    public boolean isCanFly() {
        return canFly;
    }

    @Nullable
    public void setCanFly(boolean canFly) {
        if(legacy) this.packetData.writeBoolean(2, canFly);
        this.canFly = canFly;
    }

    @Nullable
    public boolean isCanInstantlyBuild() {
        return canInstantlyBuild;
    }

    @Nullable
    public void setCanInstantlyBuild(boolean canInstantlyBuild) {
        if(legacy) this.packetData.writeBoolean(3, canInstantlyBuild);
        this.canInstantlyBuild = canInstantlyBuild;
    }

    @Nullable
    public float getFlySpeed() {
        return flySpeed;
    }

    @Nullable
    public void setFlySpeed(float flySpeed) {
        if(legacy) this.packetData.writeFloat(0, flySpeed);
        this.flySpeed = flySpeed;
    }

    @Nullable
    public float getWalkSpeed() {
        return walkSpeed;
    }

    @Nullable
    public void setWalkSpeed(float walkSpeed) {
        if(legacy) this.packetData.writeFloat(1, walkSpeed);
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
