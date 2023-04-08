package net.alis.protocoller.samples.nbt.tags;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.nbt.NBTPrimitive;
import net.alis.protocoller.samples.nbt.NBTSizeTracker;
import net.alis.protocoller.samples.util.MathHelper;
import org.jetbrains.annotations.Contract;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTPrimitive {
    private float data;

    public NBTTagFloat() {}

    public NBTTagFloat(float data) {
        this.data = data;
    }

    public void write(DataOutput output) throws IOException {
        output.writeFloat(this.data);
    }

    public void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(96L);
        this.data = input.readFloat();
    }

    public byte getId() {
        return 5;
    }

    public String toString() {
        return this.data + "f";
    }

    public NBTTagFloat copy() {
        return new NBTTagFloat(this.data);
    }

    @Contract(value = "null -> false", pure = true)
    public boolean equals(Object obj) {
        return super.equals(obj) && this.data == ((NBTTagFloat)obj).data;
    }

    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    public long getLong() {
        return (long)this.data;
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
        return (double)this.data;
    }

    public float getFloat() {
        return this.data;
    }

    @Override
    public Object toOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassesContainer.get().getNbtTagFloatClass(), Float.TYPE),
                this.data
        );
    }
}
