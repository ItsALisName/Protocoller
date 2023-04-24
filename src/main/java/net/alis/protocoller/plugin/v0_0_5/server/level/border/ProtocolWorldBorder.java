package net.alis.protocoller.plugin.v0_0_5.server.level.border;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolWorldServer;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.server.world.level.border.IWorldBorder;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class ProtocolWorldBorder implements IWorldBorder {

    private final AccessedObject handle;
    private final ProtocolWorldServer world;

    public ProtocolWorldBorder(Object worldBorder, ProtocolWorldServer world) {
        this.handle = new AccessedObject(worldBorder, true);
        this.world = world;
    }

    @Override
    public <P> P getParameter(int index, Class<?> type) throws Exception {
        return this.handle.readField(index, type);
    }

    @Override
    public void setParameter(int index, Class<?> type, Object parameter) throws Exception {
        this.handle.writeSpecify(index, type, parameter);
    }

    @Override
    public <P> P getParameter(Predicate<Version> versionPredicate, int index1, int index2, Class<?> type) throws Exception {
        return this.handle.readField(versionPredicate, index1, index2, type);
    }

    @Override
    public void setParameter(Predicate<Version> versionPredicate, int index1, int index2, Object parameter) throws Exception {
        this.handle.write(versionPredicate, index1, index2, parameter);
    }

    public double getCenterX() {
        return this.handle.readField(version -> version.lessThanOrEqualTo(Version.v1_13), 0, 2, double.class);
    }

    public double getCenterZ() {
        return this.handle.readField(version -> version.lessThanOrEqualTo(Version.v1_13), 1, 3, double.class);
    }

    public void setCenterX(double x) {
        this.handle.write(version -> version.lessThanOrEqualTo(Version.v1_13), 0, 2, x);
    }

    public void setCenterZ(double z) {
        this.handle.write(version -> version.lessThanOrEqualTo(Version.v1_13), 1, 3, z);
    }

    public BlockPosition getCenter() {
        return new BlockPosition(this.getCenterX(), 0, this.getCenterZ());
    }

    public void setCenter(@NotNull BlockPosition position) {
        this.handle.invoke(setCenterMethod, position.getX(), position.getZ());
    }

    public void setCenter(double x, double z) {
        this.handle.invoke(setCenterMethod, x, z);
    }

    public double getDamageBuffer() {
        return this.handle.readField(version -> version.lessThanOrEqualTo(Version.v1_13), 5, 1, double.class);
    }

    public void setDamageBuffer(double blocks) {
        this.handle.write(version -> version.lessThanOrEqualTo(Version.v1_13), 5, 1, blocks);
    }

    public double getDamageAmount() {
        return this.handle.readField(version -> version.lessThanOrEqualTo(Version.v1_13), 4, 0, double.class);
    }

    public void setDamageAmount(double amount) {
        this.handle.write(version -> version.lessThanOrEqualTo(Version.v1_13), 4, 0, amount);
    }

    public int getWarningTime() {
        return this.handle.readField(version -> version.lessThanOrEqualTo(Version.v1_13), 1, 0, int.class);
    }

    public void setWarningTime(int time) {
        this.handle.write(version -> version.lessThanOrEqualTo(Version.v1_13), 1, 0, time);
    }

    public int getWarningDistance() {
        return this.handle.readField(version -> version.lessThanOrEqualTo(Version.v1_13), 2, 1, int.class);
    }

    public void setWarningDistance(int distance) {
        this.handle.write(version -> version.lessThanOrEqualTo(Version.v1_13), 2, 1, distance);
    }

    public int getAbsoluteMaxSize() {
        return this.handle.invoke(getAbsoluteMaxSizeMethod);
    }

    public void setAbsoluteMaxSize(int size) {
        this.handle.invoke(setAbsoluteMaxSizeMethod, size);
    }

    public long getLerpTime() {
        return getLerpTimeMethod != null ? this.handle.invoke(getLerpTimeMethod) : 0L;
    }

    public double getNewSize() {
        return getNewSizeMethod != null ? this.handle.invoke(getNewSizeMethod) : 0;
    }

    public double getSize() {
        return this.handle.invoke(getSizeMethod);
    }

    public void setSize(double size) {
        this.handle.invoke(setSizeMethod, size);
    }

    public ProtocolWorldServer getWorld() {
        return world;
    }

    public AccessedObject getHandle() {
        return handle;
    }

    private static Method setCenterMethod, getSizeMethod, setSizeMethod, getNewSizeMethod, getAbsoluteMaxSizeMethod, setAbsoluteMaxSizeMethod, getLerpTimeMethod;

    public static void init() {
        setCenterMethod = Reflect.getMethod(clazz(), "setCenter", Void.TYPE, true, double.class, double.class);
        if(setCenterMethod == null) {
            setCenterMethod = Reflect.getMethod(clazz(), "c", Void.TYPE, false, double.class, double.class);
        }
        getSizeMethod = Reflect.getMethod(clazz(), "getSize", double.class, true);
        if(getSizeMethod == null) {
            getSizeMethod = Reflect.getMethod(clazz(), "i", double.class, false);
        }
        setSizeMethod = Reflect.getMethod(clazz(), "setSize", Void.TYPE, true, double.class);
        if(setSizeMethod == null) {
            setSizeMethod = Reflect.getMethod(clazz(), "a", Void.TYPE, false, double.class);
        }
        getNewSizeMethod = Reflect.getMethod(clazz(), "j", double.class, true);
        if(getNewSizeMethod == null) {
            getNewSizeMethod = Reflect.getMethod(clazz(), "k", double.class, true);
        }
        getAbsoluteMaxSizeMethod = Reflect.getMethod(clazz(), "l", int.class, true);
        if(getAbsoluteMaxSizeMethod == null) {
            getAbsoluteMaxSizeMethod = Reflect.getMethod(clazz(), "m", int.class, true);
        }
        setAbsoluteMaxSizeMethod = Reflect.getMethod(clazz(), "a", Void.TYPE, false, int.class);
        getLerpTimeMethod = Reflect.getMethod(clazz(), "j", long.class, true);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getMinecraftWorldBorderClass();
    }
}
