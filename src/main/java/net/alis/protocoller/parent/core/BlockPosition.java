package net.alis.protocoller.parent.core;

import com.google.common.collect.AbstractIterator;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.parent.phys.BaseBlockPosition;
import net.alis.protocoller.parent.phys.Vector3D;
import net.alis.protocoller.parent.util.Facing;
import net.alis.protocoller.parent.util.MathHelper;
import net.alis.protocoller.util.CopiedObject;
import org.bukkit.entity.Entity;

import javax.annotation.concurrent.Immutable;
import java.util.Iterator;

@Immutable
public class BlockPosition extends BaseBlockPosition implements CopiedObject {

    public static final BlockPosition ORIGIN = new BlockPosition(0, 0, 0);
    private static final int NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
    private static final int NUM_Z_BITS = NUM_X_BITS;
    private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
    private static final int Y_SHIFT = NUM_Z_BITS;
    private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
    private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
    private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
    private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;

    public BlockPosition(int x, int y, int z) {
        super(x, y, z);
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
    
    public BlockPosition add(BaseBlockPosition vec) {
        return vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0 ? this : new BlockPosition(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
    }

    public BlockPosition subtract(BaseBlockPosition vec) {
        return vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0 ? this : new BlockPosition(this.getX() - vec.getX(), this.getY() - vec.getY(), this.getZ() - vec.getZ());
    }

    public BlockPosition up() {
        return this.up(1);
    }

    public BlockPosition up(int n) {
        return this.offset(Facing.UP, n);
    }

    public BlockPosition down() {
        return this.down(1);
    }


    public BlockPosition down(int n) {
        return this.offset(Facing.DOWN, n);
    }


    public BlockPosition north() {
        return this.north(1);
    }

    public BlockPosition north(int n) {
        return this.offset(Facing.NORTH, n);
    }
    
    public BlockPosition south() {
        return this.south(1);
    }
    
    public BlockPosition south(int n) {
        return this.offset(Facing.SOUTH, n);
    }
    
    public BlockPosition west() {
        return this.west(1);
    }
    
    public BlockPosition west(int n) {
        return this.offset(Facing.WEST, n);
    }
    
    public BlockPosition east() {
        return this.east(1);
    }
    
    public BlockPosition east(int n) {
        return this.offset(Facing.EAST, n);
    }
    
    public BlockPosition offset(Facing facing) {
        return this.offset(facing, 1);
    }
    
    public BlockPosition offset(Facing facing, int n) {
        return n == 0 ? this : new BlockPosition(this.getX() + facing.getFrontOffsetX() * n, this.getY() + facing.getFrontOffsetY() * n, this.getZ() + facing.getFrontOffsetZ() * n);
    }
    
    public BlockPosition crossProduct(BaseBlockPosition vec) {
        return new BlockPosition(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }
    
    public long toLong() {
        return ((long)this.getX() & X_MASK) << X_SHIFT | ((long)this.getY() & Y_MASK) << Y_SHIFT | ((long)this.getZ() & Z_MASK) << 0;
    }
    
    public static BlockPosition fromLong(long serialized) {
        int i = (int)(serialized << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
        int j = (int)(serialized << 64 - Y_SHIFT - NUM_Y_BITS >> 64 - NUM_Y_BITS);
        int k = (int)(serialized << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);
        return new BlockPosition(i, j, k);
    }

    public static Iterable<BlockPosition> getAllInBox(BlockPosition from, BlockPosition to) {
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
                            return (BlockPosition)this.endOfData();
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
    
    public BlockPosition toImmutable() {
        return this;
    }

    public static Iterable<MutableBlockPosition> getAllInBoxMutable(BlockPosition from, BlockPosition to) {
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
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getBlockPositionClass(), int.class, int.class, int.class),
                getX(), getY(), getZ()
        );
    }
}
