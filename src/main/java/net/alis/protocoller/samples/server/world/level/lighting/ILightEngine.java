package net.alis.protocoller.samples.server.world.level.lighting;

import net.alis.protocoller.plugin.v0_0_5.server.level.lighting.ProtocolLightEngine;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import org.jetbrains.annotations.NotNull;

public interface ILightEngine {

    void checkBlock(@NotNull BlockPosition position);

    void onBlockEmissionIncrease(@NotNull BlockPosition position, int level);

    int runUpdates(int i, boolean doSkylight, boolean skipEdgeLightPropagation);

    void enableLightSources(@NotNull ChunkCoordIntPair pos, boolean retainData);

    void retainData(@NotNull ChunkCoordIntPair pos, boolean retainData);

    int getRawBrightness(@NotNull BlockPosition pos, int ambientDarkness);

    boolean hasLightWork();

    default Object original() {
        return this instanceof ProtocolLightEngine ? ((ProtocolLightEngine)this).getHandle().getOriginal() : ((LightEngine)this).createOriginal();
    }

}
