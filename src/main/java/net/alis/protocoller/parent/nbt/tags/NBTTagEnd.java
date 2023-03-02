package net.alis.protocoller.parent.nbt.tags;

import net.alis.protocoller.parent.nbt.NBTBase;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {

    @Override
    public void read(DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(64L);
    }

    @Override
    public void write(DataOutput output) throws IOException {}

    @Override
    public byte getId() {
        return (byte)0;
    }

    @Override
    public String toString() {
        return "END";
    }

    @Override
    public NBTTagEnd copy() {
        return new NBTTagEnd();
    }
}
