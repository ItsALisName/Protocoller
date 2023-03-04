package net.alis.protocoller.parent.util;

import com.google.common.collect.Maps;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.parent.phys.BaseBlockPosition;
import net.alis.protocoller.parent.phys.Vector3D;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

public enum Facing {

    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new BaseBlockPosition(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new BaseBlockPosition(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new BaseBlockPosition(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new BaseBlockPosition(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new BaseBlockPosition(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new BaseBlockPosition(1, 0, 0));
    
    private final int id;
    private final int opposite;
    private final int horizontalIndex;
    private final String name;
    private final Axis axis;
    private final AxisDirection axisDirection;
    private final BaseBlockPosition directionVec;
    public static final Facing[] VALUES = new Facing[6];
    private static final Facing[] HORIZONTALS = new Facing[4];
    private static final Map<String, Facing> NAME_LOOKUP = Maps.newHashMap();

    Facing(int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, AxisDirection axisDirectionIn, Axis axisIn, BaseBlockPosition directionVecIn) {
        this.id = indexIn;
        this.horizontalIndex = horizontalIndexIn;
        this.opposite = oppositeIn;
        this.name = nameIn;
        this.axis = axisIn;
        this.axisDirection = axisDirectionIn;
        this.directionVec = directionVecIn;
    }

    public static Facing getById(int id) {
        for(Facing f : values()) {
            if(f.ordinal() == id) return f;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getDirectionEnum(), this.ordinal());
    }

    public int getId() {
        return this.id;
    }

    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }

    public AxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    public Facing getOpposite() {
        return VALUES[this.opposite];
    }

    public Facing rotateAround(Axis axis) {
        switch (axis) {
            case X:
                if (this != WEST && this != EAST) {
                    return this.rotateX();
                }
                return this;
            case Y:
                if (this != UP && this != DOWN) {
                    return this.rotateY();
                }
                return this;
            case Z:
                if (this != NORTH && this != SOUTH) {
                    return this.rotateZ();
                }
                return this;
            default:
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
        }
    }

    public Facing rotateY() {
        switch (this) {
            case NORTH:
                return EAST;

            case EAST:
                return SOUTH;

            case SOUTH:
                return WEST;

            case WEST:
                return NORTH;

            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    private Facing rotateX() {
        switch (this) {
            case NORTH:
                return DOWN;
            case EAST:
            case WEST:
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
            case SOUTH:
                return UP;
            case UP:
                return NORTH;
            case DOWN:
                return SOUTH;
        }
    }

    private Facing rotateZ() {
        switch (this) {
            case EAST:
                return DOWN;
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            case WEST:
                return UP;
            case UP:
                return EAST;
            case DOWN:
                return WEST;
        }
    }

    public Facing rotateYCCW() {
        switch (this) {
            case NORTH:
                return WEST;

            case EAST:
                return NORTH;

            case SOUTH:
                return EAST;

            case WEST:
                return SOUTH;

            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public int getFrontOffsetX() {
        return this.axis == Axis.X ? this.axisDirection.getOffset() : 0;
    }

    public int getFrontOffsetY() {
        return this.axis == Axis.Y ? this.axisDirection.getOffset() : 0;
    }

    public int getFrontOffsetZ() {
        return this.axis == Axis.Z ? this.axisDirection.getOffset() : 0;
    }

    public String getName2() {
        return this.name;
    }

    public Axis getAxis() {
        return this.axis;
    }

    @Nullable
    public static Facing byName(String name) {
        return name == null ? null : (Facing)NAME_LOOKUP.get(name.toLowerCase());
    }

    public static Facing getFront(int index) {
        return VALUES[MathHelper.abs(index % VALUES.length)];
    }

    public static Facing getHorizontal(int i) {
        return HORIZONTALS[MathHelper.abs(i % HORIZONTALS.length)];
    }

    public static Facing fromAngle(double angle) {
        return getHorizontal(MathHelper.floor(angle / 90.0D + 0.5D) & 3);
    }

    public float getHorizontalAngle() {
        return (float)((this.horizontalIndex & 3) * 90);
    }
    
    public static Facing random(Random rand) {
        return values()[rand.nextInt(values().length)];
    }

    public static Facing getFacingFromVector(float x, float y, float z) {
        Facing facing = NORTH;
        float f = Float.MIN_VALUE;
        for (Facing facing1 : values()) {
            float f1 = x * (float)facing1.directionVec.getX() + y * (float)facing1.directionVec.getY() + z * (float)facing1.directionVec.getZ();
            if (f1 > f) {
                f = f1;
                facing = facing1;
            }
        }
        return facing;
    }

    public static Facing getFacingFromVector(Vector3D vector) {
        return getFacingFromVector(Double.valueOf(vector.x).floatValue(), Double.valueOf(vector.y).floatValue(), Double.valueOf(vector.z).floatValue());
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public static Facing getFacingFromAxis(AxisDirection axisDirectionIn, Axis axisIn) {
        for (Facing facing : values()) {
            if (facing.getAxisDirection() == axisDirectionIn && facing.getAxis() == axisIn) {
                return facing;
            }
        }
        throw new IllegalArgumentException("No such direction: " + axisDirectionIn + " " + axisIn);
    }
    
    public BaseBlockPosition getDirectionVec() {
        return this.directionVec;
    }

    static {
        for (Facing facing : values()) {
            VALUES[facing.id] = facing;

            if (facing.getAxis().isHorizontal()) {
                HORIZONTALS[facing.horizontalIndex] = facing;
            }

            NAME_LOOKUP.put(facing.getName2().toLowerCase(), facing);
        }
    }
    
}
