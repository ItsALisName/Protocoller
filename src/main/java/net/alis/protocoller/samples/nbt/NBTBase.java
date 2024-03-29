package net.alis.protocoller.samples.nbt;

import net.alis.protocoller.samples.nbt.tags.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {

    public abstract void write(DataOutput output) throws IOException;

    public abstract void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException;

    public abstract String toString();

    public abstract byte getId();

    public static @Nullable NBTBase createNewByType(byte id) {
        switch (id) {
            case 0:
                return new NBTTagEnd();
            case 1:
                return new NBTTagByte();
            case 2:
                return new NBTTagShort();
            case 3:
                return new NBTTagInt();
            case 4:
                return new NBTTagLong();
            case 5:
                return new NBTTagFloat();
            case 6:
                return new NBTTagDouble();
            case 7:
                return new NBTTagByteArray();
            case 8:
                return new NBTTagString();
            case 9:
                return new NBTTagList();
            case 10:
                return new NBTTagCompound();
            case 11:
                return new NBTTagIntArray();
            case 12:
                return new NBTTagLongArray();
            default:
                return null;
        }
    }

    @Contract(pure = true)
    public static @NotNull String tagNameById(int id) {
        switch (id) {
            case 0:
                return "TAG_End";
            case 1:
                return "TAG_Byte";
            case 2:
                return "TAG_Short";
            case 3:
                return "TAG_Int";
            case 4:
                return "TAG_Long";
            case 5:
                return "TAG_Float";
            case 6:
                return "TAG_Double";
            case 7:
                return "TAG_Byte_Array";
            case 8:
                return "TAG_String";
            case 9:
                return "TAG_List";
            case 10:
                return "TAG_Compound";
            case 11:
                return "TAG_Int_Array";
            case 12:
                return "TAG_Long_Array";
            case 99:
                return "Any Numeric Tag";
            default:
                return "UNKNOWN";
        }
    }
    
    public abstract NBTBase copy();
    
    public boolean hasNoTags() {
        return false;
    }

    public boolean equals(Object obj) {
        return obj instanceof NBTBase && this.getId() == ((NBTBase)obj).getId();
    }

    public int hashCode() {
        return this.getId();
    }

    public String getString() {
        return this.toString();
    }

    public abstract Object toOriginal();
}
