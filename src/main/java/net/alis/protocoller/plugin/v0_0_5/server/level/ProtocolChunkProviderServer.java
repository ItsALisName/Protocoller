package net.alis.protocoller.plugin.v0_0_5.server.level;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.server.level.chunk.ProtocolChunk;
import net.alis.protocoller.samples.server.world.level.ChunkProviderServer;
import net.alis.protocoller.util.AccessedObject;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class ProtocolChunkProviderServer implements ChunkProviderServer {

    private final AccessedObject handle;
    private final ProtocolWorldServer world;

    public ProtocolChunkProviderServer(Object chunkProviderServer, ProtocolWorldServer world) {
        this.handle = new AccessedObject(chunkProviderServer, true);
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
    public boolean isChunkLoaded(int x, int z) {
        return this.handle.invoke(isChunkLoadedMethod, x, z);
    }

    public ProtocolChunk getChunk(int x, int z) {
        Object chunk = this.handle.invoke(getChunkMethod, x, z);
        return chunk != null ? new ProtocolChunk(chunk, this.world) : null;
    }

    public ProtocolChunk getChunkIfLoaded(int x, int z) {
        return new ProtocolChunk(this.handle.invoke(getLoadedChunkMethod, x, z), this.world);
    }

    public ProtocolWorldServer getWorld() {
        return world;
    }

    public AccessedObject getHandle() {
        return handle;
    }

    private static Method isChunkLoadedMethod, getLoadedChunkMethod, getChunkMethod;

    public static void init() {
        //isChunkLoadedMethod = Reflect.getMethod(clazz(), "isChunkLoaded", boolean.class, false, int.class, int.class);
        isChunkLoadedMethod = Reflect.getMethod(clazz(), boolean.class, false, int.class, int.class);
        //getLoadedChunkMethod = Reflect.getMethod(clazz(), "getChunkIfLoaded", ClassAccessor.get().getMinecraftChunkClass(), true, int.class, int.class);
        getLoadedChunkMethod = Reflect.getMethod(clazz(), ClassAccessor.get().getMinecraftChunkClass(), true, int.class, int.class);
        if(getLoadedChunkMethod == null) {
            getLoadedChunkMethod = Reflect.getMethod(clazz(), "getChunkAtIfLoadedMainThread", ClassAccessor.get().getMinecraftChunkClass(), false, int.class, int.class);
        }
        getChunkMethod = Reflect.getMethod(clazz(), "getChunkAt", ClassAccessor.get().getMinecraftChunkClass(), true, int.class, int.class);
        if(getChunkMethod == null) {
            getChunkMethod = Reflect.getMethod(clazz(), "getChunkAtMainThread", ClassAccessor.get().getMinecraftChunkClass(), true, int.class, int.class);
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getChunkProviderServer();
    }

}
