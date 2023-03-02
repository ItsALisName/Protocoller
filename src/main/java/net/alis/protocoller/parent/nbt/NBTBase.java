package net.alis.protocoller.parent.nbt;

import net.alis.protocoller.parent.nbt.tags.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {
    public static final String[] NBT_TYPES = new String[] {"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]"};

    public abstract void write(DataOutput output) throws IOException;

    public abstract void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException;

    public abstract String toString();

    public abstract byte getId();

    protected static NBTBase createNewByType(byte id) {
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

            default:
                return null;
        }
    }

    public abstract NBTBase copy();

    public boolean hasNoTags() {
        return false;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NBTBase)) {
            return false;
        } else {
            NBTBase nbtbase = (NBTBase)obj;
            return this.getId() == nbtbase.getId();
        }
    }

    public int hashCode() {
        return this.getId();
    }

    public String getString() {
        return this.toString();
    }
}
