package net.alis.protocoller.samples.server.world.level.lighting;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolChunkProviderServer;
import net.alis.protocoller.plugin.v0_0_5.server.level.lighting.ProtocolLightEngine;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import net.alis.protocoller.samples.server.world.level.ChunkProviderServer;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.NotNull;

public class LightEngine implements ILightEngine, ObjectSample {

    private final ILightEngine provider;

    public LightEngine(ChunkProviderServer chunkProvider, boolean hasBlockLight, boolean hasSkyLight) {
        this.provider = new ProtocolLightEngine(Reflect.callConstructor(
                Reflect.getConstructor(ProtocolLightEngine.clazz(), false, ClassAccessor.get().getILightAccessClass(), boolean.class, boolean.class),
                ((ProtocolChunkProviderServer)chunkProvider).getHandle().getOriginal(), hasBlockLight, hasBlockLight
        ));
    }


    @Override
    public void checkBlock(@NotNull BlockPosition position) {
        this.provider.checkBlock(position);
    }

    @Override
    public void onBlockEmissionIncrease(@NotNull BlockPosition position, int level) {
        this.provider.onBlockEmissionIncrease(position, level);
    }

    @Override
    public int runUpdates(int i, boolean doSkylight, boolean skipEdgeLightPropagation) {
        return this.provider.runUpdates(i, doSkylight, skipEdgeLightPropagation);
    }

    @Override
    public void enableLightSources(@NotNull ChunkCoordIntPair pos, boolean retainData) {
        this.provider.enableLightSources(pos, retainData);
    }

    @Override
    public void retainData(@NotNull ChunkCoordIntPair pos, boolean retainData) {
        this.provider.retainData(pos, retainData);
    }

    @Override
    public int getRawBrightness(@NotNull BlockPosition pos, int ambientDarkness) {
        return this.provider.getRawBrightness(pos, ambientDarkness);
    }

    @Override
    public boolean hasLightWork() {
        return this.provider.hasLightWork();
    }

    @Override
    public Object createOriginal() {
        return ((ProtocolLightEngine)this.provider).getHandle().getOriginal();
    }
}
