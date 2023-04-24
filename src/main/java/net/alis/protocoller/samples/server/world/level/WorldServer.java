package net.alis.protocoller.samples.server.world.level;

import net.alis.protocoller.plugin.v0_0_5.server.ProtocolServer;
import net.alis.protocoller.samples.server.world.level.border.IWorldBorder;
import net.alis.protocoller.samples.server.world.level.lighting.ILightEngine;
import net.alis.protocoller.samples.server.world.level.storage.WorldData;
import net.alis.protocoller.samples.spigotmc.TickLimiter;
import net.alis.protocoller.util.ParametersChangeable;
import org.jetbrains.annotations.Nullable;

public interface WorldServer extends ParametersChangeable {

    @Nullable WorldData getWorldData();

    ChunkProviderServer getChunkProvider();

    @Nullable Thread getThread();

    IWorldBorder getWorldBorder();

    @Nullable ILightEngine getLightEngine();

    // Why not? =D
    void setThread(Thread thread) throws Exception;

    boolean isTickingBlockEntities();

    void setTickingBlockEntities(boolean tick);

    float getLastRainLevel();

    void setLastRainLevel(float level);

    float getRainLevel();

    void setRainLevel(float level);

    float getLastThunderLevel();

    void setLastThunderLevel(float level);

    float getThunderLevel();

    void setThunderLevel(float level);

    TickLimiter getEntityTickLimiter();

    TickLimiter getTileTickLimiter();

    boolean isPopulating();

    void setPopulating(boolean populating);

    int getTickPosition();

    void setTickPosition(int tickPosition);

    ProtocolServer getServer();

}
