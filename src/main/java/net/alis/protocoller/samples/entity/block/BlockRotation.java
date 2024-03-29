package net.alis.protocoller.samples.entity.block;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.util.DirectionTransformationO;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum BlockRotation {
    NONE(0, "NONE", DirectionTransformationO.IDENTITY),
    CLOCKWISE_90(1, "CLOCKWISE_90", DirectionTransformationO.ROT_90_Y_NEG),
    CLOCKWISE_180(2, "CLOCKWISE_180", DirectionTransformationO.ROT_180_FACE_XZ),
    COUNTERCLOCKWISE_90(3, "COUNTER_CLOCKWISE_90", DirectionTransformationO.ROT_90_Y_POS);

    private final int id;
    private final String name;
    private final DirectionTransformationO directionTransformation;

    BlockRotation(int id, String name, DirectionTransformationO var2) {
        this.id = id;
        this.name = name;
        this.directionTransformation = var2;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DirectionTransformationO getDirectionTransformation() {
        return this.directionTransformation;
    }

    @Contract(pure = true)
    public static @Nullable BlockRotation getById(int id) {
        Utils.checkClassSupportability(clazz(), "BlockRotation", false);
        for(BlockRotation rotation : values()) {
            if(rotation.id == id) return rotation;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), "BlockRotation", false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getBlockRotationEnum();
    }
}
