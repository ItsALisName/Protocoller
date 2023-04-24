package net.alis.protocoller.samples.core;

import com.google.common.collect.Lists;
import net.alis.protocoller.samples.phys.BaseBlockPosition;
import net.alis.protocoller.samples.util.Direction;
import net.alis.protocoller.samples.util.MathHelper;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class PooledMutableBlockPosition extends MutableBlockPosition {
    private boolean released;
    private static final List<PooledMutableBlockPosition> POOL = Lists.newArrayList();

    private PooledMutableBlockPosition(int xIn, int yIn, int zIn) {
        super(xIn, yIn, zIn);
    }

    public static @NotNull PooledMutableBlockPosition retain() {
        return retain(0, 0, 0);
    }

    public static @NotNull PooledMutableBlockPosition retain(double xIn, double yIn, double zIn) {
        return retain(MathHelper.floor(xIn), MathHelper.floor(yIn), MathHelper.floor(zIn));
    }

    public static @NotNull PooledMutableBlockPosition retain(@NotNull BaseBlockPosition vec) {
        return retain(vec.getX(), vec.getY(), vec.getZ());
    }

    public static @NotNull PooledMutableBlockPosition retain(int xIn, int yIn, int zIn) {
        synchronized (POOL) {
            if (!POOL.isEmpty()) {
                PooledMutableBlockPosition BlockPosition$pooledmutableBlockPosition = (PooledMutableBlockPosition)POOL.remove(POOL.size() - 1);
                if (BlockPosition$pooledmutableBlockPosition != null && BlockPosition$pooledmutableBlockPosition.released) {
                    BlockPosition$pooledmutableBlockPosition.released = false;
                    BlockPosition$pooledmutableBlockPosition.setPos(xIn, yIn, zIn);
                    return BlockPosition$pooledmutableBlockPosition;
                }
            }
        }
        return new PooledMutableBlockPosition(xIn, yIn, zIn);
    }

    public void release() {
        synchronized (POOL) {
            if (POOL.size() < 100) {
                POOL.add(PooledMutableBlockPosition.retain(this));
            }
            this.released = true;
        }
    }

    public PooledMutableBlockPosition setPos(int xIn, int yIn, int zIn) {
        if (this.released) {
            Bukkit.getConsoleSender().sendMessage("[Protocoller] PooledMutableBlockPositionition modified after it was released.");
            this.released = false;
        }
        return (PooledMutableBlockPosition)super.setPos(xIn, yIn, zIn);
    }

        /*public PooledMutableBlockPosition setPos(Entity entityIn) {
            return (PooledMutableBlockPosition)super.setPos(entityIn);
        }*/

    public PooledMutableBlockPosition setPos(double xIn, double yIn, double zIn) {
        return (PooledMutableBlockPosition)super.setPos(xIn, yIn, zIn);
    }

    public PooledMutableBlockPosition setPos(BaseBlockPosition vec) {
        return (PooledMutableBlockPosition)super.setPos(vec);
    }

    public PooledMutableBlockPosition move(Direction direction) {
        return (PooledMutableBlockPosition)super.move(direction);
    }

    public PooledMutableBlockPosition move(Direction direction, int i) {
        return (PooledMutableBlockPosition)super.move(direction, i);
    }
}
