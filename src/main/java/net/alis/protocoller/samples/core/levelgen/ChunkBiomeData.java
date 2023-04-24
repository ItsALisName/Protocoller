package net.alis.protocoller.samples.core.levelgen;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

public class ChunkBiomeData implements ObjectSample {

    private ChunkCoordIntPair position;
    private byte[] buffer;

    public ChunkBiomeData(Object chunkBiomeData) {
        Utils.checkClassSupportability(clazz(), "ChunkBiomeData", false);
        AccessedObject object = new AccessedObject(chunkBiomeData);
        this.position = new ChunkCoordIntPair((Object) object.readField(0, ChunkCoordIntPair.clazz()));
        this.buffer = object.readField(0, byte[].class);
    }

    public ChunkBiomeData(ChunkCoordIntPair pos, byte[] buffer) {
        Utils.checkClassSupportability(clazz(), "ChunkBiomeData", false);
        this.position = pos;
        this.buffer = buffer;
    }

    public ChunkCoordIntPair getPosition() {
        return position;
    }

    public void setPosition(ChunkCoordIntPair position) {
        this.position = position;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, ChunkCoordIntPair.clazz(), byte[].class),
                this.position.createOriginal(), this.buffer
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getChunkBiomeDataClass();
    }

}
