package net.alis.protocoller.samples.nbt.tags;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.nbt.NBTPrimitive;
import net.alis.protocoller.samples.nbt.NBTSizeTracker;
import net.alis.protocoller.samples.util.MathHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTPrimitive {
    
    private double data;

    public NBTTagDouble() {}

    public NBTTagDouble(double data) {
        this.data = data;
    }

    
    public void write(DataOutput output) throws IOException {
        output.writeDouble(this.data);
    }

    public void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(128L);
        this.data = input.readDouble();
    }
    
    public byte getId() {
        return 6;
    }

    public String toString() {
        return this.data + "d";
    }
    
    public NBTTagDouble copy() {
        return new NBTTagDouble(this.data);
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((NBTTagDouble)obj).data;
    }

    public int hashCode() {
        long i = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(i ^ i >>> 32);
    }

    public long getLong() {
        return (long)Math.floor(this.data);
    }

    public int getInt() {
        return MathHelper.floor(this.data);
    }

    public short getShort() {
        return (short)(MathHelper.floor(this.data) & 65535);
    }

    public byte getByte() {
        return (byte)(MathHelper.floor(this.data) & 255);
    }

    public double getDouble() {
        return this.data;
    }

    public float getFloat() {
        return (float)this.data;
    }

    @Override
    public Object toOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, Double.TYPE),
                this.data
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getNbtTagDoubleClass();
    }
}
