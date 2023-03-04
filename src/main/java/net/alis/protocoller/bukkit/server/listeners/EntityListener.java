package net.alis.protocoller.bukkit.server.listeners;

import net.alis.protocoller.bukkit.providers.GlobalProvider;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity =  event.getEntity();
        GlobalProvider.instance().getData().getEntitiesContainer().addEntity(entity.getEntityId(), entity);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        GlobalProvider.instance().getData().getEntitiesContainer().removeEntity(event.getEntity().getEntityId());
    }

}
