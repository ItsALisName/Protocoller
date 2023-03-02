package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTPrimitive;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTPrimitive {

    private byte data;

    public NBTTagByte() {}

    public NBTTagByte(byte data) {
        this.data = data;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(this.data);
    }

    @Override
    public void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(72L);
        this.data = input.readByte();
    }

    @Override
    public byte getId() {
        return (byte)1;
    }

    @Override
    public String toString() {
        return "" + this.data + "b";
    }

    @Override
    public NBTTagByte copy() {
        return new NBTTagByte(this.data);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            NBTTagByte nbttagbyte = (NBTTagByte)obj;
            return this.data == nbttagbyte.data;
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
        return this.data;
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
