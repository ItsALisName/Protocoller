package net.alis.protocoller.parent.util;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Predicate;

public enum Axis implements Predicate<Facing> {
    X("x", Plane.HORIZONTAL),
    Y("y", Plane.VERTICAL),
    Z("z", Plane.HORIZONTAL);

    private static final Map<String, Axis> NAME_LOOKUP = Maps.newHashMap();
    private final String name;
    private final Plane plane;

    Axis(String name, Plane plane) {
        this.name = name;
        this.plane = plane;
    }

    @Nullable
    public static Axis byName(String name) {
        return name == null ? null : NAME_LOOKUP.get(name.toLowerCase());
    }

    public String getName2() {
        return this.name;
    }

    public boolean isVertical() {
        return this.plane == Plane.VERTICAL;
    }

    public boolean isHorizontal() {
        return this.plane == Plane.HORIZONTAL;
    }

    public String toString() {
        return this.name;
    }

    public boolean apply(@Nullable Facing facing) {
        return facing != null && facing.getAxis() == this;
    }

    public Plane getPlane() {
        return this.plane;
    }

    public String getName() {
        return this.name;
    }

    static {
        for (Axis Facing$axis : values()) {
            NAME_LOOKUP.put(Facing$axis.getName2().toLowerCase(), Facing$axis);
        }
    }

    @Override
    public boolean test(Facing facing) {
        return facing.getAxis().equals(this);
    }
}
