package net.alis.protocoller.samples.core;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

public class ChunkCoordIntPair implements ObjectSample {

    private int x;
    private int z;
    private long longKey;

    public ChunkCoordIntPair(Object chunkCoordIntPair) {
        AccessedObject object = new AccessedObject(chunkCoordIntPair);
        if(GlobalProvider.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_16_4n5)) {
            this.x = object.read(0, Integer.TYPE);
            this.z = object.read(1, Integer.TYPE);
            this.longKey = getLongKey(this.x, this.z);
            return;
        } else if(GlobalProvider.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_17_1)) {
            this.x = object.read(2, Integer.TYPE);
            this.z = object.read(3, Integer.TYPE);
            this.longKey = getLongKey(this.x, this.z);
            return;
        } else if(GlobalProvider.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_18_2)) {
            this.x = object.read(3, Integer.TYPE);
            this.z = object.read(4, Integer.TYPE);
            this.longKey = getLongKey(this.x, this.z);
            return;
        } else if(GlobalProvider.get().getServer().getVersion().lessThanOrEqualTo(Version.v1_19_3)) {
            this.x = object.read(5, Integer.TYPE);
            this.z = object.read(6, Integer.TYPE);
            this.longKey = getLongKey(this.x, this.z);
            return;
        }
    }

    public ChunkCoordIntPair(int x, int z) {
        this.x = x;
        this.z = z;
        this.longKey = getLongKey(this.x, this.z);
    }

    public ChunkCoordIntPair(BlockPosition pos) {
        this.x = pos.getX() >> 4;
        this.z = pos.getZ() >> 4;
        this.longKey = getLongKey(this.x, this.z);
    }

    public ChunkCoordIntPair(long pos) {
        this.x = (int)pos;
        this.z = (int)(pos >> 32);
        this.longKey = getLongKey(this.x, this.z);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.longKey = getLongKey(x, this.z);
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
        this.longKey = getLongKey(this.x, z);
    }

    public BlockPosition toBlockPosition(int y) {
        return new BlockPosition(this.x, y, this.z);
    }

    public long getLongKey() {
        return longKey;
    }

    public static long getLongKey(int chunkX, int chunkZ) {
        return (long)chunkX & 4294967295L | ((long)chunkZ & 4294967295L) << 32;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getChunkCoordIntPairClass(), Integer.TYPE, Integer.TYPE),
                this.x, this.z
        );
    }
}
