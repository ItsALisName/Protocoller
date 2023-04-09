package net.alis.protocoller.samples.phys;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.util.MathHelper;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.concurrent.Immutable;

@Immutable
public class BaseBlockPosition implements Comparable<BaseBlockPosition>, ObjectSample {

    public static final BaseBlockPosition NULL_VECTOR = new BaseBlockPosition(0, 0, 0);
    private int x;
    private int y;
    private int z;

    public BaseBlockPosition(int xIn, int yIn, int zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public BaseBlockPosition(Object blockPosition, boolean trueLink) {
        if (!trueLink) {
            this.x = Reflect.readSuperclassField(blockPosition, 0, int.class, false);
            this.y = Reflect.readSuperclassField(blockPosition, 1, int.class, false);
            this.z = Reflect.readSuperclassField(blockPosition, 2, int.class, false);
        } else {
            AccessedObject accessor = new AccessedObject(blockPosition);
            this.x = accessor.read(0, int.class);
            this.y = accessor.read(1, int.class);
            this.z = accessor.read(2, int.class);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public BaseBlockPosition(double xIn, double yIn, double zIn) {
        this(MathHelper.floor(xIn), MathHelper.floor(yIn), MathHelper.floor(zIn));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof BaseBlockPosition)) {
            return false;
        } else {
            BaseBlockPosition Vector3I = (BaseBlockPosition)obj;
            return this.getX() == Vector3I.getX() && (this.getY() == Vector3I.getY() && this.getZ() == Vector3I.getZ());
        }
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int compareTo(BaseBlockPosition vector3I) {
        return this.getY() == vector3I.getY() ? (this.getZ() == vector3I.getZ() ? this.getX() - vector3I.getX() : this.getZ() - vector3I.getZ()) : this.getY() - vector3I.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public BaseBlockPosition crossProduct(BaseBlockPosition vec) {
        return new BaseBlockPosition(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    public double getDistance(int xIn, int yIn, int zIn) {
        double d0 = (double)(this.getX() - xIn);
        double d1 = (double)(this.getY() - yIn);
        double d2 = (double)(this.getZ() - zIn);
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double distanceSq(double toX, double toY, double toZ) {
        double d0 = (double)this.getX() - toX;
        double d1 = (double)this.getY() - toY;
        double d2 = (double)this.getZ() - toZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceSqToCenter(double xIn, double yIn, double zIn) {
        double d0 = (double)this.getX() + 0.5D - xIn;
        double d1 = (double)this.getY() + 0.5D - yIn;
        double d2 = (double)this.getZ() + 0.5D - zIn;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceSq(BaseBlockPosition to) {
        return this.distanceSq((double)to.getX(), (double)to.getY(), (double)to.getZ());
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getBaseBlockPositionClass(), int.class, int.class, int.class),
                this.x, this.y, this.z
        );
    }

    @Override
    public String toString() {
        return "BaseBlockPosition{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}

