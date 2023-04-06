package net.alis.protocoller.samples.nbt;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;
import java.util.UUID;

import com.mysql.cj.util.StringUtils;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.samples.authlib.properties.Property;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.nbt.tags.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NBTUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    @Nullable
    public static GameProfile readGameProfileFromNBT(@NotNull NBTTagCompound compound) {
        String s = null;
        String s1 = null;
        if (compound.hasKey("Name", 8)) {
            s = compound.getString("Name");
        }
        if (compound.hasKey("Id", 8)) {
            s1 = compound.getString("Id");
        }
        try {
            UUID uuid;
            try {
                uuid = UUID.fromString(s1);
            } catch (Throwable var12) {
                uuid = null;
            }
            GameProfile gameprofile = new GameProfile(uuid, s);
            if (compound.hasKey("Properties", 10)) {
                NBTTagCompound nbttagcompound = compound.getCompoundTag("Properties");
                for (String s2 : nbttagcompound.getKeySet()) {
                    NBTTagList nbttaglist = nbttagcompound.getTagList(s2, 10);
                    for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                        NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                        String s3 = nbttagcompound1.getString("Value");

                        if (nbttagcompound1.hasKey("Signature", 8)) {
                            gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.getString("Signature")));
                        } else {
                            gameprofile.getProperties().put(s2, new Property(s2, s3));
                        }
                    }
                }
            }
            return gameprofile;
        } catch (Throwable var13) {
            return null;
        }
    }

    public static NBTTagCompound writeGameProfile(NBTTagCompound tagCompound, GameProfile profile) {
        if (!StringUtils.isNullOrEmpty(profile.getName())) {
            tagCompound.setString("Name", profile.getName());
        }
        if (profile.getId() != null) {
            tagCompound.setString("Id", profile.getId().toString());
        }
        if (!profile.getProperties().isEmpty()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            for (String s : profile.getProperties().keySet()) {
                NBTTagList nbttaglist = new NBTTagList();
                for (Property property : profile.getProperties().get(s)) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setString("Value", property.getValue());
                    if (property.hasSignature()) {
                        nbttagcompound1.setString("Signature", property.getSignature());
                    }
                    nbttaglist.appendTag(nbttagcompound1);
                }
                nbttagcompound.setTag(s, nbttaglist);
            }
            tagCompound.setTag("Properties", nbttagcompound);
        }
        return tagCompound;
    }

    @VisibleForTesting
    public static boolean areNBTEquals(NBTBase nbt1, NBTBase nbt2, boolean compareTagList) {
        if (nbt1 == nbt2) {
            return true;
        } else if (nbt1 == null) {
            return true;
        } else if (nbt2 == null) {
            return false;
        } else if (!nbt1.getClass().equals(nbt2.getClass())) {
            return false;
        } else if (nbt1 instanceof NBTTagCompound) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbt1;
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbt2;
            for (String s : nbttagcompound.getKeySet()) {
                NBTBase nbtbase1 = nbttagcompound.getTag(s);
                if (!areNBTEquals(nbtbase1, nbttagcompound1.getTag(s), compareTagList)) {
                    return false;
                }
            }
            return true;
        } else if (nbt1 instanceof NBTTagList && compareTagList) {
            NBTTagList nbttaglist = (NBTTagList)nbt1;
            NBTTagList nbttaglist1 = (NBTTagList)nbt2;
            if (nbttaglist.hasNoTags()) {
                return nbttaglist1.hasNoTags();
            } else {
                for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                    NBTBase nbtbase = nbttaglist.get(i);
                    boolean flag = false;
                    for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
                        if (areNBTEquals(nbtbase, nbttaglist1.get(j), compareTagList)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return nbt1.equals(nbt2);
        }
    }

    public static NBTTagCompound createUUIDTag(UUID uuid) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setLong("M", uuid.getMostSignificantBits());
        nbttagcompound.setLong("L", uuid.getLeastSignificantBits());
        return nbttagcompound;
    }


    public static UUID getUUIDFromTag(NBTTagCompound tag) {
        return new UUID(tag.getLong("M"), tag.getLong("L"));
    }


    public static BlockPosition getPosFromTag(NBTTagCompound tag) {
        return new BlockPosition(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
    }

    public static NBTTagCompound createPosTag(BlockPosition pos) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setInteger("X", pos.getX());
        nbttagcompound.setInteger("Y", pos.getY());
        nbttagcompound.setInteger("Z", pos.getZ());
        return nbttagcompound;
    }

    public static @Nullable NBTBase readOriginalBase(Object nbtBase) {
        int nbtTypeId = BaseReflection.callMethod(nbtBase, BaseReflection.getMethod(nbtBase.getClass(), 0, Integer.TYPE), false);
        switch (nbtTypeId) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte(BaseReflection.readField(nbtBase, 0, Byte.TYPE, false));
            }
            case 2: {
                return new NBTTagShort(BaseReflection.readField(nbtBase, 0, Short.TYPE, false));
            }
            case 3: {
                return new NBTTagInt(BaseReflection.readField(nbtBase, 0, Integer.TYPE, false));
            }
            case 4: {
                return new NBTTagLong(BaseReflection.readField(nbtBase, 0, Long.TYPE, false));
            }
            case 5: {
                return new NBTTagFloat(BaseReflection.readField(nbtBase, 0, Float.TYPE, false));
            }
            case 6: {
                return new NBTTagDouble(BaseReflection.readField(nbtBase, 0, Double.TYPE, false));
            }
            case 7: {
                return new NBTTagByteArray((byte[]) BaseReflection.readField(nbtBase, 0, byte[].class, false));
            }
            case 8: {
                return new NBTTagString(BaseReflection.readField(nbtBase, 0, String.class, false));
            }
            case 9: {
                return new NBTTagList(BaseReflection.readField(nbtBase, 0, List.class, false), true);
            }
            case 10: {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.merge(nbtBase);
                return nbt;
            }
            case 11: {
                return new NBTTagIntArray((int[]) BaseReflection.readField(nbtBase, 0, int[].class, false));
            }
            case 12: {
                return new NBTTagLongArray((long[]) BaseReflection.readField(nbtBase, 0, long[].class, false));
            }
            default: return null;
        }
    }
}
