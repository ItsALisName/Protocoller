package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTBase;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {

    private String data;

    public NBTTagString() {
        this.data = "";
    }

    public NBTTagString(String data) {
        this.data = data;
        if (data == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    @Override
    public void write(@NotNull DataOutput output) throws IOException {
        output.writeUTF(this.data);
    }

    public void read(@NotNull DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(288L);
        this.data = input.readUTF();
        sizeTracker.read((long)(16 * this.data.length()));
    }

    @Override
    public byte getId() {
        return (byte)8;
    }

    @Override
    public String toString() {
        return "\"" + this.data.replace("\"", "\\\"") + "\"";
    }

    @Override
    public NBTTagString copy() {
        return new NBTTagString(this.data);
    }

    @Override
    public boolean hasNoTags() {
        return this.data.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        } else {
            NBTTagString nbttagstring = (NBTTagString) obj;
            return this.data == null && nbttagstring.data == null || this.data != null && this.data.equals(nbttagstring.data);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }

    @Override
    public String getString() {
        return this.data;
    }
}
