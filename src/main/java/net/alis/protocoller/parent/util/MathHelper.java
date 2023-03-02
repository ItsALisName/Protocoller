package net.alis.protocoller.parent.util;

import net.alis.protocoller.parent.phys.BaseBlockPosition;

import java.util.Random;
import java.util.UUID;

public class MathHelper {
    public static final float SQRT_2 = sqrt_float(2.0F);
    private static final int SIN_BITS = 12;
    private static final int SIN_MASK = 4095;
    private static final int SIN_COUNT = 4096;
    public static final float PI = (float)Math.PI;
    public static final float PI2 = ((float)Math.PI * 2F);
    public static final float PId2 = ((float)Math.PI / 2F);
    private static final float radFull = ((float)Math.PI * 2F);
    private static final float degFull = 360.0F;
    private static final float radToIndex = 651.8986F;
    private static final float degToIndex = 11.377778F;
    public static final float deg2Rad = 0.017453292F;
    private static final float[] SIN_TABLE_FAST = new float[4096];
    public static boolean fastMath = false;
    private static final float[] SIN_TABLE = new float[65536];
    private static final Random RANDOM = new Random();
    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION;
    private static final double FRAC_BIAS;
    private static final double[] ASINE_TAB;
    private static final double[] COS_TAB;

    
    public static float sin(float value) {
        return fastMath ? SIN_TABLE_FAST[(int)(value * 651.8986F) & 4095] : SIN_TABLE[(int)(value * 10430.378F) & 65535];
    }

    
    public static float cos(float value) {
        return fastMath ? SIN_TABLE_FAST[(int)((value + ((float)Math.PI / 2F)) * 651.8986F) & 4095] : SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 65535];
    }

    public static float sqrt_float(float value) {
        return (float)Math.sqrt((double)value);
    }

    public static float sqrt_double(double value) {
        return (float)Math.sqrt(value);
    }
    
    public static int floorFloat(float value) {
        int i = (int)value;
        return value < (float)i ? i - 1 : i;
    }
    
    public static int truncateDoubleToInt(double value) {
        return (int)(value + 1024.0D) - 1024;
    }
    
    public static int floorDouble(double value) {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }
    
    public static long floor_double_long(double value) {
        long i = (long)value;
        return value < (double)i ? i - 1L : i;
    }

    public static int absFloor(double value) {
        return (int)(value >= 0.0D ? value : -value + 1.0D);
    }

    public static float abs(float value) {
        return value >= 0.0F ? value : -value;
    }
    
    public static int abs_int(int value) {
        return value >= 0 ? value : -value;
    }

    public static int ceiling_float_int(float value) {
        int i = (int)value;
        return value > (float)i ? i + 1 : i;
    }

    public static int ceiling_double_int(double value) {
        int i = (int)value;
        return value > (double)i ? i + 1 : i;
    }
    
    public static int clamp_int(int num, int min, int max) {
        return num < min ? min : (num > max ? max : num);
    }
    
    public static float clamp_float(float num, float min, float max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static double clamp_double(double num, double min, double max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static double denormalizeClamp(double lowerBnd, double upperBnd, double slide) {
        return slide < 0.0D ? lowerBnd : (slide > 1.0D ? upperBnd : lowerBnd + (upperBnd - lowerBnd) * slide);
    }

    
    public static double abs_max(double d1, double d2) {
        if (d1 < 0.0D) d1 = -d1;
        if (d2 < 0.0D) d2 = -d2;
        return Math.max(d1, d2);
    }

    public static int bucketInt(int i1, int i2) {
        return i1 < 0 ? -((-i1 - 1) / i2) - 1 : i1 / i2;
    }

    public static int getRandomIntegerInRange(Random random, int minimum, int maximum) {
        return minimum >= maximum ? minimum : random.nextInt(maximum - minimum + 1) + minimum;
    }

    public static float randomFloatClamp(Random random, float minimum, float maximum) {
        return minimum >= maximum ? minimum : random.nextFloat() * (maximum - minimum) + minimum;
    }

    public static double getRandomDoubleInRange(Random random, double minimum, double maximum) {
        return minimum >= maximum ? minimum : random.nextDouble() * (maximum - minimum) + minimum;
    }

    public static double average(long[] values) {
        long i = 0L;
        for (long j : values) i += j;
        return (double)i / (double)values.length;
    }

    public static boolean epsilonEquals(float f1, float f2) {
        return abs(f2 - f1) < 1.0E-5F;
    }

    public static int normalizeAngle(int p_180184_0_, int p_180184_1_) {
        return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_;
    }

    public static float positiveModulo(float numerator, float denominator) {
        return (numerator % denominator + denominator) % denominator;
    }
    
    public static float wrapDegrees(float value) {
        value = value % 360.0F;
        if (value >= 180.0F) value -= 360.0F;
        if (value < -180.0F) value += 360.0F;
        return value;
    }
    
    public static double wrapDegrees(double value) {
        value = value % 360.0D;
        if (value >= 180.0D) value -= 360.0D;
        if (value < -180.0D) value += 360.0D;
        return value;
    }
    
    public static int clampAngle(int angle) {
        angle = angle % 360;
        if (angle >= 180) angle -= 360;
        if (angle < -180) angle += 360;
        return angle;
    }
    
    public static int parseIntWithDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Throwable var3) {
            return defaultValue;
        }
    }
    
    public static int parseIntWithDefaultAndMax(String value, int defaultValue, int max) {
        return Math.max(max, parseIntWithDefault(value, defaultValue));
    }
    
    public static double parseDoubleWithDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Throwable var4) {
            return defaultValue;
        }
    }

    public static double parseDoubleWithDefaultAndMax(String value, double defaultValue, double max) {
        return Math.max(max, parseDoubleWithDefault(value, defaultValue));
    }
    
    public static int roundUpToPowerOfTwo(int value) {
        int i = value - 1;
        i = i | i >> 1;
        i = i | i >> 2;
        i = i | i >> 4;
        i = i | i >> 8;
        i = i | i >> 16;
        return i + 1;
    }
    
    private static boolean isPowerOfTwo(int value) {
        return value != 0 && (value & value - 1) == 0;
    }
    
    public static int calculateLogBaseTwoDeBruijn(int value) {
        value = isPowerOfTwo(value) ? value : roundUpToPowerOfTwo(value);
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)((long)value * 125613361L >> 27) & 31];
    }
    
    public static int calculateLogBaseTwo(int value) {
        return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
    }
    
    public static int roundUp(int number, int interval) {
        if (interval == 0) return 0;
        else if (number == 0) return interval;
        else {
            if (number < 0) interval *= -1;
            int i = number % interval;
            return i == 0 ? number : number + interval - i;
        }
    }

    
    public static int rgb(float rIn, float gIn, float bIn) {
        return rgb(floorFloat(rIn * 255.0F), floorFloat(gIn * 255.0F), floorFloat(bIn * 255.0F));
    }
    
    public static int rgb(int rIn, int gIn, int bIn) {
        int i = (rIn << 8) + gIn;
        i = (i << 8) + bIn;
        return i;
    }

    public static int multiplyColor(int i1, int i2) {
        int i = (i1 & 16711680) >> 16;
        int j = (i2 & 16711680) >> 16;
        int k = (i1 & 65280) >> 8;
        int l = (i2 & 65280) >> 8;
        int i3 = (i1 & 255);
        int j1 = (i2 & 255);
        int k1 = (int)((float)i * (float)j / 255.0F);
        int l1 = (int)((float)k * (float)l / 255.0F);
        int i4 = (int)((float)i3 * (float)j1 / 255.0F);
        return i1 & -16777216 | k1 << 16 | l1 << 8 | i4;
    }
    
    public static double frac(double number) {
        return number - Math.floor(number);
    }

    public static long getPositionRandom(BaseBlockPosition pos) {
        return getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
    }

    public static long getCoordinateRandom(int x, int y, int z) {
        long i = (x * 3129871L) ^ (long)z * 116129781L ^ (long)y;
        i = i * i * 42317861L + i * 11L;
        return i;
    }

    public static UUID getRandomUuid(Random rand) {
        long i = rand.nextLong() & -61441L | 16384L;
        long j = rand.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
        return new UUID(i, j);
    }
    
    public static UUID getRandomUUID() {
        return getRandomUuid(RANDOM);
    }

    public static double pct(double d1, double d2, double d3) {
        return (d1 - d2) / (d3 - d2);
    }

    public static double atan2(double doub1, double doub2) {
        double d0 = doub2 * doub2 + doub1 * doub1;
        if (Double.isNaN(d0)) {
            return Double.NaN;
        } else {
            boolean flag = doub1 < 0.0D;
            if (flag) {
                doub1 = -doub1;
            }
            boolean flag1 = doub2 < 0.0D;
            if (flag1) {
                doub2 = -doub2;
            }
            boolean flag2 = doub1 > doub2;
            if (flag2) {
                double d1 = doub2;
                doub2 = doub1;
                doub1 = d1;
            }
            double d9 = fastInvSqrt(d0);
            doub2 = doub2 * d9;
            doub1 = doub1 * d9;
            double d2 = FRAC_BIAS + doub1;
            int i = (int)Double.doubleToRawLongBits(d2);
            double d3 = ASINE_TAB[i];
            double d4 = COS_TAB[i];
            double d5 = d2 - FRAC_BIAS;
            double d6 = doub1 * d4 - doub2 * d5;
            double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
            double d8 = d3 + d7;
            if (flag2) {
                d8 = (Math.PI / 2D) - d8;
            }
            if (flag1) {
                d8 = Math.PI - d8;
            }
            if (flag) {
                d8 = -d8;
            }
            return d8;
        }
    }

    public static double fastInvSqrt(double doub1) {
        double d0 = 0.5D * doub1;
        long i = Double.doubleToRawLongBits(doub1);
        i = 6910469410427058090L - (i >> 1);
        doub1 = Double.longBitsToDouble(i);
        doub1 = doub1 * (1.5D - d0 * doub1 * doub1);
        return doub1;
    }

    public static int hsvToRGB(float hue, float saturation, float value) {
        int i = (int)(hue * 6.0F) % 6;
        float f = hue * 6.0F - (float)i;
        float f1 = value * (1.0F - saturation);
        float f2 = value * (1.0F - f * saturation);
        float f3 = value * (1.0F - (1.0F - f) * saturation);
        float f4;
        float f5;
        float f6;
        switch (i) {
            case 0:
                f4 = value;
                f5 = f3;
                f6 = f1;
                break;
            case 1:
                f4 = f2;
                f5 = value;
                f6 = f1;
                break;
            case 2:
                f4 = f1;
                f5 = value;
                f6 = f3;
                break;
            case 3:
                f4 = f1;
                f5 = f2;
                f6 = value;
                break;
            case 4:
                f4 = f3;
                f5 = f1;
                f6 = value;
                break;
            case 5:
                f4 = value;
                f5 = f1;
                f6 = f2;
                break;

            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
        int j = clamp_int((int)(f4 * 255.0F), 0, 255);
        int k = clamp_int((int)(f5 * 255.0F), 0, 255);
        int l = clamp_int((int)(f6 * 255.0F), 0, 255);
        return j << 16 | k << 8 | l;
    }

    public static int getHash(int int1) {
        int1 = int1 ^ int1 >>> 16;
        int1 = int1 * -2048144789;
        int1 = int1 ^ int1 >>> 13;
        int1 = int1 * -1028477387;
        int1 = int1 ^ int1 >>> 16;
        return int1;
    }

    static {
        for (int i = 0; i < 65536; ++i) {
            SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 65536.0D);
        }
        for (int j = 0; j < 4096; ++j) {
            SIN_TABLE_FAST[j] = (float)Math.sin((double)(((float)j + 0.5F) / 4096.0F * ((float)Math.PI * 2F)));
        }
        for (int k = 0; k < 360; k += 90) {
            SIN_TABLE_FAST[(int)((float)k * 11.377778F) & 4095] = (float)Math.sin((double)((float)k * 0.017453292F));
        }

        MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[] {0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
        FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
        ASINE_TAB = new double[257];
        COS_TAB = new double[257];

        for (int l = 0; l < 257; ++l) {
            double d0 = (double)l / 256.0D;
            double d1 = Math.asin(d0);
            COS_TAB[l] = Math.cos(d1);
            ASINE_TAB[l] = d1;
        }
    }

}
