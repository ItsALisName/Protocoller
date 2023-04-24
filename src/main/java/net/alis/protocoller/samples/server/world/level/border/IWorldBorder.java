package net.alis.protocoller.samples.server.world.level.border;

import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.server.world.level.WorldServer;
import net.alis.protocoller.util.ParametersChangeable;
import org.jetbrains.annotations.NotNull;

public interface IWorldBorder extends ParametersChangeable {

    double getCenterX();

    double getCenterZ();

    void setCenterX(double x);

    void setCenterZ(double z);

    BlockPosition getCenter();

    void setCenter(@NotNull BlockPosition position);

    void setCenter(double x, double z);

    double getDamageBuffer();

    void setDamageBuffer(double blocks);

    double getDamageAmount();

    void setDamageAmount(double amount);

    int getAbsoluteMaxSize();

    void setAbsoluteMaxSize(int size);

    long getLerpTime();

    int getWarningTime();

    void setWarningTime(int time);

    int getWarningDistance();

    void setWarningDistance(int distance);

    double getSize();

    void setSize(double size);

    double getNewSize();

    WorldServer getWorld();

}
