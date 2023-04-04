package net.alis.protocoller.samples.phys;

import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.util.Direction;
import org.bukkit.entity.Entity;

public class RayTraceResult {
    protected BlockPosition position;
    protected RayTraceResult.Type typeOfHit;
    protected Direction direction;
    protected Vector3D vector;
    protected Entity entityHit;

    public RayTraceResult() { }

    public RayTraceResult(Vector3D hitVecIn, Direction sideHitIn, BlockPosition blockPosIn) {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, blockPosIn);
    }

    public RayTraceResult(Vector3D hitVecIn, Direction sideHitIn) {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, BlockPosition.ZERO);
    }

    public RayTraceResult(Entity entityIn) {
        this(entityIn, new Vector3D(entityIn.getLocation().getX(), entityIn.getLocation().getY(), entityIn.getLocation().getZ()));
    }

    public RayTraceResult(RayTraceResult.Type typeIn, Vector3D hitVecIn, Direction sideHitIn, BlockPosition blockPosIn) {
        this.typeOfHit = typeIn;
        this.position = blockPosIn;
        this.direction = sideHitIn;
        this.vector = new Vector3D(hitVecIn.x, hitVecIn.y, hitVecIn.z);
    }

    public RayTraceResult(Entity entityHitIn, Vector3D hitVecIn) {
        this.typeOfHit = RayTraceResult.Type.ENTITY;
        this.entityHit = entityHitIn;
        this.vector = hitVecIn;
    }

    public BlockPosition getBlockPos() {
        return this.position;
    }

    public String toString() {
        return "HitResult{type=" + this.typeOfHit + ", BlockPosition=" + this.position + ", f=" + this.direction + ", pos=" + this.vector + ", entity=" + this.entityHit + '}';
    }

    public enum Type {
        MISS(0),
        BLOCK(1),
        ENTITY(2);

        private final int id;

        Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Type fromBoolean(boolean missed) {
            return missed ? MISS : BLOCK;
        }
    }
}
