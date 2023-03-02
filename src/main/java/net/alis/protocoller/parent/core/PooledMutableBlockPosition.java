package net.alis.protocoller.parent.core;

import com.google.common.collect.Lists;
import net.alis.protocoller.parent.phys.BaseBlockPosition;
import net.alis.protocoller.parent.util.Facing;
import net.alis.protocoller.parent.util.MathHelper;
import org.bukkit.Bukkit;

import java.util.List;

public final class PooledMutableBlockPosition extends MutableBlockPosition {
    private boolean released;
    private static final List<PooledMutableBlockPosition> POOL = Lists.newArrayList();

    private PooledMutableBlockPosition(int xIn, int yIn, int zIn) {
        super(xIn, yIn, zIn);
    }

    public static PooledMutableBlockPosition retain() {
        return retain(0, 0, 0);
    }

    public static PooledMutableBlockPosition retain(double xIn, double yIn, double zIn) {
        return retain(MathHelper.floorDouble(xIn), MathHelper.floorDouble(yIn), MathHelper.floorDouble(zIn));
    }

    public static PooledMutableBlockPosition retain(BaseBlockPosition vec) {
        return retain(vec.getX(), vec.getY(), vec.getZ());
    }

    public static PooledMutableBlockPosition retain(int xIn, int yIn, int zIn) {
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

    public PooledMutableBlockPosition move(Facing facing) {
        return (PooledMutableBlockPosition)super.move(facing);
    }

    public PooledMutableBlockPosition move(Facing facing, int i) {
        return (PooledMutableBlockPosition)super.move(facing, i);
    }
}
