package net.alis.protocoller.parent.phys;

import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.parent.util.MathHelper;

import javax.annotation.concurrent.Immutable;

@Immutable
public class BaseBlockPosition implements Comparable<BaseBlockPosition> {

    public static final BaseBlockPosition NULL_VECTOR = new BaseBlockPosition(0, 0, 0);
    private int x;
    private int y;
    private int z;

    public BaseBlockPosition(int xIn, int yIn, int zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public BaseBlockPosition(Object blockPosition) {
        this.x = Reflection.readSuperclassField(blockPosition, 0, int.class);
        this.y = Reflection.readSuperclassField(blockPosition, 1, int.class);
        this.z = Reflection.readSuperclassField(blockPosition, 2, int.class);
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
    public String toString() {
        return "BaseBlockPosition{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}

