package net.alis.protocoller.plugin.v0_0_5.server.tasks;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.ProtocolSpigotTickLimiter;
import net.alis.protocoller.plugin.v0_0_5.server.ProtocolServer;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolChunkProviderServer;
import net.alis.protocoller.plugin.v0_0_5.server.level.ProtocolWorldServer;
import net.alis.protocoller.plugin.v0_0_5.server.level.border.ProtocolWorldBorder;
import net.alis.protocoller.plugin.v0_0_5.server.level.lighting.ProtocolLightEngine;
import net.alis.protocoller.plugin.v0_0_5.server.level.storage.ProtocolWorldData;
import net.alis.protocoller.plugin.v0_0_5.server.rcon.ProtocolQueryThreadGS4;
import net.alis.protocoller.plugin.v0_0_5.server.rcon.ProtocolRconThread;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.samples.server.world.level.WorldServer;

import java.util.List;
import java.util.Map;

public class dUSD_Task implements Runnable {

    public static void start() {
        ITaskAccess.get().doAsyncTimerTask(new dUSD_Task(), 0, 60L);
    }

    @Override
    public void run() {

        ProtocolServer server = IProtocolAccess.get().getServer();

        if (server.getQueryThreadGC4() == null) {
            Object remoteStatusListener = server.getHandle().readField(0, ClassAccessor.get().getRemoteStatusListenerClass());
            if (remoteStatusListener != null)
                server.setQueryThreadGS4(new ProtocolQueryThreadGS4(remoteStatusListener));
        }
        if (server.getRconThread() == null) {
            Object remoteControlListener = server.getHandle().readField(0, ClassAccessor.get().getRemoteControlListenerClass());
            if (remoteControlListener != null)
                server.setRconThread(new ProtocolRconThread(remoteControlListener, server));
        }
        if(server.worlds().size() == 0) {
            if(server.getVersion().greaterThanOrEqualTo(Version.v1_13)) {
                for(Map.Entry<?,?> mWorld : ((Map<?, ?>)server.getHandle().readSuperclassField(0, Map.class)).entrySet()) {
                    server.worlds.add(new ProtocolWorldServer(mWorld.getValue(), server));
                }
            } else {
                for(Object mWorld :(List<?>)server.getHandle().readSuperclassField(1, List.class)) {
                    server.worlds.add(new ProtocolWorldServer(mWorld, server));
                }
            }
        } else {
            for(WorldServer world : server.worlds()) {
                ProtocolWorldServer w = (ProtocolWorldServer) world;
                if(w.getWorldData() == null) {
                    Object worldData = null;
                    if(w.getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) {
                        worldData = ClassAccessor.get().getWorldDataServerClass().cast(w.getHandle().readSuperclassField(0, ClassAccessor.get().getWorldDataMutable()));
                    } else {
                        worldData = w.getHandle().readSuperclassField(0, ProtocolWorldData.clazz());
                    }
                    if(worldData != null) w.setWorldData(new ProtocolWorldData(worldData, w));
                }
                if(world.getEntityTickLimiter() == null) {
                    Object enTickLimiter = w.getHandle().readSuperclassField(0, ProtocolSpigotTickLimiter.clazz());
                    if(enTickLimiter != null) w.setEntityLimiter(new ProtocolSpigotTickLimiter(enTickLimiter));
                }
                if(world.getTileTickLimiter() == null) {
                    Object tlTickLimiter = w.getHandle().readSuperclassField(1, ProtocolSpigotTickLimiter.clazz());
                    if(tlTickLimiter != null) w.setTileLimiter(new ProtocolSpigotTickLimiter(tlTickLimiter));
                }
                if(w.getChunkProvider() == null) {
                    Object chunkProviderServer = w.getHandle().readField(0, ClassAccessor.get().getChunkProviderServer());
                    if(chunkProviderServer != null) w.setChunkProvider(new ProtocolChunkProviderServer(chunkProviderServer, w));
                }
                if(w.getWorldBorder() == null) {
                    Object worldBorder = w.getHandle().readSuperclassField(0, ProtocolWorldBorder.clazz());
                    if(worldBorder != null) w.setWorldBorder(new ProtocolWorldBorder(worldBorder, w));
                }
                if(w.getLightEngine() == null && w.lightEngineMethod() != null) {
                    Object lightEngine = w.getHandle().invoke(w.lightEngineMethod());
                    if(lightEngine != null) w.setLightEngine(new ProtocolLightEngine(lightEngine));
                }
            }
        }
    }
}
