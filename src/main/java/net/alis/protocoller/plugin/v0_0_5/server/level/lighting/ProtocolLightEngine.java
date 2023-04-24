package net.alis.protocoller.plugin.v0_0_5.server.level.lighting;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import net.alis.protocoller.samples.server.world.level.lighting.ILightEngine;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class ProtocolLightEngine implements ILightEngine {

    private final AccessedObject handle;

    public ProtocolLightEngine(Object lightEngine) {
        this.handle = new AccessedObject(lightEngine, true);
    }

    public void checkBlock(@NotNull BlockPosition position) {
        isMethodAvailable(checkBlockMethod, "checkBlock(BlockPosition)");
        this.handle.invoke(checkBlockMethod, position.createOriginal());
    }

    public void onBlockEmissionIncrease(@NotNull BlockPosition position, int level) {
        isMethodAvailable(onBlockEmissionIncreaseMethod, "onBlockEmissionIncrease(BlockPosition, int)");
        this.handle.invoke(onBlockEmissionIncreaseMethod, position.createOriginal(), level);
    }

    public int runUpdates(int i, boolean doSkylight, boolean skipEdgeLightPropagation) {
        isMethodAvailable(runUpdatesMethod, "runUpdates(int, boolean, boolean)");
        return this.handle.invoke(runUpdatesMethod, i, doSkylight, skipEdgeLightPropagation);
    }

    public void enableLightSources(@NotNull ChunkCoordIntPair pos, boolean retainData) {
        isMethodAvailable(enableLightSourcesMethod, "enableLightSources(ChunkCoordIntPair, boolean)");
        this.handle.invoke(enableLightSourcesMethod, pos.createOriginal(), retainData);
    }

    public void retainData(@NotNull ChunkCoordIntPair pos, boolean retainData) {
        isMethodAvailable(retainDataMethod, "retainData(ChunkCoordIntPair, boolean)");
        this.handle.invoke(retainDataMethod, pos.createOriginal(), retainData);
    }

    public int getRawBrightness(@NotNull BlockPosition pos, int ambientDarkness) {
        isMethodAvailable(getRawBrightnessMethod, "getRawBrightness(BlockPosition, int)");
        return this.handle.invoke(getRawBrightnessMethod, pos.createOriginal(), ambientDarkness);
    }

    public boolean hasLightWork() {
        isMethodAvailable(hasLightWorkMethod, "hasLightWork()");
        return this.handle.invoke(hasLightWorkMethod);
    }

    public AccessedObject getHandle() {
        return handle;
    }

    private static Method checkBlockMethod, onBlockEmissionIncreaseMethod, hasLightWorkMethod, runUpdatesMethod, enableLightSourcesMethod, retainDataMethod,
            getRawBrightnessMethod;

    public static void init() {
        if(clazz() != null){
            enableLightSourcesMethod = Reflect.getMethod(clazz(), Void.TYPE, true, ChunkCoordIntPair.clazz(), boolean.class);
            retainDataMethod = Reflect.getMethod(clazz(), 1, Void.TYPE, true, ChunkCoordIntPair.clazz(), boolean.class);
            runUpdatesMethod = Reflect.getMethod(clazz(), int.class, true, int.class, boolean.class, boolean.class);
            checkBlockMethod = Reflect.getMethod(clazz(), Void.TYPE, true, BlockPosition.clazz());
            onBlockEmissionIncreaseMethod = Reflect.getMethod(clazz(), Void.TYPE, true, BlockPosition.clazz(), int.class);
            hasLightWorkMethod = Reflect.getMethod(clazz(), 0, boolean.class, true);
            getRawBrightnessMethod = Reflect.getMethod(clazz(), int.class, true, BlockPosition.clazz(), int.class);
        }
    }

    private static void isMethodAvailable(Method method, String methodName) {
        if(method == null) {
            new ExceptionBuilder().showStackTrace(false).getReflectionExceptions().customMessage("Method \"ILightEngine#" + methodName + "\" not available on server version!").throwException();
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getLightEngineClass();
    }

}
