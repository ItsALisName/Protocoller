package net.alis.protocoller.samples.effect;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.nbt.NBTTagCompound;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MobEffect implements ObjectSample {
    protected final MobEffectList type;
    protected int duration;
    protected int amplifier;
    protected boolean ambient;
    protected boolean showParticles;
    protected boolean showIcon;
    @Nullable
    protected MobEffect hiddenEffect;

    public MobEffect(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        this.type = new MobEffectList(accessor.read(0, ClassesContainer.get().getMobEffectListClass()));
        this.duration = accessor.read(0, int.class);
        this.ambient = accessor.read(1, int.class);
        this.ambient = accessor.read(0, boolean.class);
        this.showParticles = accessor.read(1, boolean.class);
        this.showIcon = accessor.read(2, boolean.class);
        if(accessor.read(0, ClassesContainer.get().getMobEffectClass()) != null) {
            this.hiddenEffect = new MobEffect((Object) accessor.read(0, ClassesContainer.get().getMobEffectClass()));
        } else {
            this.hiddenEffect = null;
        }
    }

    public MobEffect(MobEffectList type) {
        this(type, 0, 0);
    }

    public MobEffect(MobEffectList type, int duration) {
        this(type, duration, 0);
    }

    public MobEffect(MobEffectList type, int duration, int amplifier) {
        this(type, duration, amplifier, false, true);
    }

    public MobEffect(MobEffectList type, int duration, int amplifier, boolean ambient, boolean visible) {
        this(type, duration, amplifier, ambient, visible, visible);
    }

    public MobEffect(MobEffectList type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
        this(type, duration, amplifier, ambient, showParticles, showIcon,null);
    }

    public MobEffect(MobEffectList type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, @Nullable MobEffect hiddenEffect) {
        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
        this.hiddenEffect = hiddenEffect;
    }

    public MobEffect(MobEffect instance) {
        this.type = instance.type;
        this.fromEffect(instance);
    }

    public void fromEffect(MobEffect that) {
        this.duration = that.duration;
        this.amplifier = that.amplifier;
        this.ambient = that.ambient;
        this.showParticles = that.showParticles;
        this.showIcon = that.showIcon;
    }

    public boolean compare(@NotNull MobEffect that) {
        if (this.type != that.type) {
            LogsManager.get().getLogger().warn("This method should only be called for matching effects!");
        }
        int dur = this.duration;
        boolean bool = false;
        if (that.amplifier > this.amplifier) {
            if (that.duration < this.duration) {
                MobEffect mobEffectInstance = this.hiddenEffect;
                this.hiddenEffect = new MobEffect(this);
                this.hiddenEffect.hiddenEffect = mobEffectInstance;
            }
            this.amplifier = that.amplifier;
            this.duration = that.duration;
            bool = true;
        } else if (that.duration > this.duration) {
            if (that.amplifier == this.amplifier) {
                this.duration = that.duration;
                bool = true;
            } else if (this.hiddenEffect == null) {
                this.hiddenEffect = new MobEffect(that);
            } else {
                this.hiddenEffect.compare(that);
            }
        }
        if (!that.ambient && this.ambient || bool) {
            this.ambient = that.ambient;
            bool = true;
        }
        if (that.showParticles != this.showParticles) {
            this.showParticles = that.showParticles;
            bool = true;
        }
        if (that.showIcon != this.showIcon) {
            this.showIcon = that.showIcon;
            bool = true;
        }
        return bool;
    }

    public MobEffectList getType() {
        return this.type;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public boolean isAmbient() {
        return this.ambient;
    }

    public boolean showParticles() {
        return this.showParticles;
    }

    public boolean showIcon() {
        return this.showIcon;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof MobEffect)) {
            return false;
        } else {
            MobEffect mobEffectInstance = (MobEffect)object;
            return this.duration == mobEffectInstance.duration && this.amplifier == mobEffectInstance.amplifier && this.ambient == mobEffectInstance.ambient && this.type.equals(mobEffectInstance.type);
        }
    }

    public int hashCode() {
        int i = this.type.hashCode();
        i = 31 * i + this.duration;
        i = 31 * i + this.amplifier;
        return 31 * i + (this.ambient ? 1 : 0);
    }

    private void fillNBT(@NotNull NBTTagCompound nbt) {
        nbt.setInteger("Amplifier", (byte)this.getAmplifier());
        nbt.setInteger("Duration", this.getDuration());
        nbt.setBoolean("Ambient", this.isAmbient());
        nbt.setBoolean("ShowParticles", this.showParticles());
        nbt.setBoolean("ShowIcon", this.showIcon());
        if (this.hiddenEffect != null) {
            NBTTagCompound compoundTag = new NBTTagCompound();
            this.hiddenEffect.fillNBT(compoundTag);
            nbt.setTag("HiddenEffect", compoundTag);
        }
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassesContainer.get().getMobEffectClass(), ClassesContainer.get().getMobEffectListClass(), int.class, int.class, boolean.class, boolean.class, boolean.class),
                this.type.createOriginal(), this.duration, this.amplifier, this.ambient, this.showParticles, this.showIcon
        );
    }
}
