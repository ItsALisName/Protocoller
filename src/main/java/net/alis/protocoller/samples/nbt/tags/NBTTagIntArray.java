package net.alis.protocoller.samples.nbt.tags;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.nbt.NBTBase;
import net.alis.protocoller.samples.nbt.NBTSizeTracker;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NBTTagIntArray extends NBTBase {
    
    private int[] intArray;

    public NBTTagIntArray() {}

    public NBTTagIntArray(int[] ints) {
        this.intArray = ints;
    }

    public NBTTagIntArray(List<Integer> ints) {
        this(fromIntList(ints));
    }

    private static int[] fromIntList(List<Integer> ints) {
        int[] aint = new int[ints.size()];
        for (int i = 0; i < ints.size(); ++i) {
            Integer integer = (Integer)ints.get(i);
            aint[i] = integer == null ? 0 : integer.intValue();
        }
        return aint;
    }

    public void write(DataOutput output) throws IOException {
        output.writeInt(this.intArray.length);
        for (int i : this.intArray) {
            output.writeInt(i);
        }
    }

    public void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(192L);
        int i = input.readInt();
        sizeTracker.read((32L * i));
        this.intArray = new int[i];
        for (int j = 0; j < i; ++j)
        {
            this.intArray[j] = input.readInt();
        }
    }

    public byte getId() {
        return 11;
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[I;");
        for (int i = 0; i < this.intArray.length; ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }
            stringbuilder.append(this.intArray[i]);
        }

        return stringbuilder.append(']').toString();
    }

    public NBTTagIntArray copy() {
        int[] aint = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
        return new NBTTagIntArray(aint);
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.intArray, ((NBTTagIntArray)obj).intArray);
    }

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }

    public int[] getIntArray() {
        return this.intArray;
    }

    @Override
    public Object toOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, int[].class),
                this.intArray
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getNbtTagIntArrayClass();
    }

}
