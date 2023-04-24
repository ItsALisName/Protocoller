package net.alis.protocoller.samples.core;

import com.google.common.collect.AbstractIterator;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.phys.BaseBlockPosition;
import net.alis.protocoller.samples.phys.Vector3D;
import net.alis.protocoller.samples.phys.Vector3I;
import net.alis.protocoller.samples.util.Direction;
import net.alis.protocoller.samples.util.MathHelper;
import net.alis.protocoller.util.ObjectSample;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.Immutable;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Immutable
public class BlockPosition extends BaseBlockPosition implements ObjectSample {

    public static final BlockPosition ZERO = new BlockPosition(0, 0, 0);
    private static final int PACKED_X_LENGTH = 1 + MathHelper.calculateLogBaseTwo(MathHelper.smallestEncompassingPowerOfTwo(30000000));
    private static final int PACKED_Z_LENGTH = PACKED_X_LENGTH;
    public static final int PACKED_Y_LENGTH = 64 - PACKED_X_LENGTH - PACKED_Z_LENGTH;
    private static final long PACKED_X_MASK = (1L << PACKED_X_LENGTH) - 1L;
    private static final long PACKED_Y_MASK = (1L << PACKED_Y_LENGTH) - 1L;
    private static final long PACKED_Z_MASK = (1L << PACKED_Z_LENGTH) - 1L;
    private static final int Y_OFFSET = 0;
    private static final int Z_OFFSET = PACKED_Y_LENGTH;
    private static final int X_OFFSET = PACKED_Y_LENGTH + PACKED_Z_LENGTH;

