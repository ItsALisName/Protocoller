package net.alis.protocoller.samples.server.world.level.chunk;

import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import net.alis.protocoller.samples.server.world.level.WorldServer;
import net.alis.protocoller.util.ParametersChangeable;

public interface IChunk extends ParametersChangeable {

    ChunkCoordIntPair getCoordinates();

    WorldServer getWorld();

}
