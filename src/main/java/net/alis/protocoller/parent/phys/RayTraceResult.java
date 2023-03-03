package net.alis.protocoller.parent.phys;

import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.util.Facing;

public class RayTraceResult {
    private BlockPosition blockPos;
    public RayTraceResult.Type typeOfHit;
    public Facing sideHit;
    public Vector3D hitVec;
    public Entity entityHit;

    public RayTraceResult(Vector3D hitVecIn, Facing sideHitIn, BlockPosition blockPosIn) {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, blockPosIn);
    }

    public RayTraceResult(Vector3D hitVecIn, Facing sideHitIn) {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, BlockPosition.ORIGIN);
    }

    public RayTraceResult(Entity entityIn) {
        this(entityIn, new Vector3D(entityIn.posX, entityIn.posY, entityIn.posZ));
    }

    public RayTraceResult(RayTraceResult.Type typeIn, Vector3D hitVecIn, Facing sideHitIn, BlockPosition blockPosIn) {
        this.typeOfHit = typeIn;
        this.blockPos = blockPosIn;
        this.sideHit = sideHitIn;
        this.hitVec = new Vector3D(hitVecIn.x, hitVecIn.y, hitVecIn.z);
    }

    public RayTraceResult(Entity entityHitIn, Vector3D hitVecIn) {
        this.typeOfHit = RayTraceResult.Type.ENTITY;
        this.entityHit = entityHitIn;
        this.hitVec = hitVecIn;
    }

    public BlockPosition getBlockPos() {
        return this.blockPos;
    }

    public String toString() {
        return "HitResult{type=" + this.typeOfHit + ", BlockPosition=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }

    public enum Type {
        MISS,
        BLOCK,
        ENTITY;
    }
}
