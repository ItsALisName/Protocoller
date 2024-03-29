package net.alis.protocoller.samples.phys;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.util.MathHelper;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Vector3D implements ObjectSample {

    public static final Vector3D ZERO = new Vector3D(0.0D, 0.0D, 0.0D);

    public double x;
    public double y;
    public double z;

    public Vector3D(double x, double y, double z) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        if (x == -0.0D) x = 0.0D;
        if (y == -0.0D) y = 0.0D;
        if (z == -0.0D) z = 0.0D;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Object originalVec3D) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject accessor = new AccessedObject(originalVec3D);
        this.x = accessor.readField(0, double.class);
        this.y = accessor.readField(1, double.class);
        this.z = accessor.readField(2, double.class);
    }

    public Vector3D subtractReverse(Vector3D vec) {
        return new Vector3D(vec.x - this.x, vec.y - this.y, vec.z - this.z);
    }

    public Vector3D normalize() {
        double d0 = (double) MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d0 < 1.0E-4D ? ZERO : new Vector3D(this.x / d0, this.y / d0, this.z / d0);
    }

    public double dotProduct(Vector3D vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }

    public Vector3D crossProduct(Vector3D vec) {
        return new Vector3D(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    public Vector3D subtract(@NotNull Vector3D vec) {
        return this.subtract(vec.x, vec.y, vec.z);
    }

    public Vector3D subtract(double x, double y, double z) {
        return this.addVector(-x, -y, -z);
    }

    public Vector3D add(@NotNull Vector3D vec) {
        return this.addVector(vec.x, vec.y, vec.z);
    }
    
    public Vector3D addVector(double x, double y, double z) {
        return new Vector3D(this.x + x, this.y + y, this.z + z);
    }
    
    public double distanceTo(@NotNull Vector3D vec) {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        return (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }
    
    public double squareDistanceTo(@NotNull Vector3D vec) {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double squareDistanceTo(double xIn, double yIn, double zIn) {
        double d0 = xIn - this.x;
        double d1 = yIn - this.y;
        double d2 = zIn - this.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public Vector3D scale(double p_186678_1_) {
        return new Vector3D(this.x * p_186678_1_, this.y * p_186678_1_, this.z * p_186678_1_);
    }
    
    public double lengthVector() {
        return (double)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    @Nullable
    public Vector3D getIntermediateWithXValue(@NotNull Vector3D vec, double x) {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        if (d0 * d0 < 1.0000000116860974E-7D) {
            return null;
        } else {
            double d3 = (x - this.x) / d0;
            return d3 >= 0.0D && d3 <= 1.0D ? new Vector3D(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3) : null;
        }
    }

    @Nullable
    public Vector3D getIntermediateWithYValue(@NotNull Vector3D vec, double y) {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        if (d1 * d1 < 1.0000000116860974E-7D) {
            return null;
        } else {
            double d3 = (y - this.y) / d1;
            return d3 >= 0.0D && d3 <= 1.0D ? new Vector3D(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3) : null;
        }
    }

    @Nullable
    public Vector3D getIntermediateWithZValue(@NotNull Vector3D vec, double z) {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        if (d2 * d2 < 1.0000000116860974E-7D) {
            return null;
        } else {
            double d3 = (z - this.z) / d2;
            return d3 >= 0.0D && d3 <= 1.0D ? new Vector3D(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3) : null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Vector3D)) {
            return false;
        } else {
            Vector3D Vector3D = (Vector3D)obj;
            return Double.compare(Vector3D.x, this.x) == 0 && (Double.compare(Vector3D.y, this.y) == 0 && Double.compare(Vector3D.z, this.z) == 0);
        }
    }

    public int hashCode() {
        long j = Double.doubleToLongBits(this.x);
        int i = (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.y);
        i = 31 * i + (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.z);
        i = 31 * i + (int)(j ^ j >>> 32);
        return i;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public Vector3D rotatePitch(float pitch) {
        float f = MathHelper.cos(pitch);
        float f1 = MathHelper.sin(pitch);
        double d0 = this.x;
        double d1 = this.y * (double)f + this.z * (double)f1;
        double d2 = this.z * (double)f - this.y * (double)f1;
        return new Vector3D(d0, d1, d2);
    }

    public Vector3D rotateYaw(float yaw) {
        float f = MathHelper.cos(yaw);
        float f1 = MathHelper.sin(yaw);
        double d0 = this.x * (double)f + this.z * (double)f1;
        double d1 = this.y;
        double d2 = this.z * (double)f - this.x * (double)f1;
        return new Vector3D(d0, d1, d2);
    }
    
    @Contract("_ -> new")
    public static @NotNull Vector3D fromPitchYawVector(@NotNull Vector2F vec) {
        return fromPitchYaw(vec.x, vec.y);
    }
    
    @Contract("_, _ -> new")
    public static @NotNull Vector3D fromPitchYaw(float fl1, float fl2) {
        float f = MathHelper.cos(-fl2 * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-fl2 * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-fl1 * 0.017453292F);
        float f3 = MathHelper.sin(-fl1 * 0.017453292F);
        return new Vector3D((f1 * f2), f3, (f * f2));
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(Reflect.getConstructor(clazz(), false, double.class, double.class, double.class), this.x, this.y, this.z);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getVector3dClass();
    }
}