    public BlockPosition(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockPosition(Vector3I vector3I) {
        this(vector3I.getX(), vector3I.getY(), vector3I.getZ());
    }

    public BlockPosition(double x, double y, double z) {
        super(x, y, z);
    }

    public BlockPosition(Entity entity) {
        this(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
    }

    public BlockPosition(Vector3D vec) {
        this(vec.x, vec.y, vec.z);
    }

    public BlockPosition(BaseBlockPosition source) {
        this(source.getX(), source.getY(), source.getZ());
    }
    
    public BlockPosition add(double x, double y, double z) {
        return x == 0.0D && y == 0.0D && z == 0.0D ? this : new BlockPosition((double)this.getX() + x, (double)this.getY() + y, (double)this.getZ() + z);
    }
    
    public BlockPosition add(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new BlockPosition(this.getX() + x, this.getY() + y, this.getZ() + z);
    }
    
    public BlockPosition add(@NotNull BaseBlockPosition vec) {
        return vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0 ? this : new BlockPosition(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
    }

    public BlockPosition subtract(@NotNull BaseBlockPosition vec) {
        return vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0 ? this : new BlockPosition(this.getX() - vec.getX(), this.getY() - vec.getY(), this.getZ() - vec.getZ());
    }

    public BlockPosition up() {
        return this.up(1);
    }

    public BlockPosition up(int n) {
        return this.offset(Direction.UP, n);
    }

    public BlockPosition down() {
        return this.down(1);
    }


    public BlockPosition down(int n) {
        return this.offset(Direction.DOWN, n);
    }


    public BlockPosition north() {
        return this.north(1);
    }

    public BlockPosition north(int n) {
        return this.offset(Direction.NORTH, n);
    }
    
    public BlockPosition south() {
        return this.south(1);
    }
    
    public BlockPosition south(int n) {
        return this.offset(Direction.SOUTH, n);
    }
    
    public BlockPosition west() {
        return this.west(1);
    }
    
    public BlockPosition west(int n) {
        return this.offset(Direction.WEST, n);
    }
    
    public BlockPosition east() {
        return this.east(1);
    }
    
    public BlockPosition east(int n) {
        return this.offset(Direction.EAST, n);
    }
    
    public BlockPosition offset(Direction direction) {
        return this.offset(direction, 1);
    }
    
    public BlockPosition offset(Direction direction, int n) {
        return n == 0 ? this : new BlockPosition(this.getX() + direction.getStepX() * n, this.getY() + direction.getStepY() * n, this.getZ() + direction.getStepZ() * n);
    }

    public BlockPosition offset(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new BlockPosition(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    public long asLong() {
        return asLong(this.getX(), this.getY(), this.getZ());
    }

    public static long asLong(int x, int y, int z) {
        long i = 0L;
        i |= ((long)x & PACKED_X_MASK) << X_OFFSET;
        i |= ((long) y & PACKED_Y_MASK);
        return i | ((long)z & PACKED_Z_MASK) << Z_OFFSET;
    }
    
    public BlockPosition crossProduct(@NotNull BaseBlockPosition vec) {
        return new BlockPosition(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    public static int getX(long l) {
        return (int)(l << 64 - X_OFFSET - PACKED_X_LENGTH >> 64 - PACKED_X_LENGTH);
    }

    public static int getY(long l) {
        return (int)(l << 64 - PACKED_Y_LENGTH >> 64 - PACKED_Y_LENGTH);
    }

    public static int getZ(long l) {
        return (int)(l << 64 - Z_OFFSET - PACKED_Z_LENGTH >> 64 - PACKED_Z_LENGTH);
    }

    public static BlockPosition of(long ser) {
        return new BlockPosition(getX(ser), getY(ser), getZ(ser));
    }

    public static @NotNull Iterable<BlockPosition> getAllInBox(@NotNull BlockPosition from, @NotNull BlockPosition to) {
        final BlockPosition BlockPosition = new BlockPosition(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPosition BlockPosition1 = new BlockPosition(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return new Iterable<BlockPosition>() {
            public Iterator<BlockPosition> iterator() {
                return new AbstractIterator<BlockPosition>() {
                    private BlockPosition lastReturned;
                    protected BlockPosition computeNext() {
                        if (this.lastReturned == null) {
                            this.lastReturned = BlockPosition;
                            return this.lastReturned;
                        } else if (this.lastReturned.equals(BlockPosition1)) {
                            return this.endOfData();
                        } else {
                            int i = this.lastReturned.getX();
                            int j = this.lastReturned.getY();
                            int k = this.lastReturned.getZ();
                            if (i < BlockPosition1.getX()) {
                                ++i;
                            } else if (j < BlockPosition1.getY()) {
                                i = BlockPosition.getX();
                                ++j;
                            } else if (k < BlockPosition1.getZ()) {
                                i = BlockPosition.getX();
                                j = BlockPosition.getY();
                                ++k;
                            }
                            this.lastReturned = new BlockPosition(i, j, k);
                            return this.lastReturned;
                        }
                    }
                };
            }
        };
    }

    public static Stream<BlockPosition> betweenClosedStream(int i1, int i2, int i3, int i4, int i5, int i6) {
        return StreamSupport.stream(betweenClosed(i1, i2, i3, i4, i5, i6).spliterator(), false);
    }

    @Contract(pure = true)
    public static @NotNull Iterable<BlockPosition> betweenClosed(int i1, int i2, int i3, int i4, int i5, int i6) {
        int i = i4 - i1 + 1;
        int j = i5 - i2 + 1;
        int k = i6 - i3 + 1;
        int l = i * j * k;
        return () -> {
            return new AbstractIterator<BlockPosition>() {
                private final MutableBlockPosition cursor = new MutableBlockPosition();
                private int index;

                protected BlockPosition computeNext() {
                    if (this.index == l) {
                        return this.endOfData();
                    } else {
                        int i1 = this.index % i;
                        int j1 = this.index / i;
                        int k1 = j1 % j;
                        int l1 = j1 / j;
                        ++this.index;
                        return this.cursor.setPos(i1 + i1, i2 + k1, i3 + l1);
                    }
                }
            };
        };
    }
    
    public BlockPosition toImmutable() {
        return this;
    }

    public static @NotNull Iterable<MutableBlockPosition> getAllInBoxMutable(@NotNull BlockPosition from, @NotNull BlockPosition to) {
        final BlockPosition BlockPosition = new BlockPosition(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPosition BlockPosition1 = new BlockPosition(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return new Iterable<MutableBlockPosition>() {
            public Iterator<MutableBlockPosition> iterator() {
                return new AbstractIterator<MutableBlockPosition>() {
                    private MutableBlockPosition theBlockPosition;
                    protected MutableBlockPosition computeNext() {
                        if (this.theBlockPosition == null) {
                            this.theBlockPosition = new MutableBlockPosition(BlockPosition.getX(), BlockPosition.getY(), BlockPosition.getZ());
                            return this.theBlockPosition;
                        } else if (this.theBlockPosition.equals(BlockPosition1)) {
                            return (MutableBlockPosition)this.endOfData();
                        } else {
                            int i = this.theBlockPosition.getX();
                            int j = this.theBlockPosition.getY();
                            int k = this.theBlockPosition.getZ();
                            if (i < BlockPosition1.getX()) {
                                ++i;
                            } else if (j < BlockPosition1.getY()) {
                                i = BlockPosition.getX();
                                ++j;
                            } else if (k < BlockPosition1.getZ()) {
                                i = BlockPosition.getX();
                                j = BlockPosition.getY();
                                ++k;
                            }
                            this.theBlockPosition.x = i;
                            this.theBlockPosition.y = j;
                            this.theBlockPosition.z = k;
                            return this.theBlockPosition;
                        }
                    }
                };
            }
        };
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, int.class, int.class, int.class),
                getX(), getY(), getZ()
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getBlockPositionClass();
    }

}
