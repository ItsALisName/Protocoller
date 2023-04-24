package net.alis.protocoller.samples.phys;

import com.google.common.annotations.VisibleForTesting;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.util.Direction;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;

public class AxisAligned implements ObjectSample {
    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public AxisAligned(double x1, double y1, double z1, double x2, double y2, double z2) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public AxisAligned(Object original) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject accessor = new AccessedObject(original);
        this.minX = accessor.readField(0, double.class);
        this.minY = accessor.readField(1, double.class);
        this.minZ = accessor.readField(2, double.class);
        this.maxX = accessor.readField(3, double.class);
        this.maxY = accessor.readField(4, double.class);
        this.maxZ = accessor.readField(5, double.class);
    }

    public AxisAligned(BlockPosition pos) {
        this((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1));
    }

    public AxisAligned(BlockPosition pos1, BlockPosition pos2) {
        this((double)pos1.getX(), (double)pos1.getY(), (double)pos1.getZ(), (double)pos2.getX(), (double)pos2.getY(), (double)pos2.getZ());
    }

    public AxisAligned(Vector3D min, Vector3D max) {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public AxisAligned setMaxY(double y2) {
        return new AxisAligned(this.minX, this.minY, this.minZ, this.maxX, y2, this.maxZ);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof AxisAligned)) {
            return false;
        } else {
            AxisAligned axisalignedbb = (AxisAligned)obj;
            if (Double.compare(axisalignedbb.minX, this.minX) != 0) {
                return false;
            } else if (Double.compare(axisalignedbb.minY, this.minY) != 0) {
                return false;
            } else if (Double.compare(axisalignedbb.minZ, this.minZ) != 0) {
                return false;
            } else if (Double.compare(axisalignedbb.maxX, this.maxX) != 0) {
                return false;
            } else if (Double.compare(axisalignedbb.maxY, this.maxY) != 0) {
                return false;
            } else {
                return Double.compare(axisalignedbb.maxZ, this.maxZ) == 0;
            }
        }
    }

    public int hashCode() {
        long i = Double.doubleToLongBits(this.minX);
        int j = (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.minY);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.minZ);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxX);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxY);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxZ);
        j = 31 * j + (int)(i ^ i >>> 32);
        return j;
    }

    public AxisAligned opposite(double doub1, double doub2, double doub3) {
        double d0 = this.minX;
        double d1 = this.minY;
        double d2 = this.minZ;
        double d3 = this.maxX;
        double d4 = this.maxY;
        double d5 = this.maxZ;
        if (doub1 < 0.0D) {
            d0 -= doub1;
        } else if (doub1 > 0.0D) {
            d3 -= doub1;
        }
        if (doub2 < 0.0D) {
            d1 -= doub2;
        } else if (doub2 > 0.0D) {
            d4 -= doub2;
        }

        if (doub3 < 0.0D) {
            d2 -= doub3;
        } else if (doub3 > 0.0D) {
            d5 -= doub3;
        }
        return new AxisAligned(d0, d1, d2, d3, d4, d5);
    }


    public AxisAligned addCord(double x, double y, double z) {
        double d0 = this.minX;
        double d1 = this.minY;
        double d2 = this.minZ;
        double d3 = this.maxX;
        double d4 = this.maxY;
        double d5 = this.maxZ;
        if (x < 0.0D) {
            d0 += x;
        } else if (x > 0.0D) {
            d3 += x;
        }
        if (y < 0.0D) {
            d1 += y;
        } else if (y > 0.0D) {
            d4 += y;
        }
        if (z < 0.0D) {
            d2 += z;
        } else if (z > 0.0D) {
            d5 += z;
        }
        return new AxisAligned(d0, d1, d2, d3, d4, d5);
    }

    public AxisAligned expand(double x, double y, double z) {
        double d0 = this.minX - x;
        double d1 = this.minY - y;
        double d2 = this.minZ - z;
        double d3 = this.maxX + x;
        double d4 = this.maxY + y;
        double d5 = this.maxZ + z;
        return new AxisAligned(d0, d1, d2, d3, d4, d5);
    }

    public AxisAligned expandXyz(double value) {
        return this.expand(value, value, value);
    }

    public AxisAligned union(AxisAligned other) {
        double d0 = Math.min(this.minX, other.minX);
        double d1 = Math.min(this.minY, other.minY);
        double d2 = Math.min(this.minZ, other.minZ);
        double d3 = Math.max(this.maxX, other.maxX);
        double d4 = Math.max(this.maxY, other.maxY);
        double d5 = Math.max(this.maxZ, other.maxZ);
        return new AxisAligned(d0, d1, d2, d3, d4, d5);
    }

    public AxisAligned offset(double x, double y, double z) {
        return new AxisAligned(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    public AxisAligned offset(BlockPosition pos) {
        return new AxisAligned(this.minX + (double)pos.getX(), this.minY + (double)pos.getY(), this.minZ + (double)pos.getZ(), this.maxX + (double)pos.getX(), this.maxY + (double)pos.getY(), this.maxZ + (double)pos.getZ());
    }

    public AxisAligned offset(Vector3D vec) {
        return this.offset(vec.x, vec.y, vec.z);
    }

    public double calculateXOffset(AxisAligned other, double offsetX) {
        if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
            if (offsetX > 0.0D && other.maxX <= this.minX) {
                double d1 = this.minX - other.maxX;
                if (d1 < offsetX) {
                    offsetX = d1;
                }
            } else if (offsetX < 0.0D && other.minX >= this.maxX) {
                double d0 = this.maxX - other.minX;
                if (d0 > offsetX) {
                    offsetX = d0;
                }
            }
        }
        return offsetX;
    }
    public double calculateYOffset(AxisAligned other, double offsetY) {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
            if (offsetY > 0.0D && other.maxY <= this.minY) {
                double d1 = this.minY - other.maxY;
                if (d1 < offsetY) {
                    offsetY = d1;
                }
            } else if (offsetY < 0.0D && other.minY >= this.maxY) {
                double d0 = this.maxY - other.minY;
                if (d0 > offsetY) {
                    offsetY = d0;
                }
            }
        }
        return offsetY;
    }

    public double calculateZOffset(AxisAligned other, double offsetZ) {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
            if (offsetZ > 0.0D && other.maxZ <= this.minZ) {
                double d1 = this.minZ - other.maxZ;
                if (d1 < offsetZ) {
                    offsetZ = d1;
                }
            } else if (offsetZ < 0.0D && other.minZ >= this.maxZ) {
                double d0 = this.maxZ - other.minZ;
                if (d0 > offsetZ) {
                    offsetZ = d0;
                }
            }
        }
        return offsetZ;
    }

    public boolean intersectsWith(AxisAligned other) {
        return this.intersects(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
    }

    public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
        return this.minX < x2 && this.maxX > x1 && this.minY < y2 && this.maxY > y1 && this.minZ < z2 && this.maxZ > z1;
    }

    public boolean intersects(Vector3D min, Vector3D max) {
        return this.intersects(Math.min(min.x, max.x), Math.min(min.y, max.y), Math.min(min.z, max.z), Math.max(min.x, max.x), Math.max(min.y, max.y), Math.max(min.z, max.z));
    }


    public boolean isVecInside(Vector3D vec) {
        if (vec.x > this.minX && vec.x < this.maxX) {
            if (vec.y > this.minY && vec.y < this.maxY) {
                return vec.z > this.minZ && vec.z < this.maxZ;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public double getAverageEdgeLength() {
        double d0 = this.maxX - this.minX;
        double d1 = this.maxY - this.minY;
        double d2 = this.maxZ - this.minZ;
        return (d0 + d1 + d2) / 3.0D;
    }

    public AxisAligned contract(double value) {
        return this.expandXyz(-value);
    }

    @Nullable
    public RayTraceResult calculateIntercept(Vector3D vecA, Vector3D vecB) {
        Vector3D Vector3D = this.collideWithXPlane(this.minX, vecA, vecB);
        Direction direction = Direction.WEST;
        Vector3D vector3D = this.collideWithXPlane(this.maxX, vecA, vecB);
        if (vector3D != null && this.isClosest(vecA, Vector3D, vector3D)) {
            Vector3D = vector3D;
            direction = Direction.EAST;
        }
        vector3D = this.collideWithYPlane(this.minY, vecA, vecB);
        if (vector3D != null && this.isClosest(vecA, Vector3D, vector3D)) {
            Vector3D = vector3D;
            direction = Direction.DOWN;
        }
        vector3D = this.collideWithYPlane(this.maxY, vecA, vecB);
        if (vector3D != null && this.isClosest(vecA, Vector3D, vector3D)) {
            Vector3D = vector3D;
            direction = Direction.UP;
        }
        vector3D = this.collideWithZPlane(this.minZ, vecA, vecB);
        if (vector3D != null && this.isClosest(vecA, Vector3D, vector3D)) {
            Vector3D = vector3D;
            direction = Direction.NORTH;
        }
        vector3D = this.collideWithZPlane(this.maxZ, vecA, vecB);
        if (vector3D != null && this.isClosest(vecA, Vector3D, vector3D)) {
            Vector3D = vector3D;
            direction = Direction.SOUTH;
        }
        return Vector3D == null ? null : new RayTraceResult(vector3D, direction);
    }

    @VisibleForTesting
    boolean isClosest(Vector3D vec1, @Nullable Vector3D vec2, Vector3D vec3) {
        return vec2 == null || vec1.squareDistanceTo(vec3) < vec1.squareDistanceTo(vec2);
    }

    @Nullable
    @VisibleForTesting
    Vector3D collideWithXPlane(double doub1, Vector3D doub2, Vector3D doub3) {
        Vector3D vector3D = doub2.getIntermediateWithXValue(doub3, doub1);
        return vector3D != null && this.intersectsWithYZ(vector3D) ? vector3D : null;
    }

    @Nullable
    @VisibleForTesting
    Vector3D collideWithYPlane(double doub1, Vector3D vec1, Vector3D vec2) {
        Vector3D Vector3D = vec1.getIntermediateWithYValue(vec2, doub1);
        return Vector3D != null && this.intersectsWithXZ(Vector3D) ? Vector3D : null;
    }

    @Nullable
    @VisibleForTesting
    Vector3D collideWithZPlane(double doub1, Vector3D vec1, Vector3D vec2) {
        Vector3D Vector3D = vec1.getIntermediateWithZValue(vec2, doub1);
        return Vector3D != null && this.intersectsWithXY(Vector3D) ? Vector3D : null;
    }

    @VisibleForTesting
    public boolean intersectsWithYZ(Vector3D vec) {
        return vec.y >= this.minY && vec.y <= this.maxY && vec.z >= this.minZ && vec.z <= this.maxZ;
    }

    @VisibleForTesting
    public boolean intersectsWithXZ(Vector3D vec) {
        return vec.x >= this.minX && vec.x <= this.maxX && vec.z >= this.minZ && vec.z <= this.maxZ;
    }

    @VisibleForTesting
    public boolean intersectsWithXY(Vector3D vec) {
        return vec.x >= this.minX && vec.x <= this.maxX && vec.y >= this.minY && vec.y <= this.maxY;
    }

    public String toString() {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }

    public boolean hasNaN() {
        return Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX) || Double.isNaN(this.maxY) || Double.isNaN(this.maxZ);
    }

    public Vector3D getCenter() {
        return new Vector3D(this.minX + (this.maxX - this.minX) * 0.5D, this.minY + (this.maxY - this.minY) * 0.5D, this.minZ + (this.maxZ - this.minZ) * 0.5D);
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, double.class, double.class, double.class, double.class, double.class, double.class),
                this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getAxisAlignedClass();
    }
}
