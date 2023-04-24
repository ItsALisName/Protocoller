package net.alis.protocoller.samples.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.phys.Vector3I;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public enum Direction {
    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vector3I(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vector3I(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vector3I(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vector3I(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vector3I(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vector3I(1, 0, 0));
    private final int data3d;
    private final int oppositeIndex;
    private final int data2d;
    private final String name;
    private final Axis axis;
    private final AxisDirection axisDirection;
    private final Vector3I normal;
    public static final Direction[] VALUES = values();
    public static final Direction[] BY_3D_DATA = Arrays.stream(VALUES).sorted(Comparator.comparingInt((direction) -> direction.data3d)).toArray(Direction[]::new);
    public static final Direction[] BY_2D_DATA = Arrays.stream(VALUES).filter((direction) -> direction.getAxis().isHorizontal()).sorted(Comparator.comparingInt((direction) -> direction.data2d)).toArray(Direction[]::new);
    public static final Long2ObjectMap<Direction> BY_NORMAL = Arrays.stream(VALUES).collect(Collectors.toMap((vector3I) -> (new BlockPosition(vector3I.getNormal())).asLong(), (direction) -> direction, (direction, direction1) -> ExceptionBuilder.throwException(new IllegalArgumentException("Duplicate keys"), true), Long2ObjectOpenHashMap::new));

    private Direction(int data3d, int oppositeIndex, int data2d, String name, AxisDirection axisDirection, Axis axis, Vector3I normal) {
        this.data3d = data3d;
        this.data2d = data2d;
        this.oppositeIndex = oppositeIndex;
        this.name = name;
        this.axis = axis;
        this.axisDirection = axisDirection;
        this.normal = normal;
    }

    @Contract("_, _, _ -> new")
    public static Direction @NotNull [] makeDirectionArray(@NotNull Direction direction, @NotNull Direction direction1, @NotNull Direction direction2) {
        return new Direction[]{direction, direction1, direction2, direction2.getOpposite(), direction1.getOpposite(), direction.getOpposite()};
    }

    public int get3DDataValue() {
        return this.data3d;
    }

    public int get2DDataValue() {
        return this.data2d;
    }

    public AxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    public Direction getOpposite() {
        return from3DDataValue(this.oppositeIndex);
    }

    public Direction getClockWise(Axis p_175363_) {
        Direction direction;
        switch (p_175363_) {
            case X:
                direction = this != WEST && this != EAST ? this.getClockWiseX() : this;
                break;
            case Z:
                direction = this != NORTH && this != SOUTH ? this.getClockWiseZ() : this;
                break;
            case Y:
                direction = this != UP && this != DOWN ? this.getClockWise() : this;
                break;
            default:
                return ExceptionBuilder.throwException(new IncompatibleClassChangeError(), true);
        }

        return direction;
    }

    public Direction getCounterClockWise(@NotNull Axis p_175365_) {
        Direction direction;
        switch (p_175365_) {
            case X:
                direction = this != WEST && this != EAST ? this.getCounterClockWiseX() : this;
                break;
            case Z:
                direction = this != NORTH && this != SOUTH ? this.getCounterClockWiseZ() : this;
                break;
            case Y:
                direction = this != UP && this != DOWN ? this.getCounterClockWise() : this;
                break;
            default:
                return ExceptionBuilder.throwException(new IncompatibleClassChangeError(), true);
        }

        return direction;
    }

    public Direction getClockWise() {
        Direction direction;
        switch (this) {
            case NORTH:
                direction = EAST;
                break;
            case SOUTH:
                direction = WEST;
                break;
            case WEST:
                direction = NORTH;
                break;
            case EAST:
                direction = SOUTH;
                break;
            default:
                return ExceptionBuilder.throwException(new IllegalStateException("Unable to get Y-rotated direction of " + this), true);
        }

        return direction;
    }

    public Direction getClockWiseX() {
        Direction direction;
        switch (this) {
            case DOWN:
                direction = SOUTH;
                break;
            case UP:
                direction = NORTH;
                break;
            case NORTH:
                direction = DOWN;
                break;
            case SOUTH:
                direction = UP;
                break;
            default:
                return ExceptionBuilder.throwException(new IllegalStateException("Unable to get X-rotated direction of " + this), true);
        }
        return direction;
    }

    public Direction getCounterClockWiseX() {
        Direction direction;
        switch (this) {
            case DOWN:
                direction = NORTH;
                break;
            case UP:
                direction = SOUTH;
                break;
            case NORTH:
                direction = UP;
                break;
            case SOUTH:
                direction = DOWN;
                break;
            default:
                return ExceptionBuilder.throwException(new IllegalStateException("Unable to get X-rotated direction of " + this), true);
        }
        return direction;
    }

    public Direction getClockWiseZ() {
        Direction direction;
        switch (this) {
            case DOWN:
                direction = WEST;
                break;
            case UP:
                direction = EAST;
                break;
            case NORTH:
            case SOUTH:
            default:
                return ExceptionBuilder.throwException(new IllegalStateException("Unable to get Z-rotated direction of " + this), true);
            case WEST:
                direction = UP;
                break;
            case EAST:
                direction = DOWN;
        }

        return direction;
    }

    public Direction getCounterClockWiseZ() {
        Direction direction;
        switch (this) {
            case DOWN:
                direction = EAST;
                break;
            case UP:
                direction = WEST;
                break;
            case NORTH:
            case SOUTH:
            default:
                return ExceptionBuilder.throwException(new IllegalStateException("Unable to get Z-rotated direction of " + this), true);
            case WEST:
                direction = DOWN;
                break;
            case EAST:
                direction = UP;
        }

        return direction;
    }

    public Direction getCounterClockWise() {
        Direction direction;
        switch (this) {
            case NORTH:
                direction = WEST;
                break;
            case SOUTH:
                direction = EAST;
                break;
            case WEST:
                direction = SOUTH;
                break;
            case EAST:
                direction = NORTH;
                break;
            default:
                return ExceptionBuilder.throwException(new IllegalStateException("Unable to get CCW direction of " + this), true);
        }

        return direction;
    }

    public int getStepX() {
        return this.normal.getX();
    }

    public int getStepY() {
        return this.normal.getY();
    }

    public int getStepZ() {
        return this.normal.getZ();
    }

    public String getName() {
        return this.name;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public static Direction from3DDataValue(int val) {
        return BY_3D_DATA[MathHelper.abs(val % BY_3D_DATA.length)];
    }

    public static Direction from2DDataValue(int val) {
        return BY_2D_DATA[MathHelper.abs(val % BY_2D_DATA.length)];
    }

    @Nullable
    public static Direction fromNormal(BlockPosition blockPosition) {
        return BY_NORMAL.get(blockPosition.asLong());
    }

    @Nullable
    public static Direction fromNormal(int x, int y, int z) {
        return BY_NORMAL.get(BlockPosition.asLong(x, y, z));
    }

    public static Direction fromYRot(double yRot) {
        return from2DDataValue((int)Math.floor(yRot / 90.0D + 0.5D) & 3);
    }

    public static Direction fromAxisAndDirection(Axis axis, AxisDirection axisDirection) {
        Direction direction;
        switch (axis) {
            case X:
                direction = axisDirection == AxisDirection.POSITIVE ? EAST : WEST;
                break;
            case Z:
                direction = axisDirection == AxisDirection.POSITIVE ? SOUTH : NORTH;
                break;
            case Y:
                direction = axisDirection == AxisDirection.POSITIVE ? UP : DOWN;
                break;
            default:
                return ExceptionBuilder.throwException(new IncompatibleClassChangeError(), true);
        }

        return direction;
    }

    public float toYRot() {
        return (float)((this.data2d & 3) * 90);
    }

    public static Direction getNearest(double x, double y, double z) {
        return getNearest((float)x, (float)y, (float)z);
    }

    public static Direction getNearest(float x, float y, float z) {
        Direction direction = NORTH;
        float f = Float.MIN_VALUE;

        for(Direction direction1 : VALUES) {
            float f1 = x * (float)direction1.normal.getX() + y * (float)direction1.normal.getY() + z * (float)direction1.normal.getZ();
            if (f1 > f) {
                f = f1;
                direction = direction1;
            }
        }

        return direction;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }

    public static @NotNull Direction get(AxisDirection axisDirection, Axis axis) {
        for(Direction direction : VALUES) {
            if (direction.getAxisDirection() == axisDirection && direction.getAxis() == axis) {
                return direction;
            }
        }
        return ExceptionBuilder.throwException(new IllegalArgumentException("No such direction: " + axisDirection + " " + axis), true);
    }

    public Vector3I getNormal() {
        return this.normal;
    }

    public boolean isFacingAngle(float a) {
        float f = a * ((float)Math.PI / 180F);
        float f1 = -MathHelper.sin(f);
        float f2 = MathHelper.cos(f);
        return (float)this.normal.getX() * f1 + (float)this.normal.getZ() * f2 > 0.0F;
    }

    @Contract(pure = true)
    public static @Nullable Direction getById(int id) {
        for(Direction f : VALUES) {
            if(f.ordinal() == id) return f;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.ordinal());
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getDirectionEnum();
    }
}
