package net.alis.protocoller.samples.phys;

import com.google.common.base.MoreObjects;
import net.alis.protocoller.samples.core.Position;
import net.alis.protocoller.samples.util.Axis;
import net.alis.protocoller.samples.util.Direction;
import net.alis.protocoller.samples.util.MathHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.Immutable;

@Immutable
public class Vector3I implements Comparable<Vector3I> {
    public static final Vector3I ZERO = new Vector3I(0, 0, 0);
    private int x;
    private int y;
    private int z;

    public Vector3I(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Vector3I)) {
            return false;
        } else {
            Vector3I Vector3I = (Vector3I)obj;
            if (this.getX() != Vector3I.getX()) {
                return false;
            } else if (this.getY() != Vector3I.getY()) {
                return false;
            } else {
                return this.getZ() == Vector3I.getZ();
            }
        }
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int compareTo(@NotNull Vector3I vector3I) {
        if (this.getY() == vector3I.getY()) {
            return this.getZ() == vector3I.getZ() ? this.getX() - vector3I.getX() : this.getZ() - vector3I.getZ();
        } else {
            return this.getY() - vector3I.getY();
        }
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

    protected Vector3I setX(int x) {
        this.x = x;
        return this;
    }

    protected Vector3I setY(int y) {
        this.y = y;
        return this;
    }

    protected Vector3I setZ(int z) {
        this.z = z;
        return this;
    }

    public Vector3I offset(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new Vector3I(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    public Vector3I offset(@NotNull Vector3I vector3I) {
        return this.offset(vector3I.getX(), vector3I.getY(), vector3I.getZ());
    }

    public Vector3I subtract(@NotNull Vector3I vector3I) {
        return this.offset(-vector3I.getX(), -vector3I.getY(), -vector3I.getZ());
    }

    public Vector3I multiply(int i) {
        if (i == 1) {
            return this;
        } else {
            return i == 0 ? ZERO : new Vector3I(this.getX() * i, this.getY() * i, this.getZ() * i);
        }
    }

    public Vector3I above() {
        return this.above(1);
    }

    public Vector3I above(int i) {
        return this.relative(Direction.UP, i);
    }

    public Vector3I below() {
        return this.below(1);
    }

    public Vector3I below(int i) {
        return this.relative(Direction.DOWN, i);
    }

    public Vector3I north() {
        return this.north(1);
    }

    public Vector3I north(int i) {
        return this.relative(Direction.NORTH, i);
    }

    public Vector3I south() {
        return this.south(1);
    }

    public Vector3I south(int i) {
        return this.relative(Direction.SOUTH, i);
    }

    public Vector3I west() {
        return this.west(1);
    }

    public Vector3I west(int i) {
        return this.relative(Direction.WEST, i);
    }

    public Vector3I east() {
        return this.east(1);
    }

    public Vector3I east(int i) {
        return this.relative(Direction.EAST, i);
    }

    public Vector3I relative(Direction direction) {
        return this.relative(direction, 1);
    }

    public Vector3I relative(Direction direction, int i) {
        return i == 0 ? this : new Vector3I(this.getX() + direction.getStepX() * i, this.getY() + direction.getStepY() * i, this.getZ() + direction.getStepZ() * i);
    }

    public Vector3I relative(Axis axis, int i1) {
        if (i1 == 0) {
            return this;
        } else {
            int i = axis == Axis.X ? i1 : 0;
            int j = axis == Axis.Y ? i1 : 0;
            int k = axis == Axis.Z ? i1 : 0;
            return new Vector3I(this.getX() + i, this.getY() + j, this.getZ() + k);
        }
    }

    public Vector3I cross(@NotNull Vector3I vector3I) {
        return new Vector3I(this.getY() * vector3I.getZ() - this.getZ() * vector3I.getY(), this.getZ() * vector3I.getX() - this.getX() * vector3I.getZ(), this.getX() * vector3I.getY() - this.getY() * vector3I.getX());
    }

    public boolean closerThan(Vector3I vector3I, double d) {
        return this.distSqr(vector3I) < MathHelper.square(d);
    }

    public boolean closerToCenterThan(Position position, double d) {
        return this.distToCenterSqr(position) < MathHelper.square(d);
    }

    public double distSqr(@NotNull Vector3I vector3I) {
        return this.distToLowCornerSqr(vector3I.getX(), vector3I.getY(), vector3I.getZ());
    }

    public double distToCenterSqr(@NotNull Position position) {
        return this.distToCenterSqr(position.x(), position.y(), position.z());
    }

    public double distToCenterSqr(double x, double y, double z) {
        double d0 = (double)this.getX() + 0.5D - x;
        double d1 = (double)this.getY() + 0.5D - y;
        double d2 = (double)this.getZ() + 0.5D - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distToLowCornerSqr(double x, double y, double z) {
        double d0 = (double)this.getX() - x;
        double d1 = (double)this.getY() - y;
        double d2 = (double)this.getZ() - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public int distManhattan(@NotNull Vector3I vector3I) {
        float f = (float)Math.abs(vector3I.getX() - this.getX());
        float f1 = (float)Math.abs(vector3I.getY() - this.getY());
        float f2 = (float)Math.abs(vector3I.getZ() - this.getZ());
        return (int)(f + f1 + f2);
    }

    public int get(@NotNull Axis axis) {
        return axis.choose(this.x, this.y, this.z);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }

    public String toShortString() {
        return this.getX() + ", " + this.getY() + ", " + this.getZ();
    }
}
