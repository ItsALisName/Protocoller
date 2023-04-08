package net.alis.protocoller.samples.nbt.tags;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.nbt.NBTPrimitive;
import net.alis.protocoller.samples.nbt.NBTSizeTracker;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTPrimitive {

    private long data;

    public NBTTagLong() {}

    public NBTTagLong(long data) {
        this.data = data;
    }

    public void write(DataOutput output) throws IOException {
        output.writeLong(this.data);
    }

    public void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(128L);
        this.data = input.readLong();
    }

    public byte getId() {
        return 4;
    }

    public String toString() {
        return this.data + "L";
    }

    public NBTTagLong copy() {
        return new NBTTagLong(this.data);
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((NBTTagLong)obj).data;
    }

    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }

    public long getLong() {
        return this.data;
    }

    public int getInt() {
        return (int)(this.data);
    }

    public short getShort() {
        return (short)((int)(this.data & 65535L));
    }

    public byte getByte() {
        return (byte)((int)(this.data & 255L));
    }

    public double getDouble() {
        return (double)this.data;
    }

    public float getFloat() {
        return (float)this.data;
    }

    @Override
    public Object toOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassesContainer.get().getNbtTagLongClass(), Long.TYPE),
                this.data
        );
    }
}
