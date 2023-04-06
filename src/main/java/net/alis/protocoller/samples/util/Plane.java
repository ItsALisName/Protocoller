package net.alis.protocoller.samples.util;

import com.google.common.collect.Iterators;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Predicate;

public enum Plane implements Predicate<Direction>, Iterable<Direction> {
    HORIZONTAL,
    VERTICAL;

    @Contract(value = " -> new", pure = true)
    public Direction @NotNull [] facings() {
        switch (this) {
            case HORIZONTAL:
                return new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            case VERTICAL:
                return new Direction[] {Direction.UP, Direction.DOWN};
            default:
                return ExceptionBuilder.throwException(new Error("Someone's been tampering with the universe!"), true);
        }
    }

    public Direction random(@NotNull Random rand) {
        Direction[] aDirection = this.facings();
        return aDirection[rand.nextInt(aDirection.length)];
    }

    public boolean apply(@Nullable Direction f) {
        return f != null && f.getAxis().getPlane() == this;
    }

    public @NotNull Iterator<Direction> iterator() {
        return Iterators.forArray(this.facings());
    }

    @Override
    public boolean test(Direction direction) {
        return false;
    }
}
