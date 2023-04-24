package net.alis.protocoller.plugin.v0_0_5.server.level.storage;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolWorldServer;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.difficulty.Difficulty;
import net.alis.protocoller.samples.server.world.GameMode;
import net.alis.protocoller.samples.server.world.level.storage.WorldData;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class ProtocolWorldData implements WorldData {

    private final AccessedObject handle;
    private @Nullable AccessedObject worldSettingsHandle;
    private final ProtocolWorldServer world;

    public ProtocolWorldData(Object worldData$Mutable, ProtocolWorldServer world) {
        this.handle = new AccessedObject(worldData$Mutable, true);
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) {
            this.worldSettingsHandle = new AccessedObject(this.handle.readField(0, ClassAccessor.get().getWorldSettingsClass()), true);
        }
        this.world = world;
        this.setSpawnMethod = Reflect.getMethod(worldData$Mutable.getClass(), Void.TYPE, true, BlockPosition.clazz());
        this.setSpawnMethodLevel = 1;
        if(this.setSpawnMethod == null) {
            this.setSpawnMethod = Reflect.getMethod(worldData$Mutable.getClass(), Void.TYPE, true, BlockPosition.clazz(), Float.TYPE);
            this.setSpawnMethodLevel = 2;
        }
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

    public void setWorldSpawn(BlockPosition position, float angle) {
        if(this.setSpawnMethodLevel == 1) {
            this.handle.invoke(setSpawnMethod, position.createOriginal());
            return;
        }
        this.handle.invoke(setSpawnMethod, position.createOriginal(), angle);
    }

    public void setWorldSpawn(int x, int y, int z, float angle) {
        setWorldSpawn(new BlockPosition(x, y, z), angle);
    }

    public String getWorldName() {
        if(this.worldSettingsHandle != null) {
            return this.worldSettingsHandle.readField(0, String.class);
        }
        return this.handle.readField(2, String.class);
    }

    public void setWorldName(String name) {
        if(this.worldSettingsHandle != null) {
            this.worldSettingsHandle.write(0, name);
            return;
        }
        this.handle.write(2, name);
    }

    public GameMode getGameType() {
        if(this.worldSettingsHandle != null) {
            return GameMode.getById(((Enum<?>)this.worldSettingsHandle.readField(0, GameMode.clazz())).ordinal());
        }
        return GameMode.getById(((Enum<?>)this.handle.readField(0, GameMode.clazz())).ordinal());
    }

    public void setGameType(GameMode type) {
        if(this.worldSettingsHandle != null) {
            this.worldSettingsHandle.write(0, type.original());
            return;
        }
        this.handle.write(0, type.original());
    }

    public Difficulty getDifficulty() {
        if(this.worldSettingsHandle != null) {
            return Difficulty.getById(((Enum<?>)this.worldSettingsHandle.readField(0, Difficulty.clazz())).ordinal());
        }
        return Difficulty.getById(((Enum<?>)this.handle.readField(0, Difficulty.clazz())).ordinal());
    }

    public void setDifficulty(Difficulty difficulty) {
        if(this.worldSettingsHandle != null) {
            this.worldSettingsHandle.write(0, difficulty.original());
            return;
        }
        this.handle.write(0, difficulty.original());
    }

    public int getXSpawn() {
        return this.handle.readField(version -> version.greaterThanOrEqualTo(Version.v1_16), 0, 1, Integer.TYPE);
    }

    public int getYSpawn() {
        return this.handle.readField(version -> version.greaterThanOrEqualTo(Version.v1_16), 1, 2, Integer.TYPE);
    }

    public int getZSpawn() {
        return this.handle.readField(version -> version.greaterThanOrEqualTo(Version.v1_16), 2, 3, Integer.TYPE);
    }

    public void setXSpawn(int x) {
        this.handle.write(version -> version.greaterThanOrEqualTo(Version.v1_16), 0, 1, x);
    }

    public void setYSpawn(int y) {
        this.handle.write(version -> version.greaterThanOrEqualTo(Version.v1_16), 1, 2, y);
    }

    public void setZSpawn(int z) {
        this.handle.write(version -> version.greaterThanOrEqualTo(Version.v1_16), 2, 3, z);
    }

    public ProtocolWorldServer getWorld() {
        return world;
    }

    public AccessedObject getHandle() {
        return handle;
    }

    public @Nullable AccessedObject getWorldSettingsHandle() {
        return worldSettingsHandle;
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getWorldDataClass();
    }

    private Method setSpawnMethod;
    private int setSpawnMethodLevel;
}
