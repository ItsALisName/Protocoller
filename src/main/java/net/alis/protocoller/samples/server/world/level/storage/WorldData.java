package net.alis.protocoller.samples.server.world.level.storage;

import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.difficulty.Difficulty;
import net.alis.protocoller.samples.server.world.GameMode;
import net.alis.protocoller.samples.server.world.level.WorldServer;
import net.alis.protocoller.util.ParametersChangeable;

public interface WorldData extends ParametersChangeable {

    String getWorldName();

    void setWorldName(String name);

    Difficulty getDifficulty();

    void setDifficulty(Difficulty difficulty);

    void setWorldSpawn(int x, int y, int z, float angle);

    void setWorldSpawn(BlockPosition position, float angle);

    GameMode getGameType();

    void setGameType(GameMode type);

    int getXSpawn();

    int getYSpawn();

    int getZSpawn();

    void setXSpawn(int x);

    void setYSpawn(int y);

    void setZSpawn(int z);

    WorldServer getWorld();

}
