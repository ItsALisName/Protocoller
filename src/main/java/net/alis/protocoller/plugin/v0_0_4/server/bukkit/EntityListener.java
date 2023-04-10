package net.alis.protocoller.plugin.v0_0_4.server.bukkit;

import net.alis.protocoller.plugin.providers.GlobalProvider;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity =  event.getEntity();
        GlobalProvider.get().getServer().getEntityList().addEntity(entity.getEntityId(), entity);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        GlobalProvider.get().getServer().getEntityList().removeEntity(event.getEntity().getEntityId());
    }

}
