package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTPrimitive;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import net.alis.protocoller.parent.util.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTPrimitive {

    private double data;
    public NBTTagDouble() { }

    public NBTTagDouble(double data) {
        this.data = data;
    }

    public void write(@NotNull DataOutput output) throws IOException {
        output.writeDouble(this.data);
    }

    public void read(@NotNull DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(128L);
        this.data = input.readDouble();
    }

    @Override
    public byte getId() {
        return (byte)6;
    }

    @Override
    public String toString() {
        return "" + this.data + "d";
    }

    @Override
    public NBTTagDouble copy() {
        return new NBTTagDouble(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            NBTTagDouble nbttagdouble = (NBTTagDouble)obj;
            return this.data == nbttagdouble.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        long i = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(i ^ i >>> 32);
    }

    @Override
    public long getLong() {
        return (long)Math.floor(this.data);
    }

    @Override
    public int getInt() {
        return MathHelper.floorDouble(this.data);
    }

    @Override
    public short getShort() {
        return (short)(MathHelper.floorDouble(this.data) & 65535);
    }

    @Override
    public byte getByte() {
        return (byte)(MathHelper.floorDouble(this.data) & 255);
    }

    @Override
    public double getDouble() {
        return this.data;
    }

    @Override
    public float getFloat() {
        return (float)this.data;
    }
}
