package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.entity.player.PlayerAbilities;
import net.alis.protocoller.parent.nbt.NBTTagCompound;

public class PacketPlayOutAbilities implements Packet {

    private final PacketDataContainer packetData;
    private boolean invulnerable;
    private boolean flying;
    private boolean canFly;
    private boolean canInstantlyBuild;
    private float flySpeed;
    private float walkSpeed;

    public PacketPlayOutAbilities(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.invulnerable = packetData.readBoolean(0);
        this.flying = packetData.readBoolean(1);
        this.canFly = packetData.readBoolean(2);
        this.canInstantlyBuild = packetData.readBoolean(3);
        this.flySpeed = packetData.readFloat(0);
        this.walkSpeed = packetData.readFloat(1);
    }

    public PacketPlayOutAbilities(boolean invulnerable, boolean flying, boolean canFly, boolean canInstantlyBuild, float flySpeed, float walkSpeed) {
        PlayerAbilities abilities = new PlayerAbilities(invulnerable, flying, canFly, canInstantlyBuild, true, flySpeed, walkSpeed);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, abilities.createOriginal()));
        this.invulnerable = invulnerable;
        this.flying = flying;
        this.canFly = canFly;
        this.canInstantlyBuild = canInstantlyBuild;
        this.flySpeed = flySpeed;
        this.walkSpeed = walkSpeed;
    }

    public PacketPlayOutAbilities(PlayerAbilities abilities) {
        this(abilities.isInvulnerable(), abilities.isFlying(), abilities.isCanFly(), abilities.isCanInstantlyBuild(), abilities.getFlySpeed(), abilities.getFlySpeed());
    }

    public PacketPlayOutAbilities(NBTTagCompound compound) {
        if(compound.getCompoundTag("abilities") != null) {
            NBTTagCompound tag = compound.getCompoundTag("abilities");
            this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, new PlayerAbilities().fromNBT(compound).createOriginal()));
            this.invulnerable = tag.getBoolean("invulnerable");
            this.flying = tag.getBoolean("flying");
            this.canFly = tag.getBoolean("mayfly");
            this.canInstantlyBuild = tag.getBoolean("instabuild");
            this.flySpeed = tag.getFloat("flySpeed");
            this.walkSpeed = tag.getTagId("walkSpeed");
            return;
        }
        LogsManager.get().getLogger().warn("Failed to create PlayerAbilities from nbt, key \"abilities\" not founded!");
        this.packetData = null;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.packetData.writeBoolean(0, invulnerable);
        this.invulnerable = invulnerable;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.packetData.writeBoolean(1, flying);
        this.flying = flying;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.packetData.writeBoolean(2, canFly);
        this.canFly = canFly;
    }

    public boolean isCanInstantlyBuild() {
        return canInstantlyBuild;
    }

    public void setCanInstantlyBuild(boolean canInstantlyBuild) {
        this.packetData.writeBoolean(3, canInstantlyBuild);
        this.canInstantlyBuild = canInstantlyBuild;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        this.packetData.writeFloat(0, flySpeed);
        this.flySpeed = flySpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.packetData.writeFloat(1, walkSpeed);
        this.walkSpeed = walkSpeed;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.ABILITIES;
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