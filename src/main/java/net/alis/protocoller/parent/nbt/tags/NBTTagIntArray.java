package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTBase;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
    private int[] intArray;

    public NBTTagIntArray()  {}

    public NBTTagIntArray(int[] ints) {
        this.intArray = ints;
    }

    @Override
    public void write(@NotNull DataOutput output) throws IOException {
        output.writeInt(this.intArray.length);
        for (int i : this.intArray) {
            output.writeInt(i);
        }
    }

    @Override
    public void read(@NotNull DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(192L);
        int i = input.readInt();
        sizeTracker.read((32L * i));
        this.intArray = new int[i];
        for (int j = 0; j < i; ++j) {
            this.intArray[j] = input.readInt();
        }
    }

    @Override
    public byte getId() {
        return (byte)11;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (int i : this.intArray) {
            s.append(i).append(",");
        }
        return s + "]";
    }

    @Override
    public NBTTagIntArray copy() {
        int[] aint = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
        return new NBTTagIntArray(aint);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.intArray, ((NBTTagIntArray) obj).intArray);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }

    public int[] getIntArray() {
        return this.intArray;
    }
}
