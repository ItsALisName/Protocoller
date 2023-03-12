package net.alis.protocoller.samples.phys;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.util.Facing;
import net.alis.protocoller.util.CopiedObject;
import net.alis.protocoller.util.ObjectAccessor;

public class MovingObjectPositionBlock extends RayTraceResult implements CopiedObject {
    private boolean missed;
    private boolean insideBlock;

    public MovingObjectPositionBlock(Vector3D pos, Facing side, BlockPosition blockPos, boolean insideBlock) {
        this(false, pos, side, blockPos, insideBlock);
    }

    public MovingObjectPositionBlock(boolean missed, Vector3D pos, Facing side, BlockPosition blockPos, boolean insideBlock) {
        super(Type.fromBoolean(missed), pos, side, blockPos);
        this.missed = missed;
        this.facing = side;
        this.position = blockPos;
        this.insideBlock = insideBlock;
    }

    public MovingObjectPositionBlock(Object originalObject) {
        ObjectAccessor accessor = new ObjectAccessor(originalObject);
        this.vector = new Vector3D(accessor.read(0, ClassesContainer.INSTANCE.getVector3dClass()));
        this.position = new BlockPosition(new BaseBlockPosition(accessor.read(0, ClassesContainer.INSTANCE.getBlockPositionClass()), false));
        this.facing = Facing.getById(((Enum<?>)accessor.read(0, ClassesContainer.INSTANCE.getDirectionEnum())).ordinal());
        this.missed = accessor.read(0, Boolean.TYPE);
        this.insideBlock = accessor.read(1, Boolean.TYPE);
    }

    public MovingObjectPositionBlock changeFacing(Facing side) {
        return new MovingObjectPositionBlock(this.missed, super.vector, side, this.position, this.insideBlock);
    }

    public MovingObjectPositionBlock changePosition(BlockPosition blockPos) {
        return new MovingObjectPositionBlock(this.missed, super.vector, this.facing, blockPos, this.insideBlock);
    }

    public BlockPosition getPosition() {
        return this.position;
    }

    public Facing getFacing() {
        return this.facing;
    }

    public Type getMovingObjectType() {
        return this.missed ? Type.MISS : Type.BLOCK;
    }

    public boolean isMissed() {
        return missed;
    }

    public void setMissed(boolean missed) {
        this.missed = missed;
    }

    public void setInsideBlock(boolean insideBlock) {
        this.insideBlock = insideBlock;
    }

    public boolean isInsideBlock() {
        return this.insideBlock;
    }

    @Override
    public Object createOriginal() {
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getMovingObjectPositionBlockClass(), Boolean.TYPE, ClassesContainer.INSTANCE.getVector3dClass(), ClassesContainer.INSTANCE.getDirectionEnum(), ClassesContainer.INSTANCE.getBlockPositionClass(), Boolean.TYPE),
                this.missed, this.vector.createOriginal(), this.facing.original(), this.position.createOriginal(), this.insideBlock
        );
    }
}

