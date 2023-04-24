package net.alis.protocoller.samples.phys;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.util.Direction;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

public class MovingObjectPositionBlock extends RayTraceResult implements ObjectSample {
    private boolean missed;
    private boolean insideBlock;

    public MovingObjectPositionBlock(Vector3D pos, Direction side, BlockPosition blockPos, boolean insideBlock) {
        this(false, pos, side, blockPos, insideBlock);
    }

    public MovingObjectPositionBlock(boolean missed, Vector3D pos, Direction side, BlockPosition blockPos, boolean insideBlock) {
        super(Type.fromBoolean(missed), pos, side, blockPos);
        this.missed = missed;
        this.direction = side;
        this.position = blockPos;
        this.insideBlock = insideBlock;
    }

    public MovingObjectPositionBlock(Object originalObject) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject accessor = new AccessedObject(originalObject);
        this.vector = new Vector3D(accessor.readField(0, Vector3D.clazz()));
        this.position = new BlockPosition(new BaseBlockPosition(accessor.readField(0, BlockPosition.clazz()), false));
        this.direction = Direction.getById(((Enum<?>)accessor.readField(0, Direction.clazz())).ordinal());
        this.missed = accessor.readField(0, Boolean.TYPE);
        this.insideBlock = accessor.readField(1, Boolean.TYPE);
    }

    public MovingObjectPositionBlock changeFacing(Direction side) {
        return new MovingObjectPositionBlock(this.missed, super.vector, side, this.position, this.insideBlock);
    }

    public MovingObjectPositionBlock changePosition(BlockPosition blockPos) {
        return new MovingObjectPositionBlock(this.missed, super.vector, this.direction, blockPos, this.insideBlock);
    }

    public BlockPosition getPosition() {
        return this.position;
    }

    public Direction getFacing() {
        return this.direction;
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
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, Boolean.TYPE, Vector3D.clazz(), Direction.clazz(), BlockPosition.clazz(), Boolean.TYPE),
                this.missed, this.vector.createOriginal(), this.direction.original(), this.position.createOriginal(), this.insideBlock
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getMovingObjectPositionBlockClass();
    }
}

