package net.alis.protocoller.plugin.util;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.spigotmc.TickLimiter;
import net.alis.protocoller.util.AccessedObject;

import java.lang.reflect.Method;

public class ProtocolSpigotTickLimiter implements TickLimiter {

    private final AccessedObject handle;


    public ProtocolSpigotTickLimiter(Object tickLimiter) {
        this.handle = new AccessedObject(tickLimiter);
        this.initTickMethod = Reflect.getMethod(tickLimiter.getClass(), "initTick", new Class[]{});
        this.shouldContinueMethod = Reflect.getMethod(tickLimiter.getClass(), "shouldContinue", new Class[]{});
    }

    public int getMaxTime() {
        return this.handle.readField(0, Integer.TYPE);
    }

    public void setMaxTime(int time) {
        this.handle.write(0, time);
    }

    public long getStartTime() {
        return this.handle.readField(0, Long.TYPE);
    }

    public void setStartTime(long time) {
        this.handle.write(0, time);
    }

    public void initTick() {
        this.handle.invoke(initTickMethod);
    }

    public boolean shouldContinue() {
        return this.handle.invoke(shouldContinueMethod);
    }

    public AccessedObject getHandle() {
        return handle;
    }

    private final Method initTickMethod, shouldContinueMethod;

    public static Class<?> clazz() {
        return ClassAccessor.get().getSpigotTickLimiterClass();
    }
}
