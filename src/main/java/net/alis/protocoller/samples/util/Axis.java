package net.alis.protocoller.samples.util;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;

public enum Axis {

    X("x") {
        public int choose(int x, int y, int z) {
            return x;
        }

        public double choose(double x, double y, double z) {
            return x;
        }
    },
    Y("y") {
        public int choose(int x, int y, int z) {
            return y;
        }

        public double choose(double x, double y, double z) {
            return y;
        }
    },
    Z("z") {
        public int choose(int x, int y, int z) {
            return z;
        }

        public double choose(double x, double y, double z) {
            return z;
        }
    };

    public static final Axis[] VALUES = values();
    private final String name;

    Axis(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isVertical() {
        return this == Y;
    }

    public boolean isHorizontal() {
        return this == X || this == Z;
    }

    public String toString() {
        return this.name;
    }

    public Plane getPlane() {
        Plane direction$plane;
        switch (this) {
            case X:
            case Z:
                direction$plane = Plane.HORIZONTAL;
                break;
            case Y:
                direction$plane = Plane.VERTICAL;
                break;
            default:
                return ExceptionBuilder.throwException(new IncompatibleClassChangeError(), true);
        }
        return direction$plane;
    }

    public String getSerializedName() {
        return this.name;
    }

    public abstract int choose(int x, int y, int z);

    public abstract double choose(double x, double y, double z);
}