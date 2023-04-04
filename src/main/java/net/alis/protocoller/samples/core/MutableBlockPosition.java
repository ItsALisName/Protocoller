package net.alis.protocoller.samples.core;

import net.alis.protocoller.samples.phys.BaseBlockPosition;
import net.alis.protocoller.samples.util.Direction;
import net.alis.protocoller.samples.util.MathHelper;

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
        return this.setPos(MathHelper.floor(xIn), MathHelper.floor(yIn), MathHelper.floor(zIn));
    }

    public MutableBlockPosition setPos(BaseBlockPosition vec) {
        return this.setPos(vec.getX(), vec.getY(), vec.getZ());
    }

    public MutableBlockPosition move(Direction direction) {
        return this.move(direction, 1);
    }

    public MutableBlockPosition move(Direction direction, int i) {
        return this.setPos(this.x + direction.getStepX() * i, this.y + direction.getStepY() * i, this.z + direction.getStepZ() * i);
    }

    public void setY(int yIn) {
        this.y = yIn;
    }

    public BlockPosition toImmutable() {
        return new BlockPosition(this);
    }
}
