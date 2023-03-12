package net.alis.protocoller.samples.phys;

public class Vector2F {
    public static final Vector2F ZERO = new Vector2F(0.0F, 0.0F);
    public static final Vector2F ONE = new Vector2F(1.0F, 1.0F);
    public static final Vector2F UNIT_X = new Vector2F(1.0F, 0.0F);
    public static final Vector2F NEGATIVE_UNIT_X = new Vector2F(-1.0F, 0.0F);
    public static final Vector2F UNIT_Y = new Vector2F(0.0F, 1.0F);
    public static final Vector2F NEGATIVE_UNIT_Y = new Vector2F(0.0F, -1.0F);
    public static final Vector2F MAX = new Vector2F(Float.MAX_VALUE, Float.MAX_VALUE);
    public static final Vector2F MIN = new Vector2F(Float.MIN_VALUE, Float.MIN_VALUE);
    public float x;
    public float y;

    public Vector2F(float xIn, float yIn) {
        this.x = xIn;
        this.y = yIn;
    }
}

