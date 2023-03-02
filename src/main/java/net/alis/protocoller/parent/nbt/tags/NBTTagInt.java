package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTPrimitive;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTPrimitive {

    private int data;

    public NBTTagInt() { }

    public NBTTagInt(int data) {
        this.data = data;
    }

    @Override
    public void write(@NotNull DataOutput output) throws IOException {
        output.writeInt(this.data);
    }

    @Override
    public void read(@NotNull DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(96L);
        this.data = input.readInt();
    }

    @Override
    public byte getId() {
        return (byte)3;
    }

    @Override
    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTTagInt copy() {
        return new NBTTagInt(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            NBTTagInt nbttagint = (NBTTagInt)obj;
            return this.data == nbttagint.data;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    @Override
    public long getLong() {
        return this.data;
    }

    @Override
    public int getInt() {
        return this.data;
    }

    @Override
    public short getShort() {
        return (short)(this.data & 65535);
    }

    @Override
    public byte getByte() {
        return (byte)(this.data & 255);
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
