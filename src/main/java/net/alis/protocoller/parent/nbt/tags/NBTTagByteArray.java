package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTBase;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {
    private byte[] data;
    public NBTTagByteArray() {}

    public NBTTagByteArray(byte[] data) {
        this.data = data;
    }

    @Override
    public void write(@NotNull DataOutput output) throws IOException {
        output.writeInt(this.data.length);
        output.write(this.data);
    }

    @Override
    public void read(@NotNull DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(192L);
        int i = input.readInt();
        sizeTracker.read((8L * i));
        this.data = new byte[i];
        input.readFully(this.data);
    }

    @Override
    public byte getId() {
        return (byte)7;
    }

    @Override
    public String toString() {
        return "[" + this.data.length + " bytes]";
    }

    @Override
    public NBTBase copy() {
        byte[] abyte = new byte[this.data.length];
        System.arraycopy(this.data, 0, abyte, 0, this.data.length);
        return new NBTTagByteArray(abyte);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(this.data, ((NBTTagByteArray) obj).data);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }

    public byte[] getByteArray() {
        return this.data;
    }
}
