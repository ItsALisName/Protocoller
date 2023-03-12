package net.alis.protocoller.samples.nbt.tags;

import net.alis.protocoller.samples.nbt.NBTBase;
import net.alis.protocoller.samples.nbt.NBTSizeTracker;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class NBTTagString extends NBTBase {

    private String data;

    public NBTTagString() {
        this("");
    }

    public NBTTagString(String data) {
        Objects.requireNonNull(data, "Null string not allowed");
        this.data = data;
    }

    public void write(DataOutput output) throws IOException {
        output.writeUTF(this.data);
    }

    public void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(288L);
        this.data = input.readUTF();
        sizeTracker.read((long)(16 * this.data.length()));
    }

    public byte getId() {
        return 8;
    }

    public String toString() {
        return func_193588_a(this.data);
    }

    public NBTTagString copy() {
        return new NBTTagString(this.data);
    }

    public boolean hasNoTags() {
        return this.data.isEmpty();
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        } else {
            NBTTagString nbttagstring = (NBTTagString)obj;
            return this.data == null && nbttagstring.data == null || Objects.equals(this.data, nbttagstring.data);
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }

    public String getString() {
        return this.data;
    }

    public static String func_193588_a(String p_193588_0_) {
        StringBuilder stringbuilder = new StringBuilder("\"");
        for (int i = 0; i < p_193588_0_.length(); ++i) {
            char c0 = p_193588_0_.charAt(i);
            if (c0 == '\\' || c0 == '"') {
                stringbuilder.append('\\');
            }
            stringbuilder.append(c0);
        }
        return stringbuilder.append('"').toString();
    }
}
