package net.alis.protocoller.parent.util;

import com.google.common.collect.Iterators;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Predicate;

public enum Plane implements Predicate<Facing>, Iterable<Facing> {
    HORIZONTAL,
    VERTICAL;

    public Facing[] facings() {
        switch (this) {
            case HORIZONTAL:
                return new Facing[] {Facing.NORTH, Facing.EAST, Facing.SOUTH, Facing.WEST};
            case VERTICAL:
                return new Facing[] {Facing.UP, Facing.DOWN};
            default:
                throw new Error("Someone\'s been tampering with the universe!");
        }
    }

    public Facing random(Random rand) {
        Facing[] aFacing = this.facings();
        return aFacing[rand.nextInt(aFacing.length)];
    }

    public boolean apply(@Nullable Facing f) {
        return f != null && f.getAxis().getPlane() == this;
    }

    public @NotNull Iterator<Facing> iterator() {
        return Iterators.forArray(this.facings());
    }

    @Override
    public boolean test(Facing facing) {
        return false;
    }
}
