package net.alis.protocoller.samples.paper;

import net.alis.protocoller.samples.phys.AxisAligned;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class CollisionUtil {

    public static boolean isEmpty(AxisAligned aabb) {
        return aabb.maxX - aabb.minX < 1.0E-7 && aabb.maxY - aabb.minY < 1.0E-7 && aabb.maxZ - aabb.minZ < 1.0E-7;
    }

    public static boolean isEmpty(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return maxX - minX < 1.0E-7 && maxY - minY < 1.0E-7 && maxZ - minZ < 1.0E-7;
    }

    @Contract("_, _ -> new")
    public static @NotNull AxisAligned getBoxForChunk(int chunkX, int chunkZ) {
        double x = (double)(chunkX << 4);
        double z = (double)(chunkZ << 4);
        return new AxisAligned(x - 3.0E-7, Double.NEGATIVE_INFINITY, z - 3.0E-7, x + 16.0000003, Double.POSITIVE_INFINITY, z + 16.0000003);
    }

    public static boolean voxelShapeIntersect(double minX1, double minY1, double minZ1, double maxX1, double maxY1, double maxZ1, double minX2, double minY2, double minZ2, double maxX2, double maxY2, double maxZ2) {
        return minX1 - maxX2 < -1.0E-7 && maxX1 - minX2 > 1.0E-7 && minY1 - maxY2 < -1.0E-7 && maxY1 - minY2 > 1.0E-7 && minZ1 - maxZ2 < -1.0E-7 && maxZ1 - minZ2 > 1.0E-7;
    }

    @Contract(pure = true)
    public static boolean voxelShapeIntersect(@NotNull AxisAligned box, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return box.minX - maxX < -1.0E-7 && box.maxX - minX > 1.0E-7 && box.minY - maxY < -1.0E-7 && box.maxY - minY > 1.0E-7 && box.minZ - maxZ < -1.0E-7 && box.maxZ - minZ > 1.0E-7;
    }

    @Contract(pure = true)
    public static boolean voxelShapeIntersect(@NotNull AxisAligned box1, @NotNull AxisAligned box2) {
        return box1.minX - box2.maxX < -1.0E-7 && box1.maxX - box2.minX > 1.0E-7 && box1.minY - box2.maxY < -1.0E-7 && box1.maxY - box2.minY > 1.0E-7 && box1.minZ - box2.maxZ < -1.0E-7 && box1.maxZ - box2.minZ > 1.0E-7;
    }

    public static double collideX(AxisAligned target, AxisAligned source, double source_move) {
        if (source_move == 0.0) {
            return 0.0;
        } else if (source.minY - target.maxY < -1.0E-7 && source.maxY - target.minY > 1.0E-7 && source.minZ - target.maxZ < -1.0E-7 && source.maxZ - target.minZ > 1.0E-7) {
            double max_move;
            if (source_move >= 0.0) {
                max_move = target.minX - source.maxX;
                return max_move < -1.0E-7 ? source_move : Math.min(max_move, source_move);
            } else {
                max_move = target.maxX - source.minX;
                return max_move > 1.0E-7 ? source_move : Math.max(max_move, source_move);
            }
        } else {
            return source_move;
        }
    }

    public static double collideY(AxisAligned target, AxisAligned source, double source_move) {
        if (source_move == 0.0) {
            return 0.0;
        } else if (source.minX - target.maxX < -1.0E-7 && source.maxX - target.minX > 1.0E-7 && source.minZ - target.maxZ < -1.0E-7 && source.maxZ - target.minZ > 1.0E-7) {
            double max_move;
            if (source_move >= 0.0) {
                max_move = target.minY - source.maxY;
                return max_move < -1.0E-7 ? source_move : Math.min(max_move, source_move);
            } else {
                max_move = target.maxY - source.minY;
                return max_move > 1.0E-7 ? source_move : Math.max(max_move, source_move);
            }
        } else {
            return source_move;
        }
    }

    public static double collideZ(AxisAligned target, AxisAligned source, double source_move) {
        if (source_move == 0.0) {
            return 0.0;
        } else if (source.minX - target.maxX < -1.0E-7 && source.maxX - target.minX > 1.0E-7 && source.minY - target.maxY < -1.0E-7 && source.maxY - target.minY > 1.0E-7) {
            double max_move;
            if (source_move >= 0.0) {
                max_move = target.minZ - source.maxZ;
                return max_move < -1.0E-7 ? source_move : Math.min(max_move, source_move);
            } else {
                max_move = target.maxZ - source.minZ;
                return max_move > 1.0E-7 ? source_move : Math.max(max_move, source_move);
            }
        } else {
            return source_move;
        }
    }
}

