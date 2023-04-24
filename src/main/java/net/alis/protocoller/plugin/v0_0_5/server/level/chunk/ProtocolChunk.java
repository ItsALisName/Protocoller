package net.alis.protocoller.plugin.v0_0_5.server.level.chunk;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.memory.ApproximateData;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolWorldServer;
import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import net.alis.protocoller.samples.server.world.level.WorldServer;
import net.alis.protocoller.samples.server.world.level.chunk.IChunk;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class ProtocolChunk implements IChunk {

    private final AccessedObject handle;
    private final ProtocolWorldServer world;
    private static boolean hasSuperclass;

    public ProtocolChunk(Object minecraftChunk, @NotNull ProtocolWorldServer world) {
        this.handle = new AccessedObject(minecraftChunk, true);
        this.world = world;

    }

    @Override
    public <P> P getParameter(int index, Class<?> type) throws Exception {
        return this.handle.readField(index, type);
    }

    @Override
    public void setParameter(int index, Class<?> type, Object parameter) throws Exception {
        this.handle.writeSpecify(index, type, parameter);
    }

    @Override
    public <P> P getParameter(Predicate<Version> versionPredicate, int index1, int index2, Class<?> type) throws Exception {
        return this.handle.readField(versionPredicate, index1, index2, type);
    }

    @Override
    public void setParameter(Predicate<Version> versionPredicate, int index1, int index2, Object parameter) throws Exception {
        this.handle.write(versionPredicate, index1, index2, parameter);
    }

    @Override
    public ChunkCoordIntPair getCoordinates() {
        return new ChunkCoordIntPair(this.handle.invoke(getChunkCoordIntPairMethod));
    }

    public WorldServer getWorld() {
        return world;
    }

    public AccessedObject getHandle() {
        return handle;
    }

    private static Method getChunkCoordIntPairMethod;

    public static void init() {
        hasSuperclass = ApproximateData.get().getPreVersion().greaterThanOrEqualTo(Version.v1_18_n1);
        getChunkCoordIntPairMethod = Reflect.getMethod(clazz(), ChunkCoordIntPair.clazz(), true);
        if(getChunkCoordIntPairMethod == null) {
            getChunkCoordIntPairMethod = Reflect.getMethod(clazz().getSuperclass(), ChunkCoordIntPair.clazz(), false);
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getMinecraftChunkClass();
    }
}
