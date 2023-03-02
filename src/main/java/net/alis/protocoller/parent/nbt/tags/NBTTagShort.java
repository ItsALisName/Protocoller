package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTPrimitive;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTPrimitive {
    private short data;

    public NBTTagShort() {}

    public NBTTagShort(short data) {
        this.data = data;
    }

    @Override
    public void write(@NotNull DataOutput output) throws IOException {
        output.writeShort(this.data);
    }

    @Override
    public void read(@NotNull DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(80L);
        this.data = input.readShort();
    }

    @Override
    public byte getId() {
        return (byte)2;
    }

    @Override
    public String toString() {
        return "" + this.data + "s";
    }

    @Override
    public NBTTagShort copy() {
        return new NBTTagShort(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            NBTTagShort nbttagshort = (NBTTagShort) obj;
            return this.data == nbttagshort.data;
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
        return this.data;
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
        return this.data;
    }
}
