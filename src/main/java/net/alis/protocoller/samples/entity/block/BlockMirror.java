package net.alis.protocoller.samples.entity.block;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.util.Axis;
import net.alis.protocoller.samples.util.Facing;
import net.alis.protocoller.samples.util.DirectionTransformationO;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum BlockMirror {
    NONE(0, "NONE", DirectionTransformationO.IDENTITY),
    LEFT_RIGHT(1, "LEFT_CLICK", DirectionTransformationO.INVERT_Z),
    FRONT_BACK(2, "RIGHT_CLICK", DirectionTransformationO.INVERT_X);

    private final int id;
    private final String name;
    private final DirectionTransformationO transformationO;

    BlockMirror(int id, String name, DirectionTransformationO directionTransformation) {
        this.id = id;
        this.name = name;
        this.transformationO = directionTransformation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Contract(pure = true)
    public BlockRotation rotationFromFacing(@NotNull Facing facing) {
        Axis var1 = facing.getAxis();
        return ((this == LEFT_RIGHT && var1 == Axis.Z) || (this == FRONT_BACK && var1 == Axis.X)) ? BlockRotation.CLOCKWISE_180 : BlockRotation.NONE;
    }

    public DirectionTransformationO getTransformation() {
        return this.transformationO;
    }

    @Contract(pure = true)
    public static @Nullable BlockMirror getById(int id) {
        for(BlockMirror mirror : values()) {
            if(mirror.id == id) return mirror;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getBlockMirrorEnum(), this.id);
    }
}

