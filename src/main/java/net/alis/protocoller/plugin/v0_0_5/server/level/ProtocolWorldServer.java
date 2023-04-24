package net.alis.protocoller.plugin.v0_0_5.server.level;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.ProtocolSpigotTickLimiter;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.server.ProtocolServer;
import net.alis.protocoller.plugin.v0_0_5.server.level.border.ProtocolWorldBorder;
import net.alis.protocoller.plugin.v0_0_5.server.level.lighting.ProtocolLightEngine;
import net.alis.protocoller.plugin.v0_0_5.server.level.storage.ProtocolWorldData;
import net.alis.protocoller.samples.server.world.level.WorldServer;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public class ProtocolWorldServer implements WorldServer {

    private final AccessedObject handle;
    private final ProtocolServer server;
    private ProtocolSpigotTickLimiter entityLimiter;
    private ProtocolSpigotTickLimiter tileLimiter;
    private ProtocolWorldData worldData;
    private ProtocolChunkProviderServer chunkProvider;
    private ProtocolWorldBorder worldBorder;
    private ProtocolLightEngine lightEngine;

    public ProtocolWorldServer(Object worldServer, ProtocolServer server) {
        this.handle = new AccessedObject(worldServer, true);
        this.server = server;
        Object enTickLimiter = this.handle.readSuperclassField(0, ProtocolSpigotTickLimiter.clazz());
        Object tlTickLimiter = this.handle.readSuperclassField(1, ProtocolSpigotTickLimiter.clazz());
        if(enTickLimiter != null) this.entityLimiter = new ProtocolSpigotTickLimiter(this.handle.readSuperclassField(0, ProtocolSpigotTickLimiter.clazz()));
        if(tlTickLimiter != null) this.tileLimiter = new ProtocolSpigotTickLimiter(this.handle.readSuperclassField(1, ProtocolSpigotTickLimiter.clazz()));
        Object worldData = null;
        if(this.server.getVersion().greaterThanOrEqualTo(Version.v1_16)) {
            worldData = ClassAccessor.get().getWorldDataServerClass().cast(this.handle.readSuperclassField(0, ClassAccessor.get().getWorldDataMutable()));
        } else {
            worldData = this.handle.readSuperclassField(0, ProtocolWorldData.clazz());
        }
        if(worldData != null) this.worldData = new ProtocolWorldData(worldData, this);
        if(ClassAccessor.get().getChunkProviderServer() != null) {
            Object chunkProviderServer = this.handle.readField(0, ClassAccessor.get().getChunkProviderServer());
            if (chunkProviderServer != null)
                this.chunkProvider = new ProtocolChunkProviderServer(chunkProviderServer, this);
        }
        Object worldBorder = this.handle.readSuperclassField(0, ProtocolWorldBorder.clazz());
        if(worldBorder != null) this.worldBorder = new ProtocolWorldBorder(worldBorder, this);
        if(getLightEngineMethod != null) {
            Object lightEngine = this.handle.invoke(getLightEngineMethod);
            if(lightEngine != null) this.lightEngine = new ProtocolLightEngine(lightEngine);
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

    @Override
    public @Nullable ProtocolLightEngine getLightEngine() {
        return this.lightEngine;
    }

    public ProtocolWorldData getWorldData() {
        return worldData;
    }

    @Override
    public ProtocolChunkProviderServer getChunkProvider() {
        return chunkProvider;
    }

    @Override
    public @Nullable Thread getThread() {
        return threadField != null ? this.handle.readField(threadField) : null;
    }

    @Override
    public ProtocolWorldBorder getWorldBorder() {
        return this.worldBorder;
    }

    public void setWorldBorder(ProtocolWorldBorder worldBorder) {
        this.worldBorder = worldBorder;
    }

    @Override
    public void setThread(Thread thread) throws Exception {
        if(threadField != null) this.handle.writeField(threadField, thread);
    }

    public ProtocolSpigotTickLimiter getEntityTickLimiter() {
        return entityLimiter;
    }

    public int getTickPosition() {
        return this.handle.readField(tickPositionField);
    }

    public void setTickPosition(int tickPosition) {
        this.handle.writeField(tickPositionField, tickPosition);
    }

    public ProtocolSpigotTickLimiter getTileTickLimiter() {
        return tileLimiter;
    }

    public boolean isPopulating() {
        return this.handle.readNamedSuperclassField("populating");
    }

    public void setPopulating(boolean populating) {
        this.handle.writeNamedSuperclassField("populating", populating);
    }

    public boolean isTickingBlockEntities() {
        return this.handle.readField(0, Boolean.TYPE);
    }

    public void setTickingBlockEntities(boolean tick) {
        this.handle.write(0, tick);
    }

    public float getLastRainLevel() {
        return this.handle.readField(0, Float.TYPE);
    }

    public void setLastRainLevel(float level) {
        this.handle.write(0, level);
    }

    public float getRainLevel() {
        return this.handle.readField(1, Float.TYPE);
    }

    public void setRainLevel(float level) {
        this.handle.write(1, level);
    }

    public float getLastThunderLevel() {
        return this.handle.readField(2, Float.TYPE);
    }

    public void setLastThunderLevel(float level) {
        this.handle.write(2, level);
    }

    public float getThunderLevel() {
        return this.handle.readField(3, Float.TYPE);
    }

    public void setThunderLevel(float level) {
        this.handle.write(3, level);
    }

    public ProtocolServer getServer() {
        return server;
    }

    public AccessedObject getHandle() {
        return handle;
    }

    public void setEntityLimiter(ProtocolSpigotTickLimiter entityLimiter) {
        this.entityLimiter = entityLimiter;
    }

    public void setTileLimiter(ProtocolSpigotTickLimiter tileLimiter) {
        this.tileLimiter = tileLimiter;
    }

    public void setWorldData(ProtocolWorldData worldData) {
        this.worldData = worldData;
    }

    public void setChunkProvider(ProtocolChunkProviderServer chunkProvider) {
        this.chunkProvider = chunkProvider;
    }

    public void setLightEngine(ProtocolLightEngine lightEngine) {
        this.lightEngine = lightEngine;
    }

    public Method lightEngineMethod() {
        return getLightEngineMethod;
    }

    private static Field tickPositionField, threadField;

    private static Method getLightEngineMethod;
    public static void init() {
        tickPositionField = Reflect.getField(ClassAccessor.get().getMinecraftWorldClass(), "tickPosition", true);
        if(tickPositionField == null) {
            tickPositionField = Reflect.getField(ClassAccessor.get().getMinecraftWorldClass(), "tileTickPosition", false);
        }
        threadField = Reflect.getField(ClassAccessor.get().getMinecraftWorldClass(), 0, Thread.class, true);
        getLightEngineMethod = Reflect.getMethod(clazz().getSuperclass(), 0, ProtocolLightEngine.clazz(), true);
    }


    public static Class<?> clazz() {
        return ClassAccessor.get().getMinecraftWorldServerClass();
    }


}
