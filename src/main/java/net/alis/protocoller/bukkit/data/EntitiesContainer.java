package net.alis.protocoller.bukkit.data;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EntitiesContainer {

    private final Map<Integer, Entity> entities = new HashMap<>();

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
