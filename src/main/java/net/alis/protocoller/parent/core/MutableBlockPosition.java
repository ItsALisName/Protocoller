package net.alis.protocoller.parent.core;

import net.alis.protocoller.parent.phys.BaseBlockPosition;
import net.alis.protocoller.parent.util.Facing;
import net.alis.protocoller.parent.util.MathHelper;

public class MutableBlockPosition extends BlockPosition {
    protected int x;
    protected int y;
    protected int z;

    public MutableBlockPosition() {
        this(0, 0, 0);
    }

    public MutableBlockPosition(BlockPosition pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public MutableBlockPosition(int x_, int y_, int z_) {
        super(0, 0, 0);
        this.x = x_;
        this.y = y_;
        this.z = z_;
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

    public MutableBlockPosition setPos(int xIn, int yIn, int zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
        return this;
    }

        /*public MutableBlockPosition setPos(Entity entityIn) {
            return this.setPos(entityIn.posX, entityIn.posY, entityIn.posZ);
        }*/

    public MutableBlockPosition setPos(double xIn, double yIn, double zIn) {
        return this.setPos(MathHelper.floorDouble(xIn), MathHelper.floorDouble(yIn), MathHelper.floorDouble(zIn));
    }

    public MutableBlockPosition setPos(BaseBlockPosition vec) {
        return this.setPos(vec.getX(), vec.getY(), vec.getZ());
    }

    public MutableBlockPosition move(Facing facing) {
        return this.move(facing, 1);
    }

    public MutableBlockPosition move(Facing facing, int p_189534_2_) {
        return this.setPos(this.x + facing.getFrontOffsetX() * p_189534_2_, this.y + facing.getFrontOffsetY() * p_189534_2_, this.z + facing.getFrontOffsetZ() * p_189534_2_);
    }

    public void setY(int yIn) {
        this.y = yIn;
    }

    public BlockPosition toImmutable() {
        return new BlockPosition(this);
    }
}
