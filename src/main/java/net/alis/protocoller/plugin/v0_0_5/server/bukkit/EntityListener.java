package net.alis.protocoller.plugin.v0_0_5.server.bukkit;

import net.alis.protocoller.plugin.providers.IProtocolAccess;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity =  event.getEntity();
        IProtocolAccess.get().getServer().getEntityList().addEntity(entity.getEntityId(), entity);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        IProtocolAccess.get().getServer().getEntityList().removeEntity(event.getEntity().getEntityId());
    }

}
