package net.alis.protocoller.samples.server.world.level;

import net.alis.protocoller.samples.server.world.level.chunk.IChunk;
import net.alis.protocoller.util.ParametersChangeable;
import org.jetbrains.annotations.Nullable;

public interface ChunkProviderServer extends ParametersChangeable {

    boolean isChunkLoaded(int x, int z);

    @Nullable IChunk getChunk(int x, int z);

    IChunk getChunkIfLoaded(int x, int z);

    WorldServer getWorld();

}
