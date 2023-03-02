package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTPrimitive;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import net.alis.protocoller.parent.util.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTPrimitive {

    private float data;

    public NBTTagFloat() {}

    public NBTTagFloat(float data){
        this.data = data;
    }

    @Override
    public void write(@NotNull DataOutput output) throws IOException {
        output.writeFloat(this.data);
    }

    @Override
    public void read(@NotNull DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(96L);
        this.data = input.readFloat();
    }

    @Override
    public byte getId() {
        return (byte)5;
    }

    @Override
    public String toString() {
        return "" + this.data + "f";
    }

    @Override
    public NBTTagFloat copy() {
        return new NBTTagFloat(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            NBTTagFloat nbttagfloat = (NBTTagFloat)obj;
            return this.data == nbttagfloat.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    @Override
    public long getLong() {
        return (long)this.data;
    }

    @Override
    public int getInt() {
        return MathHelper.floorFloat(this.data);
    }

    @Override
    public short getShort() {
        return (short)(MathHelper.floorFloat(this.data) & 65535);
    }

    @Override
    public byte getByte() {
        return (byte)(MathHelper.floorFloat(this.data) & 255);
    }

    @Override
    public double getDouble() {
        return this.data;
    }

    @Override
    public float getFloat() {
        return this.data;
    }
}
