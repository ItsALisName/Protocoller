package net.alis.protocoller.samples.effect;

import net.alis.protocoller.samples.util.MathHelper;

public class FactorCalculatingData {
    private int paddingDuration;
    private float factorStart;
    private float factorTarget;
    private float factorCurrent;
    private int effectChangedTimestamp;
    private float factorPreviousFrame;
    private boolean hadEffectLastTick;

    public FactorCalculatingData(int paddingDuration, float factorStart, float factorTarget, float factorCurrent, int effectChangedTimestamp, float factorPreviousFrame, boolean hadEffectLastTick) {
        this.paddingDuration = paddingDuration;
        this.factorStart = factorStart;
        this.factorTarget = factorTarget;
        this.factorCurrent = factorCurrent;
        this.effectChangedTimestamp = effectChangedTimestamp;
        this.factorPreviousFrame = factorPreviousFrame;
        this.hadEffectLastTick = hadEffectLastTick;
    }

    public FactorCalculatingData(int paddingDuration) {
        this(paddingDuration, 0.0F, 1.0F, 0.0F, 0, 0.0F, false);
    }

    public void setValuesFromEffect(MobEffect instance) {
        this.factorPreviousFrame = this.factorCurrent;
        boolean bl = instance.duration > this.paddingDuration;
        if (this.hadEffectLastTick != bl) {
            this.hadEffectLastTick = bl;
            this.effectChangedTimestamp = instance.duration;
            this.factorStart = this.factorCurrent;
            this.factorTarget = bl ? 1.0F : 0.0F;
        }
        float f = MathHelper.clamp(((float)this.effectChangedTimestamp - (float)instance.duration) / (float)this.paddingDuration, 0.0F, 1.0F);
        this.factorCurrent = MathHelper.clamp(f, this.factorStart, this.factorTarget);
    }

    public int getPaddingDuration() {
        return paddingDuration;
    }

    public void setPaddingDuration(int paddingDuration) {
        this.paddingDuration = paddingDuration;
    }

    public float getFactorStart() {
        return factorStart;
    }

    public void setFactorStart(float factorStart) {
        this.factorStart = factorStart;
    }

    public float getFactorTarget() {
        return factorTarget;
    }

    public void setFactorTarget(float factorTarget) {
        this.factorTarget = factorTarget;
    }

    public float getFactorCurrent() {
        return factorCurrent;
    }

    public void setFactorCurrent(float factorCurrent) {
        this.factorCurrent = factorCurrent;
    }

    public int getEffectChangedTimestamp() {
        return effectChangedTimestamp;
    }

    public void setEffectChangedTimestamp(int effectChangedTimestamp) {
        this.effectChangedTimestamp = effectChangedTimestamp;
    }

    public float getFactorPreviousFrame() {
        return factorPreviousFrame;
    }

    public void setFactorPreviousFrame(float factorPreviousFrame) {
        this.factorPreviousFrame = factorPreviousFrame;
    }

    public boolean isHadEffectLastTick() {
        return hadEffectLastTick;
    }

    public void setHadEffectLastTick(boolean hadEffectLastTick) {
        this.hadEffectLastTick = hadEffectLastTick;
    }
}
