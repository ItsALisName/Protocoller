package net.alis.protocoller.plugin.v0_0_4.server;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EntityList {

    private final Map<Integer, Entity> entities = new HashMap<>();

    protected EntityList() { }

    public Map<Integer, Entity> getEntities() {
        return entities;
    }

    @Nullable
    public Entity getEntity(int id) {
        return this.entities.get(id);
    }

    public void addEntity(int id, Entity entity) {
        this.entities.put(id, entity);
    }

    public void removeEntity(int id) {
        this.entities.remove(id);
    }

    public boolean isIdPresent(int id) {
        return this.entities.containsKey(id);
    }

}
