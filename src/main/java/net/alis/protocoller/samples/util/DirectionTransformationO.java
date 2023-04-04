package net.alis.protocoller.samples.util;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;

public enum DirectionTransformationO {
    IDENTITY("identity", DirectionTransformationS.P123, false, false, false),
    ROT_180_FACE_XY("rot_180_face_xy", DirectionTransformationS.P123, true, true, false),
    ROT_180_FACE_XZ("rot_180_face_xz", DirectionTransformationS.P123, true, false, true),
    ROT_180_FACE_YZ("rot_180_face_yz", DirectionTransformationS.P123, false, true, true),
    ROT_120_NNN("rot_120_nnn", DirectionTransformationS.P231, false, false, false),
    ROT_120_NNP("rot_120_nnp", DirectionTransformationS.P312, true, false, true),
    ROT_120_NPN("rot_120_npn", DirectionTransformationS.P312, false, true, true),
    ROT_120_NPP("rot_120_npp", DirectionTransformationS.P231, true, false, true),
    ROT_120_PNN("rot_120_pnn", DirectionTransformationS.P312, true, true, false),
    ROT_120_PNP("rot_120_pnp", DirectionTransformationS.P231, true, true, false),
    ROT_120_PPN("rot_120_ppn", DirectionTransformationS.P231, false, true, true),
    ROT_120_PPP("rot_120_ppp", DirectionTransformationS.P312, false, false, false),
    ROT_180_EDGE_XY_NEG("rot_180_edge_xy_neg", DirectionTransformationS.P213, true, true, true),
    ROT_180_EDGE_XY_POS("rot_180_edge_xy_pos", DirectionTransformationS.P213, false, false, true),
    ROT_180_EDGE_XZ_NEG("rot_180_edge_xz_neg", DirectionTransformationS.P321, true, true, true),
    ROT_180_EDGE_XZ_POS("rot_180_edge_xz_pos", DirectionTransformationS.P321, false, true, false),
    ROT_180_EDGE_YZ_NEG("rot_180_edge_yz_neg", DirectionTransformationS.P132, true, true, true),
    ROT_180_EDGE_YZ_POS("rot_180_edge_yz_pos", DirectionTransformationS.P132, true, false, false),
    ROT_90_X_NEG("rot_90_x_neg", DirectionTransformationS.P132, false, false, true),
    ROT_90_X_POS("rot_90_x_pos", DirectionTransformationS.P132, false, true, false),
    ROT_90_Y_NEG("rot_90_y_neg", DirectionTransformationS.P321, true, false, false),
    ROT_90_Y_POS("rot_90_y_pos", DirectionTransformationS.P321, false, false, true),
    ROT_90_Z_NEG("rot_90_z_neg", DirectionTransformationS.P213, false, true, false),
    ROT_90_Z_POS("rot_90_z_pos", DirectionTransformationS.P213, true, false, false),
    INVERSION("inversion", DirectionTransformationS.P123, true, true, true),
    INVERT_X("invert_x", DirectionTransformationS.P123, true, false, false),
    INVERT_Y("invert_y", DirectionTransformationS.P123, false, true, false),
    INVERT_Z("invert_z", DirectionTransformationS.P123, false, false, true),
    ROT_60_REF_NNN("rot_60_ref_nnn", DirectionTransformationS.P312, true, true, true),
    ROT_60_REF_NNP("rot_60_ref_nnp", DirectionTransformationS.P231, true, false, false),
    ROT_60_REF_NPN("rot_60_ref_npn", DirectionTransformationS.P231, false, false, true),
    ROT_60_REF_NPP("rot_60_ref_npp", DirectionTransformationS.P312, false, false, true),
    ROT_60_REF_PNN("rot_60_ref_pnn", DirectionTransformationS.P231, false, true, false),
    ROT_60_REF_PNP("rot_60_ref_pnp", DirectionTransformationS.P312, true, false, false),
    ROT_60_REF_PPN("rot_60_ref_ppn", DirectionTransformationS.P312, false, true, false),
    ROT_60_REF_PPP("rot_60_ref_ppp", DirectionTransformationS.P231, true, true, true),
    SWAP_XY("swap_xy", DirectionTransformationS.P213, false, false, false),
    SWAP_YZ("swap_yz", DirectionTransformationS.P132, false, false, false),
    SWAP_XZ("swap_xz", DirectionTransformationS.P321, false, false, false),
    SWAP_NEG_XY("swap_neg_xy", DirectionTransformationS.P213, true, true, false),
    SWAP_NEG_YZ("swap_neg_yz", DirectionTransformationS.P132, false, true, true),
    SWAP_NEG_XZ("swap_neg_xz", DirectionTransformationS.P321, true, false, true),
    ROT_90_REF_X_NEG("rot_90_ref_x_neg", DirectionTransformationS.P132, true, false, true),
    ROT_90_REF_X_POS("rot_90_ref_x_pos", DirectionTransformationS.P132, true, true, false),
    ROT_90_REF_Y_NEG("rot_90_ref_y_neg", DirectionTransformationS.P321, true, true, false),
    ROT_90_REF_Y_POS("rot_90_ref_y_pos", DirectionTransformationS.P321, false, true, true),
    ROT_90_REF_Z_NEG("rot_90_ref_z_neg", DirectionTransformationS.P213, false, true, true),
    ROT_90_REF_Z_POS("rot_90_ref_z_pos", DirectionTransformationS.P213, true, false, true);

    private final String name;
    private final boolean flipX;
    private final boolean flipY;
    private final boolean flipZ;
    private final DirectionTransformationS pointS;

    DirectionTransformationO(String name, DirectionTransformationS axisTransformation, boolean flipX, boolean flipY, boolean flipZ) {
        this.name = name;
        this.flipX = flipX;
        this.flipY = flipY;
        this.flipZ = flipZ;
        this.pointS = axisTransformation;
    }

    public String getName() {
        return name;
    }

    public boolean isFlipX() {
        return flipX;
    }

    public boolean isFlipY() {
        return flipY;
    }

    public boolean isFlipZ() {
        return flipZ;
    }

    public DirectionTransformationS getPointS() {
        return pointS;
    }

    public static DirectionTransformationO getById(int id) {
        for(DirectionTransformationO dt : values()) {
            if(dt.ordinal() == id) return dt;
        }
        return null;
    }

    public Enum<?> original() {
        return BaseReflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getPointOEnum(), this.ordinal());
    }
}
