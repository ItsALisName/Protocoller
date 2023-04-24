package net.alis.protocoller.samples.phys;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.util.MathHelper;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.NotNull;

public class Vector2F implements ObjectSample {
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

    public Vector2F(Object vec2f) {
        AccessedObject object = new AccessedObject(vec2f, true);
        this.x = object.readField(0, float.class);
        this.y = object.readField(1, float.class);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2F set(float d) {
        this.x = d;
        this.y = d;
        return this;
    }

    public Vector2F set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2F set(double d) {
        this.x = (float) d;
        this.y = (float) d;
        return this;
    }

    public Vector2F set(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
        return this;
    }

    public Vector2F set(@NotNull Vector2F v) {
        this.x = (float) v.x;
        this.y = (float) v.y;
        return this;
    }

    public Vector2F set(float @NotNull [] xy) {
        this.x = xy[0];
        this.y = xy[1];
        return this;
    }

    public float get(int component) throws IllegalArgumentException {
        switch (component) {
            case 0:
                return x;
            case 1:
                return y;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Vector2F setComponent(int component, float value) throws IllegalArgumentException {
        switch (component) {
            case 0:
                x = value;
                break;
            case 1:
                y = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

    public Vector2F perpendicular() {
        float xTemp = y;
        this.y = this.x * -1;
        this.x = xTemp;
        return this;
    }

    public Vector2F sub(float x, float y) {
        this.x = this.x - x;
        this.y = this.y - y;
        return this;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public static float lengthSquared(float x, float y) {
        return x * x + y * y;
    }

    public float length() {
        return MathHelper.sqrt(x * x + y * y);
    }

    public static float length(float x, float y) {
        return MathHelper.sqrt(x * x + y * y);
    }

    public float distance(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return MathHelper.sqrt(dx * dx + dy * dy);
    }

    public float distanceSquared(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return MathHelper.sqrt(dx * dx + dy * dy);
    }


    public static float distanceSquared(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    public Vector2F normalize() {
        float invLength = MathHelper.invsqrt(x * x + y * y);
        this.x = x * invLength;
        this.y = y * invLength;
        return this;
    }

    public Vector2F normalize(@NotNull Vector2F dest) {
        float invLength = MathHelper.invsqrt(x * x + y * y);
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    public Vector2F normalize(float length) {
        float invLength = MathHelper.invsqrt(x * x + y * y) * length;
        this.x = x * invLength;
        this.y = y * invLength;
        return this;
    }

    public Vector2F normalize(float length, @NotNull Vector2F dest) {
        float invLength = MathHelper.invsqrt(x * x + y * y) * length;
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    public Vector2F add(float x, float y) {
        return add(x, y, this);
    }

    public Vector2F add(float x, float y, @NotNull Vector2F dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        return dest;
    }

    public Vector2F zero() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    public Vector2F negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }

    public Vector2F negate(Vector2F dest) {
        dest.x = -x;
        dest.y = -y;
        return dest;
    }

    public Vector2F mul(float scalar) {
        this.x = x * scalar;
        this.y = y * scalar;
        return this;
    }

    public Vector2F mul(float scalar, @NotNull Vector2F dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        return dest;
    }

    public Vector2F mul(float x, float y) {
        this.x = this.x * x;
        this.y = this.y * y;
        return this;
    }

    public Vector2F mul(float x, float y, @NotNull Vector2F dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        return dest;
    }

    public Vector2F div(float scalar) {
        float inv = 1.0f / scalar;
        this.x = this.x * inv;
        this.y = this.y * inv;
        return this;
    }

    public Vector2F div(float scalar, @NotNull Vector2F dest) {
        float inv = 1.0f / scalar;
        dest.x = this.x * inv;
        dest.y = this.y * inv;
        return dest;
    }

    public Vector2F div(float x, float y) {
        this.x = this.x / x;
        this.y = this.y / y;
        return this;
    }

    public Vector2F div(float x, float y, @NotNull Vector2F dest) {
        dest.x = this.x / x;
        dest.y = this.y / y;
        return dest;
    }

    public int maxComponent() {
        float absX = MathHelper.abs(x);
        float absY = MathHelper.abs(y);
        if (absX >= absY)
            return 0;
        return 1;
    }

    public int minComponent() {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        if (absX < absY)
            return 0;
        return 1;
    }

    public Vector2F floor() {
        this.x = MathHelper.floor(x);
        this.y = MathHelper.floor(y);
        return this;
    }

    public Vector2F floor(Vector2F dest) {
        dest.x = MathHelper.floor(x);
        dest.y = MathHelper.floor(y);
        return dest;
    }

    public Vector2F ceil() {
        this.x = MathHelper.ceil(x);
        this.y = MathHelper.ceil(y);
        return this;
    }

    public Vector2F ceil(Vector2F dest) {
        dest.x = MathHelper.ceil(x);
        dest.y = MathHelper.ceil(y);
        return dest;
    }

    public Vector2F round() {
        this.x = MathHelper.round(x);
        this.y = MathHelper.round(y);
        return this;
    }

    public Vector2F round(@NotNull Vector2F dest) {
        dest.x = MathHelper.round(x);
        dest.y = MathHelper.round(y);
        return dest;
    }

    public Vector2F absolute() {
        this.x = MathHelper.abs(this.x);
        this.y = MathHelper.abs(this.y);
        return this;
    }

    public Vector2F absolute(@NotNull Vector2F dest) {
        dest.x = MathHelper.abs(this.x);
        dest.y = MathHelper.abs(this.y);
        return dest;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, float.class, float.class),
                x, y
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getVector2FClass();
    }

}

