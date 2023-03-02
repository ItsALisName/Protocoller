package net.alis.protocoller.parent.nbt.tags;

import com.google.common.collect.Lists;
import net.alis.protocoller.parent.nbt.NBTBase;
import net.alis.protocoller.parent.nbt.NBTSizeTracker;
import net.alis.protocoller.parent.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class NBTTagList extends NBTBase {
    private List<NBTBase> tagList = Lists.newArrayList();


    private byte tagType = 0;

    @Override
    public void write(DataOutput output) throws IOException {
        if (this.tagList.isEmpty()) {
            this.tagType = 0;
        } else {
            this.tagType = this.tagList.get(0).getId();
        }
        output.writeByte(this.tagType);
        output.writeInt(this.tagList.size());
        for (NBTBase nbtBase : this.tagList) {
            nbtBase.write(output);
        }
    }

    @Override
    public void read(DataInput input, int depth, @NotNull NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.read(296L);
        if (depth > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        } else {
            this.tagType = input.readByte();
            int i = input.readInt();
            if (this.tagType == 0 && i > 0) {
                throw new RuntimeException("Missing type on ListTag");
            } else {
                sizeTracker.read(32L * (long)i);
                this.tagList = Lists.<NBTBase>newArrayListWithCapacity(i);
                for (int j = 0; j < i; ++j) {
                    NBTBase nbtbase = NBTBase.createNewByType(this.tagType);
                    nbtbase.read(input, depth + 1, sizeTracker);
                    this.tagList.add(nbtbase);
                }
            }
        }
    }

    @Override
    public byte getId() {
        return (byte)9;
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[");
        for (int i = 0; i < this.tagList.size(); ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }
            stringbuilder.append(i).append(':').append(this.tagList.get(i));
        }
        return stringbuilder.append(']').toString();
    }


    public void appendTag(@NotNull NBTBase nbt) {
        if (nbt.getId() == 0) {
            Bukkit.getConsoleSender().sendMessage("[Protocoller] Invalid TagEnd added to ListTag");
        } else {
            if (this.tagType == 0) {
                this.tagType = nbt.getId();
            } else if (this.tagType != nbt.getId()) {
                Bukkit.getConsoleSender().sendMessage("[Protocoller] Adding mismatching tag types to tag list");
                return;
            }
            this.tagList.add(nbt);
        }
    }

    public void set(int idx, @NotNull NBTBase nbt) {
        if (nbt.getId() == 0) {
            Bukkit.getConsoleSender().sendMessage("[Protocoller] Invalid TagEnd added to ListTag");
        } else if (idx >= 0 && idx < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = nbt.getId();
            } else if (this.tagType != nbt.getId()) {
                Bukkit.getConsoleSender().sendMessage("[Protocoller] Adding mismatching tag types to tag list");
                return;
            }
            this.tagList.set(idx, nbt);
        } else {
            Bukkit.getConsoleSender().sendMessage("[Protocoller] Index out of bounds to set tag in tag list");
        }
    }

    public NBTBase removeTag(int i) {
        return (NBTBase)this.tagList.remove(i);
    }

    @Override
    public boolean hasNoTags() {
        return this.tagList.isEmpty();
    }

    public NBTTagCompound getCompoundTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtbase = (NBTBase)this.tagList.get(i);
            if (nbtbase.getId() == 10) {
                return (NBTTagCompound)nbtbase;
            }
        }
        return new NBTTagCompound();
    }

    public int getIntAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtbase = (NBTBase)this.tagList.get(i);
            if (nbtbase.getId() == 3) {
                return ((NBTTagInt)nbtbase).getInt();
            }
        }
        return 0;
    }

    public int[] getIntArrayAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtbase = (NBTBase)this.tagList.get(i);
            if (nbtbase.getId() == 11) {
                return ((NBTTagIntArray)nbtbase).getIntArray();
            }
        }
        return new int[0];
    }

    public double getDoubleAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtbase = (NBTBase)this.tagList.get(i);
            if (nbtbase.getId() == 6) {
                return ((NBTTagDouble)nbtbase).getDouble();
            }
        }
        return 0.0D;
    }

    public float getFloatAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtbase = (NBTBase)this.tagList.get(i);
            if (nbtbase.getId() == 5) {
                return ((NBTTagFloat)nbtbase).getFloat();
            }
        }
        return 0.0F;
    }

    public String getStringTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtbase = (NBTBase)this.tagList.get(i);
            return nbtbase.getId() == 8 ? nbtbase.getString() : nbtbase.toString();
        }
        return "";
    }

    public NBTBase get(int idx) {
        return (NBTBase)(idx >= 0 && idx < this.tagList.size() ? (NBTBase)this.tagList.get(idx) : new NBTTagEnd());
    }

    public int tagCount() {
        return this.tagList.size();
    }

    @Override
    public NBTTagList copy() {
        NBTTagList nbttaglist = new NBTTagList();
        nbttaglist.tagType = this.tagType;

        for (NBTBase nbtbase : this.tagList)
        {
            NBTBase nbtbase1 = nbtbase.copy();
            nbttaglist.tagList.add(nbtbase1);
        }

        return nbttaglist;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            NBTTagList nbttaglist = (NBTTagList)obj;
            if (this.tagType == nbttaglist.tagType) {
                return this.tagList.equals(nbttaglist.tagList);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }

    public int getTagType() {
        return this.tagType;
    }
}
