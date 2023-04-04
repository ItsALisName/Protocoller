package net.alis.protocoller.samples.core;

import it.unimi.dsi.fastutil.longs.LongConsumer;
import net.alis.protocoller.samples.phys.Vector3I;
import net.alis.protocoller.samples.util.Direction;
import net.alis.protocoller.samples.util.MathHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SectionPosition extends Vector3I {

    SectionPosition(int x, int y, int z) {
        super(x, y, z);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull SectionPosition of(int x, int y, int z) {
        return new SectionPosition(x, y, z);
    }

    @Contract("_ -> new")
    public static @NotNull SectionPosition of(@NotNull BlockPosition blockPosition) {
        return new SectionPosition(blockToSectionCoord(blockPosition.getX()), blockToSectionCoord(blockPosition.getY()), blockToSectionCoord(blockPosition.getZ()));
    }

    @Contract("_ -> new")
    public static @NotNull SectionPosition of(@NotNull Position position) {
        return new SectionPosition(blockToSectionCoord(position.x()), blockToSectionCoord(position.y()), blockToSectionCoord(position.z()));
    }

    @Contract("_ -> new")
    public static @NotNull SectionPosition of(long ser) {
        return new SectionPosition(x(ser), y(ser), z(ser));
    }

    public static long offset(long p_123192_, @NotNull Direction direction) {
        return offset(p_123192_, direction.getStepX(), direction.getStepY(), direction.getStepZ());
    }

    public static long offset(long l, int i1, int i2, int i3) {
        return asLong(x(l) + i1, y(l) + i2, z(l) + i3);
    }

    public static int posToSectionCoord(double d) {
        return blockToSectionCoord(MathHelper.floor(d));
    }

    public static int blockToSectionCoord(int i) {
        return i >> 4;
    }

    public static int blockToSectionCoord(double i) {
        return MathHelper.floor(i) >> 4;
    }

    public static int sectionRelative(int i) {
        return i & 15;
    }

    public static short sectionRelativePos(BlockPosition blockPosition) {
        int i = sectionRelative(blockPosition.getX());
        int j = sectionRelative(blockPosition.getY());
        int k = sectionRelative(blockPosition.getZ());
        return (short)(i << 8 | k << 4 | j);
    }

    public static int sectionRelativeX(short s) {
        return s >>> 8 & 15;
    }

    public static int sectionRelativeY(short s) {
        return s & 15;
    }

    public static int sectionRelativeZ(short s) {
        return s >>> 4 & 15;
    }

    public int relativeToBlockX(short s) {
        return this.minBlockX() + sectionRelativeX(s);
    }

    public int relativeToBlockY(short s) {
        return this.minBlockY() + sectionRelativeY(s);
    }

    public int relativeToBlockZ(short s) {
        return this.minBlockZ() + sectionRelativeZ(s);
    }

    public BlockPosition relativeToBlockPosition(short s) {
        return new BlockPosition(this.relativeToBlockX(s), this.relativeToBlockY(s), this.relativeToBlockZ(s));
    }

    public static int sectionToBlockCoord(int i) {
        return i << 4;
    }

    public static int sectionToBlockCoord(int i, int i1) {
        return sectionToBlockCoord(i) + i1;
    }

    public static int x(long l) {
        return (int)(l >> 42);
    }

    public static int y(long l) {
        return (int)(l << 44 >> 44);
    }

    public static int z(long l) {
        return (int)(l << 22 >> 42);
    }

    public int x() {
        return this.getX();
    }

    public int y() {
        return this.getY();
    }

    public int z() {
        return this.getZ();
    }

    public int minBlockX() {
        return sectionToBlockCoord(this.x());
    }

    public int minBlockY() {
        return sectionToBlockCoord(this.y());
    }

    public int minBlockZ() {
        return sectionToBlockCoord(this.z());
    }

    public int maxBlockX() {
        return sectionToBlockCoord(this.x(), 15);
    }

    public int maxBlockY() {
        return sectionToBlockCoord(this.y(), 15);
    }

    public int maxBlockZ() {
        return sectionToBlockCoord(this.z(), 15);
    }

    public static long blockToSection(long ser) {
        return asLong(blockToSectionCoord(BlockPosition.getX(ser)), blockToSectionCoord(BlockPosition.getY(ser)), blockToSectionCoord(BlockPosition.getZ(ser)));
    }

    public static long getZeroNode(long l) {
        return l & -1048576L;
    }

    public BlockPosition origin() {
        return new BlockPosition(sectionToBlockCoord(this.x()), sectionToBlockCoord(this.y()), sectionToBlockCoord(this.z()));
    }

    public BlockPosition center() {
        int i = 8;
        return this.origin().offset(8, 8, 8);
    }

    public static long asLong(BlockPosition blockPosition) {
        return asLong(blockToSectionCoord(blockPosition.getX()), blockToSectionCoord(blockPosition.getY()), blockToSectionCoord(blockPosition.getZ()));
    }

    public static long asLong(int x, int y, int z) {
        long i = 0L;
        i |= ((long)x & 4194303L) << 42;
        i |= ((long) y & 1048575L);
        return i | ((long)z & 4194303L) << 20;
    }

    public long asLong() {
        return asLong(this.x(), this.y(), this.z());
    }

    public SectionPosition offset(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new SectionPosition(this.x() + x, this.y() + y, this.z() + z);
    }

    public Stream<BlockPosition> blocksInside() {
        return BlockPosition.betweenClosedStream(this.minBlockX(), this.minBlockY(), this.minBlockZ(), this.maxBlockX(), this.maxBlockY(), this.maxBlockZ());
    }

    public static @NotNull Stream<SectionPosition> cube(@NotNull SectionPosition sectionPosition, int i1) {
        int i = sectionPosition.x();
        int j = sectionPosition.y();
        int k = sectionPosition.z();
        return betweenClosedStream(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull Stream<SectionPosition> betweenClosedStream(final int i1, final int i2, final int i3, final int i4, final int i5, final int i6) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<SectionPosition>(((long) (i4 - i1 + 1) * (i5 - i2 + 1) * (i6 - i3 + 1)), 64) {
            final Cursor3D cursor = new Cursor3D(i1, i2, i3, i4, i5, i6);

            public boolean tryAdvance(Consumer<? super SectionPosition> consumer) {
                if (this.cursor.advance()) {
                    consumer.accept(new SectionPosition(this.cursor.nextX(), this.cursor.nextY(), this.cursor.nextZ()));
                    return true;
                } else {
                    return false;
                }
            }
        }, false);
    }

    public static void aroundAndAtBlockPosition(@NotNull BlockPosition position, LongConsumer consumer) {
        aroundAndAtBlockPosition(position.getX(), position.getY(), position.getZ(), consumer);
    }

    public static void aroundAndAtBlockPosition(long l, LongConsumer consumer) {
        aroundAndAtBlockPosition(BlockPosition.getX(l), BlockPosition.getY(l), BlockPosition.getZ(l), consumer);
    }

    public static void aroundAndAtBlockPosition(int i1, int i2, int i3, LongConsumer p_194638_) {
        int i4 = blockToSectionCoord(i1 - 1);
        int j = blockToSectionCoord(i1 + 1);
        int k = blockToSectionCoord(i2 - 1);
        int l = blockToSectionCoord(i2 + 1);
        int i5 = blockToSectionCoord(i3 - 1);
        int j1 = blockToSectionCoord(i3 + 1);
        if (i4 == j && k == l && i5 == j1) {
            p_194638_.accept(asLong(i4, k, i5));
        } else {
            for(int k1 = i4; k1 <= j; ++k1) {
                for(int l1 = k; l1 <= l; ++l1) {
                    for(int i6 = i5; i6 <= j1; ++i6) {
                        p_194638_.accept(asLong(k1, l1, i6));
                    }
                }
            }
        }

    }
}
