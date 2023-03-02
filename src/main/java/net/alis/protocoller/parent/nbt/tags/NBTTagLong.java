package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTPrimitive;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTPrimitive {

    private long data;

    public NBTTagLong() {}

    public NBTTagLong(long data) {
        this.data = data;
    }

    @Override
    public void write(@NotNull DataOutput output) throws IOException {
        output.writeLong(this.data);
    }

    @Override
    public void read(@NotNull DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(128L);
        this.data = input.readLong();
    }

    @Override
    public byte getId() {
        return (byte)4;
    }

    @Override
    public String toString() {
        return "" + this.data + "L";
    }

    @Override
    public NBTTagLong copy() {
        return new NBTTagLong(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            NBTTagLong nbttaglong = (NBTTagLong)obj;
            return this.data == nbttaglong.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }

    @Override
    public long getLong() {
        return this.data;
    }

    @Override
    public int getInt() {
        return (int) this.data;
    }

    @Override
    public short getShort() {
        return (short)((int)(this.data & 65535L));
    }

    @Override
    public byte getByte() {
        return (byte)((int)(this.data & 255L));
    }

    @Override
    public double getDouble() {
        return (double)this.data;
    }

    @Override
    public float getFloat() {
        return (float)this.data;
    }
}
