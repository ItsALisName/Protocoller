package net.alis.protocoller.samples.spigotmc;

public interface TickLimiter {

    int getMaxTime();

    void setMaxTime(int time);

    long getStartTime();

    void setStartTime(long time);

    void initTick();

    boolean shouldContinue();

}
