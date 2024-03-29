package net.alis.protocoller.samples.entity.player;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.nbt.NBTTagCompound;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.NotNull;

public class PlayerAbilities implements ObjectSample {

    private boolean isInvulnerable;
    private boolean isFlying;
    private boolean canFly;
    private boolean canInstantlyBuild;
    private boolean mayBuild = true;
    private float flySpeed = 0.05F;
    private float walkSpeed = 0.1F;

    public PlayerAbilities() { }

    public PlayerAbilities(Object abilities) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject accessor = new AccessedObject(abilities);
        this.isInvulnerable = accessor.readField(0, boolean.class);
        this.isFlying = accessor.readField(1, boolean.class);
        this.canFly = accessor.readField(2, boolean.class);
        this.canInstantlyBuild = accessor.readField(3, boolean.class);
        this.mayBuild = accessor.readField(4, boolean.class);
        this.flySpeed = accessor.readField(0, Float.TYPE);
        this.walkSpeed = accessor.readField(1, Float.TYPE);
    }

    public PlayerAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantlyBuild, boolean mayBuild, float flySpeed, float walkSpeed) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.isInvulnerable = isInvulnerable;
        this.isFlying = isFlying;
        this.canFly = canFly;
        this.canInstantlyBuild = canInstantlyBuild;
        this.mayBuild = mayBuild;
        this.flySpeed = flySpeed;
        this.walkSpeed = walkSpeed;
    }

    public boolean isInvulnerable() {
        return isInvulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.isInvulnerable = invulnerable;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        this.isFlying = flying;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public boolean isCanInstantlyBuild() {
        return canInstantlyBuild;
    }

    public void setCanInstantlyBuild(boolean canInstantlyBuild) {
        this.canInstantlyBuild = canInstantlyBuild;
    }

    public boolean isMayBuild() {
        return mayBuild;
    }

    public void setMayBuild(boolean mayBuild) {
        this.mayBuild = mayBuild;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public NBTTagCompound getNBT(@NotNull NBTTagCompound compound) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setBoolean("invulnerable", this.isInvulnerable);
        tagCompound.setBoolean("flying", this.isFlying);
        tagCompound.setBoolean("instabuild", this.canInstantlyBuild);
        tagCompound.setBoolean("mayBuild", this.mayBuild);
        tagCompound.setFloat("flySpeed", this.flySpeed);
        tagCompound.setFloat("walkSpeed", this.walkSpeed);
        compound.setTag("abilities", tagCompound);
        return compound;
    }

    public PlayerAbilities fromNBT(@NotNull NBTTagCompound nbt) {
        if (nbt.getCompoundTag("abilities") != null) {
            NBTTagCompound compoundTag = nbt.getCompoundTag("abilities");
            this.isInvulnerable = compoundTag.getBoolean("invulnerable");
            this.isFlying = compoundTag.getBoolean("flying");
            this.canFly = compoundTag.getBoolean("mayfly");
            this.canInstantlyBuild = compoundTag.getBoolean("instabuild");
            this.flySpeed = compoundTag.getFloat("flySpeed");
            this.walkSpeed = compoundTag.getFloat("walkSpeed");
            this.mayBuild = compoundTag.getBoolean("mayBuild");
            return this;
        }
        LogsManager.get().getLogger().warn("Failed to create PlayerAbilities from nbt, key \"abilities\" not founded!");
        return this;
    }

    @Override
    public Object createOriginal() {
        IndexedParam<?,?>[] params = {
                new IndexedParam<>(this.isInvulnerable, 0),
                new IndexedParam<>(this.isFlying, 1),
                new IndexedParam<>(this.canFly, 2),
                new IndexedParam<>(this.canInstantlyBuild, 3),
                new IndexedParam<>(this.mayBuild, 4),
                new IndexedParam<>(this.flySpeed, 0),
                new IndexedParam<>(this.walkSpeed, 1)
        };
        return Reflect.callEmptyConstructor(clazz(), params);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getPlayerAbilitiesClass();
    }
}
